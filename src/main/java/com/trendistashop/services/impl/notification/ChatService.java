package com.trendistashop.services.impl.notification;

import com.trendistashop.constants.ResponseMessage;
import com.trendistashop.dto.chat.*;
import com.trendistashop.dto.response.PageDTO;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.entities.notification.AutoReplyConfig;
import com.trendistashop.entities.notification.ChatConversation;
import com.trendistashop.entities.notification.ChatMessage;
import com.trendistashop.entities.user.UserEntity;
import com.trendistashop.enums.ConversationStatus;
import com.trendistashop.enums.GuardType;
import com.trendistashop.enums.MessageType;
import com.trendistashop.enums.SystemNotificationType;
import com.trendistashop.helper.PageConverter;
import com.trendistashop.repositories.auth.UserDetailRepository;
import com.trendistashop.repositories.notification.AutoReplyConfigRepository;
import com.trendistashop.repositories.notification.ChatConversationRepository;
import com.trendistashop.repositories.notification.ChatMessageRepository;
import com.trendistashop.utils.ResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ChatService {
    @Autowired
    private ChatConversationRepository conversationRepo;
    @Autowired
    private ChatMessageRepository messageRepo;
    @Autowired
    private AutoReplyConfigRepository autoReplyRepo;
    @Autowired
    private UserDetailRepository userRepo;
    @Autowired
    private AIChatService AIChatService;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private PageConverter pageConverter;

    /**
     * TẠO HOẶC LẤY CONVERSATION KHI USER bắt đầu chat
     */
    public TypeResponse<ConversationResponseDTO> getOrCreateConversation(UUID guestSessionId, UserEntity currentUser) {
        try {
            ChatConversation conv;
            // TH1: User đã login
            if (currentUser != null) {
                Optional<ChatConversation> existing = conversationRepo.findByUserId(currentUser.getId());
                if (existing.isPresent()) {
                    conv = existing.get();
                } else {
                    // Tạo mới cho user
                    UserEntity admin = getDefaultAdmin();
                    conv = ChatConversation.builder()
                            .title("Khách hàng: " + currentUser.getFullName())
                            .user(currentUser)
                            .guestSessionId(null)
                            .admin(admin)
                            .status(ConversationStatus.ACTIVE)
                            .build();
                    conv = conversationRepo.save(conv);
                }
            }
            // TH2: Guest (chưa login)
            else if (guestSessionId != null) {
                Optional<ChatConversation> existing = conversationRepo.findByGuestSessionId(guestSessionId);
                if (existing.isPresent()) {
                    conv = existing.get();
                } else {
                    // Tạo mới cho guest
                    UserEntity admin = getDefaultAdmin();
                    conv = ChatConversation.builder()
                            .title("Guest #" + guestSessionId.toString().substring(0, 8))
                            .user(null)
                            .guestSessionId(guestSessionId)
                            .admin(admin)
                            .status(ConversationStatus.ACTIVE)
                            .build();
                    conv = conversationRepo.save(conv);
                }
            } else {
                return ResponseHelper.validationError("user", "Phải có currentUser hoặc guestSessionId");
            }
            return ResponseHelper.created(buildConversationResponse(conv, currentUser), ResponseMessage.CREATE_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.badRequest(e.getMessage());
        }
    }
    /**
     * LẤY CONVERSATION - CHO API /conversation/{id}
     */
    public  TypeResponse<ConversationResponseDTO> getConversation(UUID convId) {
        try {
            ChatConversation conv = conversationRepo.findById(convId)
                    .orElseThrow(() -> new RuntimeException("Conversation not found"));
            // Thử lấy current user, nếu không có thì null (guest)
            UserEntity currentUser = getCurrentUserOrNull();
            return ResponseHelper.ok(buildConversationResponse(conv, currentUser), ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.badRequest(e.getMessage());
        }
    }

    /**
     * BUILD CONVERSATION RESPONSE - HELPER METHOD
     */
    private ConversationResponseDTO buildConversationResponse(ChatConversation conv, UserEntity currentUser) {
        ConversationResponseDTO dto = new ConversationResponseDTO();
        dto.setConversationId(conv.getId());
        dto.setTitle(conv.getTitle());
        dto.setParticipant(
                conv.getUser() != null
                        ? mapToSenderDTO(conv.getUser())
                        : mapToGuestSenderDTO(conv.getGuestSessionId())
        );
        dto.setAdmin(mapToSenderDTO(conv.getAdmin()));
        dto.setStartedAt(conv.getStartedAt());
        dto.setLastMessageAt(conv.getLastMessageAt());

        // Đếm unread - chỉ khi có currentUser
        if (currentUser != null) {
            dto.setUnreadCount((int) messageRepo.countByConversationIdAndIsReadFalseAndReceiverId(
                    conv.getId(), currentUser.getId()));
            // Mark as read
            messageRepo.findUnreadByConversationAndReceiver(conv.getId(), currentUser.getId())
                    .forEach(message -> {
                        message.setRead(true);
                        messageRepo.save(message);
                    });
        } else {
            dto.setUnreadCount(0);
        }
        // Load messages
        dto.setMessages(messageRepo.findByConversationIdOrderBySentAtAsc(conv.getId())
                .stream().map(this::mapToMessageDTO).collect(Collectors.toList()));
        return dto;
    }

    /**
     * GỬI TIN NHẮN - HỖ TRỢ CẢ USER VÀ GUEST
     */
    public TypeResponse<MessageResponseDTO> sendMessage(SendMessageRequestDTO req) {
        try {
            UserEntity sender = getCurrentUserOrNull();
            ChatConversation conv = getOrCreateConversationFromRequest(req, sender);
            // Xác định receiver
            UserEntity receiver;
            if (sender != null && isAdmin(sender)) {
                receiver = conv.getUser() != null ? conv.getUser() : conv.getAdmin();
            } else {
                receiver = conv.getAdmin();
            }

            // Tạo message
            ChatMessage message = ChatMessage.builder()
                    .conversation(conv)
                    .sender(sender)
                    .receiver(receiver)
                    .content(req.getContent())
                    .type(req.getType())
                    .build();

            if (req.getType().equals(MessageType.PRODUCT_LINK)) {
                message.setContent(extractProductId(req.getContent()));
            }

            message = messageRepo.save(message);
            conv.setLastMessageAt(message.getSentAt());
            conversationRepo.save(conv);

            MessageResponseDTO dto = mapToMessageDTO(message);
            broadcastMessage(dto, conv.getId());
            // Auto-reply nếu là user/guest (không phải admin)
            if (sender == null || !isAdmin(sender)) {
                ChatMessage finalMessage = message;
                userRepo.findByRoleName(GuardType.ADMIN.name()).forEach(admin ->
                        notificationService.sendSystemNotification(
                                SystemNotificationType.CHAT_NEW_MESSAGE,
                                admin.getId(),
                                Map.of(
                                        "senderName", finalMessage.getSender().getFullName(),
                                        "message", finalMessage.getContent()
                                )
                        )
                );
                triggerAutoReply(conv, message);
            }
            return ResponseHelper.created(dto, ResponseMessage.SEND_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.badRequest(e.getMessage());
        }
    }

    /**
     * HELPER: LẤY HOẶC TẠO CONVERSATION TỪ REQUEST
     */
    private ChatConversation getOrCreateConversationFromRequest(SendMessageRequestDTO req, UserEntity sender) {
        if (req.getConversationId() != null) {
            return conversationRepo.findById(req.getConversationId())
                    .orElseThrow(() -> new RuntimeException("Conversation not found"));
        }
        // Tạo mới
        if (sender != null) {
            // User đã login
            Optional<ChatConversation> existing = conversationRepo.findByUserId(sender.getId());
            if (existing.isPresent()) return existing.get();

            return createNewConversation(sender, null);
        } else {
            // Guest
            if (req.getGuestSessionId() == null) {
                throw new IllegalArgumentException("guestSessionId is required for guests");
            }

            Optional<ChatConversation> existing = conversationRepo.findByGuestSessionId(req.getGuestSessionId());
            if (existing.isPresent()) return existing.get();
            return createNewConversation(null, req.getGuestSessionId());
        }
    }

    /**
     * TẠO CONVERSATION MỚI
     */
    private ChatConversation createNewConversation(UserEntity user, UUID guestSessionId) {
        UserEntity admin = getDefaultAdmin();
        String title = user != null
                ? "Khách hàng: " + user.getFullName()
                : "Guest #" + (guestSessionId != null ? guestSessionId.toString().substring(0, 8) : "Unknown");

        return conversationRepo.save(ChatConversation.builder()
                .title(title)
                .user(user)
                .guestSessionId(guestSessionId)
                .admin(admin)
                .status(ConversationStatus.ACTIVE)
                .build());
    }

    public boolean isAutoChatEnabled() {
        return autoReplyRepo.findFirstByOrderByIdAsc()
                .map(AutoReplyConfig::isAutoChatEnabled)
                .orElse(true);
    }
    /**
     * AUTO REPLY
     */
    private void triggerAutoReply(ChatConversation conv, ChatMessage userMsg) {
        // 1. Tự động tra lời theo cấu hình cài sẵn
        String userContent = userMsg.getContent().trim();
        if (userContent.isBlank()) return;
        String normalizedContent = userContent.toLowerCase()
                .replaceAll("[^a-z0-9À-ỹ\\s]", " ");
        List<AutoReplyConfig> matchingRules = autoReplyRepo
                .findByMessageContainingKeyword(normalizedContent);
        if (!matchingRules.isEmpty()) {
            AutoReplyConfig rule = matchingRules.get(0);
            sendBotReply(conv, rule.getReplyMessage());
        }
        // 2. Grok AI fallback (chỉ nếu có rule nào bật)
        if (!isAutoChatEnabled()) {
            String aiReply = AIChatService.generateReply(userMsg.getContent(), null);
            if (aiReply != null && !aiReply.contains("không hiểu") && !aiReply.contains("lỗi")) {
                sendBotReply(conv, aiReply);
            } else {
                // AI không trả lời được → chuyển cho admin
                ChatConversation convAdmin = getChatBotAdmin();
                notifyAdminUnanswered(convAdmin, conv, userMsg);
            }
        }
    }
    /** Gửi cảnh báo cho admin nếu chatbot lỗi, không phản hồi*/
    private void notifyAdminUnanswered(ChatConversation convAdmin , ChatConversation conv, ChatMessage userMsg) {
        String alert = """
        [CẢNH BÁO] Khách hàng cần hỗ trợ!
        Khách: %s
        Tin nhắn: %s
        Thời gian: %s
        → Vui lòng trả lời ngay!
        """.formatted(
                conv.getUser() != null ? conv.getUser().getFullName() : "Khách vãng lai",
                userMsg.getContent(),
                userMsg.getCreatedAt().toString()
        );
        ChatMessage alertMsg = ChatMessage.builder()
                .conversation(convAdmin)
                .sender(convAdmin.getUser())
                .receiver(convAdmin.getAdmin())
                .content(alert)
                .type(MessageType.TEXT)
                .build();
        messageRepo.save(alertMsg);

        MessageResponseDTO dto = mapToMessageDTO(alertMsg);
        broadcastMessage(dto, convAdmin.getId());
    }
    private void sendBotReply(ChatConversation conv, String reply) {
        UserEntity bot = getBotUser();
        if (!bot.isLocked()) {
            ChatMessage botMsg = ChatMessage.builder()
                    .conversation(conv)
                    .sender(bot)
                    .receiver(conv.getUser() != null ? conv.getUser() : conv.getAdmin())
                    .content(reply)
                    .type(MessageType.TEXT)
                    .build();
            messageRepo.save(botMsg);
            MessageResponseDTO dto = mapToMessageDTO(botMsg);
            broadcastMessage(dto, conv.getId());
        }
    }
    /** Lấy user bot đang họạt động */
    private UserEntity getBotUser() {
        String botEmail = AIChatService.getBotEmail();
        log.info("Bot Email : {} ", botEmail);
        return userRepo.findByEmail(botEmail)
                .orElseThrow(() -> new RuntimeException("Bot user not found: " + botEmail));
    }
    /**
     * ADMIN APIs
     */
    @Transactional
    public TypeResponse<PageDTO<ChatSessionDTO>> getActiveSessions(FilterDTO filter) {
        try {
            String sortDir = "asc".equalsIgnoreCase(filter.getSortDir()) ? "ASC" : "DESC";
            Pageable pageable = PageRequest.of(
                    filter.getPage(),
                    filter.getSize(),
                    Sort.Direction.fromString(sortDir),
                    filter.getSortBy()
            );
            Page<ChatConversation> page = conversationRepo.findActiveSessionsWithFilter(
                    filter.getSearch(),
                    filter.getStatus(),
                    filter.getFromDate(),
                    filter.getToDate(),
                    pageable
            );
            List<ChatSessionDTO> dtos = page.getContent().stream()
                    .map(this::mapToSessionDTO)
                    .toList();
            PageDTO<ChatSessionDTO> pageDTO = pageConverter.toPageDTO(
                    new PageImpl<>(dtos, pageable, page.getTotalElements())
            );
            return ResponseHelper.ok(pageDTO, ResponseMessage.FETCH_SUCCESS);
        } catch (IllegalArgumentException e) {
            return ResponseHelper.validationError("Tham số", "Sắp xếp không hợp lê");
        } catch (Exception e) {
            log.error("Lỗi khi lấy danh sách session chat", e);
            return ResponseHelper.badRequest("Hệ thống lỗi: " + e.getMessage());
        }
    }

    public TypeResponse<ChatStatisticsDTO> getStatistics() {
        try {
            long total = messageRepo.count();
            ChatStatisticsDTO response = ChatStatisticsDTO.builder()
                    .totalMessages(total)
                    .averageResponseTime(2.5)
                    .responseRate(total > 0 ? 95.0 : 0)
                    .build();
            return ResponseHelper.ok(response, ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.badRequest(e.getMessage());
        }
    }

    /**
     * HELPER METHODS
     */
    private UserEntity getDefaultAdmin() {
        List<UserEntity> admins = userRepo.findByRoleName(GuardType.ADMIN.name());
        if (admins.isEmpty()) {
            throw new RuntimeException("Không tìm thấy admin trong hệ thống");
        }
        return admins.get(0);
    }

    /**
     * : Lấy current user, trả về null nếu không có -> khách
     */
    private UserEntity getCurrentUserOrNull() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof UserEntity) {
                return (UserEntity) auth.getPrincipal();
            }
        } catch (Exception e) {
            // Ignore - guest user
        }
        return null;
    }
    private boolean isAdmin(UserEntity user) {
        if (user == null) return false;
        return user.getRoles().stream().anyMatch(r -> r.getName().equals(GuardType.ADMIN.name()));
    }

    private String extractProductId(String url) {
        return url.contains("/product/") ? url.substring(url.lastIndexOf("/") + 1) : url;
    }
    /**
     * MAPPING METHODS
     */
    private SenderDTO mapToSenderDTO(UserEntity user) {
        if (user == null) return null;
        SenderDTO dto = new SenderDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setAvatar(user.getAvatar());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhoneNumber());
        dto.setRole(user.getRoles().stream().anyMatch(r -> r.getName().equals(GuardType.ADMIN.name())) ? GuardType.ADMIN.name() :GuardType.USER.name());
        return dto;
    }

    private SenderDTO mapToGuestSenderDTO(UUID guestSessionId) {
        SenderDTO guest = new SenderDTO();
        guest.setId(null);
        guest.setFullName("Khách #" + (guestSessionId != null ? guestSessionId.toString().substring(0, 8) : ""));
        guest.setAvatar("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSq3LFYtPxGzAWByY0wFINNAaeFGMBu7KQj_w&s");
        guest.setRole("GUEST");
        return guest;
    }

    private MessageResponseDTO mapToMessageDTO(ChatMessage msg) {
        MessageResponseDTO dto = new MessageResponseDTO();
        dto.setMessageId(msg.getId());

        // Sender có thể null nếu là guest
        if (msg.getSender() != null) {
            dto.setSender(mapToSenderDTO(msg.getSender()));
        } else {
            // Tin nhắn từ guest - lấy thông tin từ conversation
            dto.setSender(mapToGuestSenderDTO(msg.getConversation().getGuestSessionId()));
        }
        dto.setContent(msg.getContent());
        dto.setType(msg.getType().name());
        dto.setSentAt(msg.getSentAt());
        dto.setIsRead(msg.isRead());

        if (msg.getType() == MessageType.PRODUCT_LINK) {
            dto.setProductUrl(msg.getContent());
        }
        return dto;
    }

    private void broadcastMessage(MessageResponseDTO dto, UUID convId) {
        messagingTemplate.convertAndSend("/topic/conversation/" + convId, dto);
    }

    private ChatSessionDTO mapToSessionDTO(ChatConversation conv) {
        ChatSessionDTO dto = new ChatSessionDTO();
        dto.setConversationId(conv.getId());
        dto.setTitle(conv.getTitle());
        dto.setParticipant(
                conv.getUser() != null
                        ? mapToSenderDTO(conv.getUser())
                        : mapToGuestSenderDTO(conv.getGuestSessionId())
        );
        if (!conv.getMessages().isEmpty()) {
            ChatMessage last = conv.getMessages().get(conv.getMessages().size() - 1);
            dto.setLastMessage(last.getContent());
            dto.setLastMessageAt(last.getSentAt());
        } else {
            dto.setLastMessage("Chưa có tin nhắn");
            dto.setLastMessageAt(conv.getStartedAt());
        }
        if (conv.getAdmin() != null) {
            dto.setUnreadCount((int) messageRepo.countByConversationIdAndIsReadFalseAndReceiverId(
                    conv.getId(), conv.getAdmin().getId()
            ));
        } else {
            dto.setUnreadCount(0);
        }
        dto.setIsOnline(true);
        return dto;
    }
    /**
     * Khởi tạo kênh chat cảnh báo cho bot - admin
     * */
    private ChatConversation getChatBotAdmin() {
        if (conversationRepo.findByTitle("Trợ Lý AI").isEmpty()) {
            UserEntity admin = getDefaultAdmin();
            UserEntity bot = getBotUser();
            ChatConversation systemConv = ChatConversation.builder()
                    .title("Trợ Lý AI")
                    .admin(admin)
                    .user(bot)
                    .guestSessionId(null)
                    .status(ConversationStatus.ACTIVE)
                    .build();
            conversationRepo.save(systemConv);
        }
        Optional<ChatConversation> conv = conversationRepo.findByTitle("Trợ Lý AI");
        return conv.get();
    }
}