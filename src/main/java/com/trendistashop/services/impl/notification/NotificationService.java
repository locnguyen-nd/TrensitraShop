package com.trendistashop.services.impl.notification;

import com.trendistashop.constants.ResponseMessage;
import com.trendistashop.dto.chat.FilterDTO;
import com.trendistashop.dto.chat.notification.AdminNotificationRequestDTO;
import com.trendistashop.dto.chat.notification.AdminNotificationResponseDTO;
import com.trendistashop.dto.chat.notification.UserNotificationDTO;
import com.trendistashop.dto.response.PageDTO;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.entities.notification.Notification;
import com.trendistashop.entities.notification.NotificationRecipient;
import com.trendistashop.entities.user.UserEntity;
import com.trendistashop.enums.NotificationType;
import com.trendistashop.enums.ProviderEnum;
import com.trendistashop.enums.SystemNotificationType;
import com.trendistashop.helper.PageConverter;
import com.trendistashop.repositories.auth.UserDetailRepository;
import com.trendistashop.repositories.notification.NotificationRecipientRepository;
import com.trendistashop.repositories.notification.NotificationRepository;
import com.trendistashop.utils.ResponseHelper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Locnd
 */
@Service
@Slf4j
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepo;
    @Autowired
    private NotificationRecipientRepository recipientRepo;
    @Autowired
    private UserDetailRepository userRepo;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private PageConverter pageConverter;

    @Transactional
    public AdminNotificationResponseDTO createNotification(AdminNotificationRequestDTO req, UserEntity admin){
        Notification notification = Notification.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .type(req.getType())
                .createdBy(admin)
                .scheduledAt(req.getScheduledAt())
                .isDraft(req.isDraft())
                .isSent(false)
                .build();

        notification = notificationRepo.save(notification);
        // Nếu không phải draft → gửi luôn
        if (!req.isDraft()) {
            sendNotificationAsync(notification, req.getUserIds());
        }
        return mapToAdminResponse(notification);
    }
    @Transactional
    public AdminNotificationResponseDTO updateNotification(UUID id, AdminNotificationRequestDTO req, UserEntity admin) {
        Notification notification = notificationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        if (notification.isSent()) {
            throw new RuntimeException("Cannot edit sent notification");
        }

        notification.setTitle(req.getTitle());
        notification.setContent(req.getContent());
        notification.setType(req.getType());
        notification.setScheduledAt(req.getScheduledAt());
        notification.setDraft(req.isDraft());

        if (!req.isDraft() && !notification.isSent()) {
            sendNotificationAsync(notification, req.getUserIds());
        }

        return mapToAdminResponse(notification);
    }
    @Transactional
    public void deleteNotification(UUID id) {
        Notification notification = notificationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notificationRepo.delete(notification);
    }
    @Async("notificationTaskExecutor")
    @Transactional
    public void sendSystemNotification(
            SystemNotificationType type,
            UUID targetUserId,           // null = gửi tất cả
            Map<String, Object> data     // dữ liệu gửi đi
    ) {
        String title = replacePlaceholders(type.getTitleTemplate(), data);
        String content = replacePlaceholders(type.getContentTemplate(), data);

        Notification notification = notificationRepo.save(
                Notification.builder()
                        .title(title)
                        .content(content)
                        .type(NotificationType.SYSTEM)
                        .createdBy(getSystemBot())
                        .isSent(false)
                        .isDraft(false)
                        .build()
        );

        Set<UUID> targetIds = targetUserId != null ? Set.of(targetUserId) : null;
        sendNotificationAsync(notification, targetIds);
    }

    private String replacePlaceholders(String template, Map<String, Object> data) {
        if (data == null || data.isEmpty()) return template;
        String result = template;
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            result = result.replace("{" + entry.getKey() + "}", String.valueOf(entry.getValue()));
        }
        return result;
    }
    private UserEntity getSystemBot() {
        return userRepo.findByEmail("system@trendista.shop")
                .orElseGet(this::createSystemBot);
    }
    private UserEntity createSystemBot() {
        UserEntity bot = UserEntity.builder()
                .email("system@trendista.shop")
                .firstName("Trendista")
                .lastName("Shop")
                .provider(ProviderEnum.MANUAL)
                .enabled(true)
                .locked(false)
                .build();
        return userRepo.save(bot);
    }
    @Async("notificationTaskExecutor")
    @Transactional
    public void sendNotificationAsync(Notification notification, Set<UUID> targetUserIds) {
        if (notification == null || notification.isSent()) {
            log.warn("Invalid or already sent notification: {}", notification);
            return;
        }
        LocalDateTime now = LocalDateTime.now();
        // 1. Lấy danh sách user
        try (Stream<UserEntity> userStream = getUserStream(targetUserIds)) {
            List<NotificationRecipient> recipients = new ArrayList<>();
            UserNotificationDTO dto = buildNotificationDTO(notification, now);
            userStream.forEach(user -> {
                NotificationRecipient recipient = NotificationRecipient.builder()
                        .notification(notification)
                        .user(user)
                        .sentAt(now)
                        .isRead(false)
                        .build();
                recipients.add(recipient);
                // Gửi WebSocket (non-blocking), client subscribe theo fortmat user/${email}/queue/notifications
                messagingTemplate.convertAndSendToUser(
                        user.getUsername(),
                        "/queue/notifications",
                        dto
                );
            });
            // 2. Batch save recipients
            if (!recipients.isEmpty()) {
                recipientRepo.saveAll(recipients);
                log.info("Saved {} recipients for notification {}", recipients.size(), notification.getId());
            }
        }
        // 3. Cập nhật trạng thái
        notification.setSent(true);
        notification.setDraft(false);
        notificationRepo.saveAndFlush(notification);
        log.info("Successfully sent notification {} to {} users", notification.getId(),
                targetUserIds == null ? "ALL" : targetUserIds.size());
    }
    private Stream<UserEntity> getUserStream(Set<UUID> userIds) {
        if (userIds != null && !userIds.isEmpty()) {
            return userRepo.findAllById(userIds).stream();
        } else {
            return userRepo.findAllAsStream();
        }
    }
    private UserNotificationDTO buildNotificationDTO(Notification n, LocalDateTime sentAt) {
        return UserNotificationDTO.builder()
                .notificationId(n.getId())
                .title(n.getTitle())
                .content(n.getContent())
                .type(n.getType())
                .sentAt(sentAt)
                .isRead(false)
                .build();
    }
    @Transactional
    public TypeResponse<PageDTO<AdminNotificationResponseDTO>> getAdminNotifications(
            UUID adminId, FilterDTO filter) {
        if (adminId == null) {
            return ResponseHelper.validationError("adminId", "Admin ID không được để trống");
        }
        try {
            String sortDir = "asc".equalsIgnoreCase(filter.getSortDir()) ? "ASC" : "DESC";
            Pageable pageable = PageRequest.of(
                    filter.getPage(),
                    filter.getSize(),
                    Sort.Direction.fromString(sortDir),
                    "createdAt"
            );
            Page<Notification> page = notificationRepo.findByCreatedByIdWithFilter(
                    adminId,
                    filter.getSearch(),
                    filter.getIsSend(),
                    filter.getType(),
                    pageable
            );
            List<AdminNotificationResponseDTO> dtos = page.getContent().stream()
                    .map(this::mapToAdminResponse)
                    .toList();
            PageDTO<AdminNotificationResponseDTO> pageDTO = pageConverter.toPageDTO(
                    new PageImpl<>(dtos, pageable, page.getTotalElements())
            );
            return ResponseHelper.ok(pageDTO, ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            log.error("Lỗi lấy danh sách thông báo admin", e);
            return ResponseHelper.badRequest("Lỗi hệ thống: " + e.getMessage());
        }
    }
    // === USER: Lấy thông báo của mình ===
    public List<UserNotificationDTO> getUserNotifications(UUID userId) {
        UserEntity user = userRepo.findById(userId).orElseThrow();
        return recipientRepo.findByUserOrderBySentAtDesc(user)
                .stream()
                .map(this::mapToUserDTO)
                .collect(Collectors.toList());
    }
    // === USER: Đánh dấu đã đọc ===
    @Transactional
    public void markAsRead(UUID notificationId, UUID userId) {
        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (notificationId == null) {
            // Đánh dấu TẤT CẢ thông báo của user là đã đọc
            List<NotificationRecipient> unreadList = recipientRepo.findByUserAndIsReadFalse(user);
            if (!unreadList.isEmpty()) {
                unreadList.forEach(recipient -> recipient.setRead(true));
                recipientRepo.saveAll(unreadList);
                log.info("Marked {} notifications as read for user {}", unreadList.size(), userId);
            }
        } else {
            // Đánh dấu 1 thông báo cụ thể
            NotificationRecipient recipient = recipientRepo.findByNotificationIdAndUserId(notificationId, userId)
                    .orElseThrow(() -> new RuntimeException("Notification not found for user"));
            if (!recipient.isRead()) {
                recipient.setRead(true);
                recipientRepo.save(recipient);
                log.info("Marked notification {} as read for user {}", notificationId, userId);
            }
        }
    }

    // === HELPER MAPPERS ===
    private AdminNotificationResponseDTO mapToAdminResponse(Notification n) {
        AdminNotificationResponseDTO dto = new AdminNotificationResponseDTO();
        dto.setId(n.getId());
        dto.setTitle(n.getTitle());
        dto.setContent(n.getContent());
        dto.setType(n.getType());
        dto.setCreatedAt(n.getCreatedAt());
        dto.setScheduledAt(n.getScheduledAt());
        dto.setSent(n.isSent());
        dto.setDraft(n.isDraft());
        dto.setRecipientCount(
                n.getRecipients() != null ? n.getRecipients().size() : 0
        );
        dto.setCreatedByName(n.getCreatedBy() != null
                ? n.getCreatedBy().getFullName()
                : "Unknown");
        dto.setCreatedById(n.getId() != null ? n.getId() : null);
        return dto;
    }

    private UserNotificationDTO mapToUserDTO(NotificationRecipient r) {
        UserNotificationDTO dto = new UserNotificationDTO();
        dto.setNotificationId(r.getNotification().getId());
        dto.setTitle(r.getNotification().getTitle());
        dto.setContent(r.getNotification().getContent());
        dto.setType(r.getNotification().getType());
        dto.setSentAt(r.getSentAt());
        dto.setIsRead(r.isRead());
        return dto;
    }

}
