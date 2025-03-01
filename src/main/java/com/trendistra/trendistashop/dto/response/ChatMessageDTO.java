package com.trendistra.trendistashop.dto.response;

import com.trendistra.trendistashop.entities.notification.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDTO {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private UUID id;
    private UUID senderId;
    private UUID receiverId;
    private String senderName;
    private String message;
    private String createdAt;
    private boolean isRead;
    // New fields to support rich content
    private String messageType; // TEXT, IMAGE, PRODUCT_LINK, FILE
    private String contentUrl; // URL for image, product, or file
    private String thumbnailUrl; // For product thumbnail or image preview
    private String contentMetadata; // JSON string with metadata

    public static ChatMessageDTO fromEntity(ChatMessage entity) {

        return ChatMessageDTO.builder()
                .id(entity.getId())
                .senderId(entity.getSender().getId())
                .receiverId(entity.getReceiver().getId())
                .senderName(entity.getSender().getUsername()) // Assuming UserEntity has a username field
                .message(entity.getMessage())
                .createdAt(entity.getCreatedAt().format(FORMATTER))
                .isRead(entity.isRead())
                .messageType(entity.getMessageType())
                .contentUrl(entity.getContentUrl())
                .thumbnailUrl(entity.getThumbnailUrl())
                .contentMetadata(entity.getContentMetadata())
                .build();
    }

}
