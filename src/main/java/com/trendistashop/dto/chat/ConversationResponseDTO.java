package com.trendistashop.dto.chat;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Locnd
 */
@Data
public class ConversationResponseDTO {
  private UUID conversationId;
  private String title;
  private SenderDTO participant;
  private SenderDTO admin;
  private LocalDateTime startedAt;
  private LocalDateTime lastMessageAt;
  private int unreadCount;
  private List<MessageResponseDTO> messages;
}
