package com.trendistashop.repositories.notification;

import com.trendistashop.entities.notification.NotificationRecipient;
import com.trendistashop.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author Locnd
 */
@Repository
public interface NotificationRecipientRepository extends JpaRepository<NotificationRecipient, UUID> {
    List<NotificationRecipient> findByUserOrderBySentAtDesc(UserEntity user);
    Optional<NotificationRecipient> findByNotificationIdAndUserId(UUID notificationId, UUID userId);
    int countByNotificationId(UUID notificationId);

    List<NotificationRecipient> findByUserAndIsReadFalse(UserEntity user);
}
