package com.trendistashop.entities.notification;

import com.trendistashop.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * @author Locnd
 */
@Entity
@Table(name = "notification_recipient",
        uniqueConstraints = @UniqueConstraint(columnNames = {"notification_id", "user_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"notification", "user"})
public class NotificationRecipient {
    @GeneratedValue
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id", nullable = false)
    private Notification notification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    private LocalDateTime sentAt;

    private boolean isRead = false;
}
