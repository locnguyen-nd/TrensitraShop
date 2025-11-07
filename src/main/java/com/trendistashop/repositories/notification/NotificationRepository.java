package com.trendistashop.repositories.notification;

import com.trendistashop.entities.notification.Notification;
import com.trendistashop.enums.NotificationStatus;
import com.trendistashop.enums.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findByCreatedByIdOrderByCreatedAtDesc(UUID adminId);
    List<Notification> findByScheduledAtBetweenAndIsSentFalseAndIsDraftFalse(
            LocalDateTime start, LocalDateTime end
    );
    @Query("""
        SELECT n FROM Notification n
        WHERE n.createdBy.id = :adminId
          AND (:search IS NULL OR 
               LOWER(n.title) LIKE LOWER(CONCAT('%', :search, '%')) OR
               LOWER(n.content) LIKE LOWER(CONCAT('%', :search, '%')))
          AND (:status IS NULL OR 
               (:status = 'SENT' AND n.isSent = true) OR 
               (:status = 'NOT_SENT' AND n.isSent = false))
          AND (:type IS NULL OR n.type = :type)
        ORDER BY n.createdAt DESC
        """)
    Page<Notification> findByCreatedByIdWithFilter(
            @Param("adminId") UUID adminId,
            @Param("search") String search,
            @Param("status") String status,
            @Param("type") String type,
            Pageable pageable
    );
}
