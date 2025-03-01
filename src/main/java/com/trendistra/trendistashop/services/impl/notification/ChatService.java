package com.trendistra.trendistashop.services.impl.notification;

import com.trendistra.trendistashop.dto.response.ChatMessageDTO;
import com.trendistra.trendistashop.dto.response.ConversationDTO;
import com.trendistra.trendistashop.dto.response.ProductDTO;
import com.trendistra.trendistashop.entities.notification.ChatMessage;
import com.trendistra.trendistashop.entities.user.UserEntity;
import com.trendistra.trendistashop.repositories.auth.UserDetailRepository;
import com.trendistra.trendistashop.repositories.notification.ChatMessageRepository;
import com.trendistra.trendistashop.services.MessageBroadcaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;
    private final UserDetailRepository userDetailRepository;
    private final UserDetailsService userDetailsService;
    private final MessageBroadcaster messageBroadcaster;
    private final RestTemplate restTemplate;
    private static final Pattern URL_PATTERN = Pattern.compile(
            "^(http?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$",
            Pattern.CASE_INSENSITIVE
    );
    private static final Pattern PRODUCT_URL_PATTERN = Pattern.compile(
            "/api/v1/products/slug/([^/?]+)" // Chỉ lấy slug sau /slug/
    );
    @Autowired
    public ChatService(ChatMessageRepository chatMessageRepository, UserDetailRepository userDetailRepository, UserDetailsService userDetailsService, MessageBroadcaster messageBroadcaster, RestTemplate restTemplate) {
        this.chatMessageRepository = chatMessageRepository;
        this.userDetailRepository = userDetailRepository;
        this.userDetailsService = userDetailsService;
        this.messageBroadcaster = messageBroadcaster;
        this.restTemplate = restTemplate;
    }
    public ChatMessageDTO sendMessage(ChatMessageDTO dto , Principal principal) {
        UserEntity sender = (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
        UserEntity receiver = userDetailRepository.findById(dto.getReceiverId()).orElseThrow();
        ChatMessage message = new ChatMessage();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setMessage(dto.getMessage());
        if (dto.getMessageType() != null){
            message.setMessageType(dto.getMessageType());
            message.setContentUrl(dto.getContentUrl());
            message.setThumbnailUrl(dto.getThumbnailUrl());
            message.setContentMetadata(dto.getContentMetadata());
        } else {
            String content = dto.getMessage().trim();
            if (isUrl(content)) {
                message.setMessageType("LINK");
                message.setContentUrl(content);

                Matcher matcher = PRODUCT_URL_PATTERN.matcher(content);
                if (matcher.find()) {
                    String slug = matcher.group(1);

                    ProductDTO productInfo = fetchProductInfo(slug);
                    if (productInfo != null) {
                        message.setMessageType("PRODUCT_LINK");
                        message.setMessage("Shared a product: " + productInfo.getName());
                        message.setContentUrl("/product/" + slug);
                        message.setThumbnailUrl(productInfo.getUrlImage());
                        message.setContentMetadata(String.format(
                                "{\"productId\":\"%s\",\"productName\":\"%s\",\"productPrice\":\"%s\"}",
                                slug, productInfo.getName(), productInfo.getPrice()
                        ));
                    }
                }
            } else {
                message.setMessageType("TEXT");
            }
        }
        ChatMessage savedMessage = chatMessageRepository.save(message);
        ChatMessageDTO responseDto = ChatMessageDTO.fromEntity(savedMessage);

        messageBroadcaster.broadcastMessage(responseDto);
        return responseDto;
    }

    public ChatMessageDTO sendProductLink(Principal principal, UUID receiverId, String productId, String productName, String productImageUrl, String productPrice) {
        UserEntity sender = (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
        UserEntity receiver = userDetailRepository.findById(receiverId).orElseThrow();
        ChatMessage message = new ChatMessage();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setMessage("Shared a product: " + productName);
        message.setMessageType("PRODUCT_LINK");
        message.setContentUrl("/product/" + productId); // Link to product page
        message.setThumbnailUrl(productImageUrl);
        message.setContentMetadata(String.format(
                "{\"productId\":\"%s\",\"productName\":\"%s\",\"productPrice\":\"%s\"}",
                productId, productName, productPrice));

        ChatMessage savedMessage = chatMessageRepository.save(message);
        ChatMessageDTO responseDto = ChatMessageDTO.fromEntity(savedMessage);

        messageBroadcaster.broadcastMessage(responseDto);
        return responseDto;
    }
    public ChatMessageDTO sendImage(Principal principal, UUID receiverId, String imageUrl, String caption) {
        UserEntity sender = (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
        UserEntity receiver = userDetailRepository.findById(receiverId).orElseThrow();

        ChatMessage message = new ChatMessage();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setMessage(caption != null ? caption : "Shared an image");
        message.setMessageType("IMAGE");
        message.setContentUrl(imageUrl);
        message.setThumbnailUrl(imageUrl); // Could be a smaller version in a real implementation

        ChatMessage savedMessage = chatMessageRepository.save(message);
        ChatMessageDTO responseDto = ChatMessageDTO.fromEntity(savedMessage);

        messageBroadcaster.broadcastMessage(responseDto);
        return responseDto;
    }
    public List<?> getChatHistory(Principal principal, UUID receiverId) {
        System.out.println(principal.getName());
        UserEntity sender = (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
        boolean isAdmin = sender.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        System.out.println(isAdmin);
        UUID senderId = UUID.fromString(sender.getId().toString());
        if (isAdmin && !senderId.equals(receiverId)) {
            List<ChatMessage> messageToAdmin = chatMessageRepository.findByReceiverId(senderId);
            Map<UUID, List<ChatMessage>> messagesBySender = messageToAdmin.stream()
                    .collect(Collectors.groupingBy(m -> m.getSender().getId()));
            // Chuyen thanh danh sach tin nhăn theo user gửi tới admin
            List<ConversationDTO> conversations = messagesBySender.entrySet().stream()
                    .map(entry -> {
                        UUID userId = entry.getKey();
                        List<ChatMessage> userMessages = entry.getValue();
                        UserEntity user = userDetailRepository.findById(userId).orElseThrow();
                        ConversationDTO convo = new ConversationDTO();
                        convo.setUserId(userId);
                        convo.setUsername(user.getUsername());
                        convo.setMessages(userMessages.stream()
                                .map(ChatMessageDTO::fromEntity)
                                .sorted((m1, m2) -> m1.getCreatedAt().compareTo(m2.getCreatedAt())) // Sắp xếp tin nhắn theo thời gian
                                .collect(Collectors.toList()));
                        return convo;
                    })
                    .sorted((c1, c2) -> {
                        // Sắp xếp cuộc hội thoại theo tin nhắn mới nhất
                        LocalDateTime t1 = LocalDateTime.parse(c1.getMessages().get(c1.getMessages().size() - 1).getCreatedAt());
                        LocalDateTime t2 = LocalDateTime.parse(c2.getMessages().get(c2.getMessages().size() - 1).getCreatedAt());
                        return t2.compareTo(t1); // Mới nhất ở trên
                    })
                    .collect(Collectors.toList());
            return conversations;
        } else {
            List<ChatMessage> chats = chatMessageRepository.findChatHistoryBetweenUsers(senderId, receiverId);
            return chats.stream()
                    .map(ChatMessageDTO::fromEntity)
                    .collect(Collectors.toList());
            }
        }
    private boolean isUrl(String text) {
        return URL_PATTERN.matcher(text).matches();
    }

    private ProductDTO fetchProductInfo(String slug) {
        String apiUrl = String.format("http://localhost:8080/api/v1/products/slug/%s", slug);
        try {
            return restTemplate.getForObject(apiUrl, ProductDTO.class);
        } catch (Exception e) {
            System.out.println("Error fetching product info: " + e.getMessage());
            return null;
        }
    }
}

