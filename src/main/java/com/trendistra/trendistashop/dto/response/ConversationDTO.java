package com.trendistra.trendistashop.dto.response;

import lombok.Data;

import java.util.List;
import java.util.UUID;
@Data
public class ConversationDTO {
    private UUID userId;          // ID của User gửi tin nhắn tới Admin
    private String username;      // Tên của User
    private List<ChatMessageDTO> messages; // Danh sách tin nhắn giữa User và Admin
}
