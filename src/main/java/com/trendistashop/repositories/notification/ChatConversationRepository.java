package com.trendistashop.repositories.notification;

import com.trendistashop.entities.notification.ChatConversation;
import com.trendistashop.enums.ConversationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author Locnd
 */
public interface ChatConversationRepository extends JpaRepository<ChatConversation, UUID> {
    Optional<ChatConversation> findByUserId(UUID userId);
    Optional<ChatConversation> findByGuestSessionId(UUID guestSessionId);
    List<ChatConversation> findByStatus(ConversationStatus status);
    List<ChatConversation> findByStatusOrderByLastMessageAtDesc(ConversationStatus status);

    Optional<ChatConversation> findByTitle(String title);
    @Query("""
        SELECT DISTINCT c FROM ChatConversation c
        LEFT JOIN FETCH c.messages m
        LEFT JOIN c.user u
        WHERE c.status = COALESCE(:status, c.status)
          AND (:fromDate IS NULL OR c.startedAt >= :fromDate)
          AND (:toDate IS NULL OR c.startedAt <= :toDate)
          AND (
            :search IS NULL OR 
            LOWER(c.title) LIKE LOWER(CONCAT('%', :search, '%')) OR
            (u.email IS NOT NULL AND LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))) OR
            (c.guestSessionId IS NOT NULL AND CAST(c.guestSessionId AS string) LIKE CONCAT('%', :search, '%'))
          )
        """)
    Page<ChatConversation> findActiveSessionsWithFilter(
            @Param("search") String search,
            @Param("status") ConversationStatus status,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            Pageable pageable
    );
}
