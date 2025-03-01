package com.trendistra.trendistashop.dto.response;

import com.trendistra.trendistashop.entities.notification.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private UUID id;
    private UUID userId;
    private String type;
    private String message;
    private String status;
    private LocalDateTime createdAt;
    private String referenceId;
    private String redirectUrl;
    private String category;
    public static NotificationDTO fromEntity(Notification entity) {
        return NotificationDTO.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .type(entity.getType().name())
                .message(entity.getMessage())
                .status(entity.getStatus().name())
                .createdAt(entity.getCreatedAt())
                .referenceId(entity.getReferenceId())
                .redirectUrl(entity.getRedirectUrl())
                .category(entity.getCategory())
                .build();
    }
}

