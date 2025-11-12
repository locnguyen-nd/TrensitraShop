package com.trendistashop.repositories.notification;

import com.trendistashop.entities.notification.AutoReplyConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author Locnd
 */
public interface AutoReplyConfigRepository extends JpaRepository<AutoReplyConfig, UUID> {
    @Query("""
    SELECT r FROM AutoReplyConfig r
    JOIN r.triggerKeywords tk
    WHERE LOWER(:message) LIKE LOWER(CONCAT('%', tk.keyword, '%'))
    AND r.enabled = true
    """)
    List<AutoReplyConfig> findByMessageContainingKeyword(@Param("message") String message);
    Optional<AutoReplyConfig> findFirstByOrderByIdAsc();
}
