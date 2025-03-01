package com.trendistra.trendistashop.repositories.notification;

import com.trendistra.trendistashop.entities.notification.Notification;
import com.trendistra.trendistashop.enums.NotificationStatus;
import com.trendistra.trendistashop.enums.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    Page<Notification> findByUserIdAndType(UUID userId, NotificationType type, Pageable pageable);
    Page<Notification> findByUserIdAndStatus(UUID userId, NotificationStatus status, Pageable pageable);
    Page<Notification> findByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);
}
