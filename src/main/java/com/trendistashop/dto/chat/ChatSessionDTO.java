package com.trendistashop.dto.chat;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * @author Locnd
 */
@Data
public class ChatSessionDTO {
    private UUID conversationId;
    private String title;
    private SenderDTO participant;
    private String lastMessage;
    private LocalDateTime lastMessageAt;
    private Integer unreadCount;
    private Boolean isOnline;
}
