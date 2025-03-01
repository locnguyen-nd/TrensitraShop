package com.trendistra.trendistashop.services.impl.notification;

import com.trendistra.trendistashop.dto.response.NotificationDTO;
import com.trendistra.trendistashop.entities.notification.Notification;
import com.trendistra.trendistashop.entities.user.UserEntity;
import com.trendistra.trendistashop.enums.NotificationStatus;
import com.trendistra.trendistashop.enums.NotificationType;
import com.trendistra.trendistashop.repositories.auth.UserDetailRepository;
import com.trendistra.trendistashop.repositories.notification.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationEventHandler {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserDetailRepository userDetailRepository;
    @Autowired
    private SocketIOService socketIOService;
    public Page<Notification> getNotifications(UUID userId, String type, String status, int page, int size) {
        if (type != null) {
            return notificationRepository.findByUserIdAndType(userId, NotificationType.valueOf(type), PageRequest.of(page, size));
        } else if (status != null) {
            return notificationRepository.findByUserIdAndStatus(userId, NotificationStatus.valueOf(status), PageRequest.of(page, size));
        }
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, PageRequest.of(page, size));
    }
    public void markAsRead(UUID id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        notification.ifPresent(n -> {
            n.setStatus(NotificationStatus.READ);
            notificationRepository.save(n);
        });
    }

    public void sendNotification(NotificationDTO dto) {
        UserEntity user = userDetailRepository.findById(dto.getUserId()).orElseThrow();
        Notification notification = new Notification();
        notification.setType(NotificationType.valueOf(dto.getType()));
        notification.setMessage(dto.getMessage());
        notification.setUser(user);
        notification.setReferenceId(dto.getReferenceId());
        notification.setRedirectUrl(dto.getRedirectUrl());
        notification.setCategory(dto.getCategory());

        Notification savedNotification = notificationRepository.save(notification);

        // Send real-time notification via Socket.IO
        socketIOService.broadcastNotification(convertToDTO(savedNotification));
    }
    // Order-related notifications
    public void notifyOrderPlaced(UUID userId, UUID orderId, String orderNumber) {
        NotificationDTO dto = new NotificationDTO();
        dto.setUserId(userId);
        dto.setType(NotificationType.ORDER_PLACED.name());
        dto.setMessage("Your order #" + orderNumber + " has been placed successfully!");
        dto.setReferenceId(orderId.toString());
        dto.setRedirectUrl("/orders/" + orderId);
        dto.setCategory("ORDER");

        sendNotification(dto);
    }
    public void notifyOrderStatusChanged(UUID userId, UUID orderId, String orderNumber, String newStatus) {
        NotificationDTO dto = new NotificationDTO();
        dto.setUserId(userId);
        dto.setType(NotificationType.ORDER_STATUS_CHANGED.name());
        dto.setMessage("Your order #" + orderNumber + " status has changed to: " + newStatus);
        dto.setReferenceId(orderId.toString());
        dto.setRedirectUrl("/orders/" + orderId);
        dto.setCategory("ORDER");

        sendNotification(dto);
    }
    // Account-related notifications
    public void notifyPasswordChanged(UUID userId) {
        NotificationDTO dto = new NotificationDTO();
        dto.setUserId(userId);
        dto.setType(NotificationType.PASSWORD_CHANGED.name());
        dto.setMessage("Your password has been changed successfully.");
        dto.setRedirectUrl("/account/security");
        dto.setCategory("ACCOUNT");

        sendNotification(dto);
    }
    public void notifyNewMessage(UUID userId, UUID senderId, String senderName) {
        NotificationDTO dto = new NotificationDTO();
        dto.setUserId(userId);
        dto.setType(NotificationType.NEW_MESSAGE.name());
        dto.setMessage("You received a new message from " + senderName);
        dto.setReferenceId(senderId.toString());
        dto.setRedirectUrl("/chat/" + senderId);
        dto.setCategory("CHAT");

        sendNotification(dto);
    }

    private NotificationDTO convertToDTO(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setUserId(notification.getUser().getId());
        dto.setType(notification.getType().name());
        dto.setMessage(notification.getMessage());
        dto.setStatus(notification.getStatus().name());
        dto.setCreatedAt(notification.getCreatedAt());
        dto.setReferenceId(notification.getReferenceId());
        dto.setRedirectUrl(notification.getRedirectUrl());
        dto.setCategory(notification.getCategory());
        return dto;
    }

}