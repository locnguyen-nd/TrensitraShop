package com.trendistra.trendistashop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDTO {
    private UUID id;
    private String sender;
    private String content;
    private long timestamp;
    private UUID userId;
    private UserDetailDTO user;
    private boolean isFromAdmin;
    private boolean isRead;
}
