package com.trendistashop.dto.chat.notification;

import com.trendistashop.enums.NotificationType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * @author Locnd
 */
@Data
public class AdminNotificationResponseDTO {
    private UUID id;
    private String title;
    private String content;
    private NotificationType type;
    private UUID createdById;
    private LocalDateTime createdAt;
    private String createdByName;
    private LocalDateTime scheduledAt;
    private boolean isSent;
    private boolean isDraft;
    private int recipientCount;
}
