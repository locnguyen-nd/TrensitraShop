package com.trendistra.trendistashop.entities.notification;

import com.trendistra.trendistashop.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "chat_message")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserEntity sender;
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private UserEntity receiver;
    private String message;
    private LocalDateTime createdAt;
    private boolean isRead;
    // New fields to support rich content
    private String messageType; // TEXT, IMAGE, PRODUCT_LINK, FILE
    private String contentUrl; // URL for image, product, or file
    private String thumbnailUrl; // For product thumbnail or image preview
    private String contentMetadata; // JSON string with metadata (product info, image details, etc.)

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        isRead = false;
        if (messageType == null) {
            messageType = "TEXT"; // Default type
        }
    }
}
