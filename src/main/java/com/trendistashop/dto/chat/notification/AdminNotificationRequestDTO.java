package com.trendistashop.dto.chat.notification;

import com.trendistashop.enums.NotificationType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author Locnd
 */
@Data
public class AdminNotificationRequestDTO {
    private String title;
    private String content;
    private NotificationType type;
    private LocalDateTime scheduledAt; // null = gửi ngay
    private Set<UUID> userIds; // null = gửi all
    private boolean isDraft = true;
}
