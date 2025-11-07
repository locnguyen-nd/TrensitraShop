package com.trendistashop.dto.chat.notification;

import com.trendistashop.enums.NotificationType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * @author Locnd
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class UserNotificationDTO {
    private UUID notificationId;
    private String title;
    private String content;
    private NotificationType type;
    private LocalDateTime sentAt;
    private Boolean isRead;
}
