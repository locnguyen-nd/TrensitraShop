package com.trendistashop.repositories.notification;

import com.trendistashop.entities.notification.AutoReplyConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author Locnd
 */
public interface AutoReplyConfigRepository extends JpaRepository<AutoReplyConfig, UUID> {
    List<AutoReplyConfig> findByEnabledTrue();
    Optional<AutoReplyConfig> findFirstByOrderByIdAsc();
}
