package com.trendistra.trendistashop.entities.notification;

import com.trendistra.trendistashop.entities.user.UserEntity;
import com.trendistra.trendistashop.enums.NotificationStatus;
import com.trendistra.trendistashop.enums.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "notification")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue
    private UUID id;
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    private String message;
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    private LocalDateTime createdAt;
    // Reference data - could be an order ID, product ID, etc.
    private String referenceId;

    // URL for redirection when notification is clicked
    private String redirectUrl;

    // For notification grouping and filtering
    private String category;
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        status = NotificationStatus.UNREAD;
    }
}
