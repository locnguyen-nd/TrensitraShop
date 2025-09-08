package com.trendistashop.repositories.auth;

import java.time.LocalDateTime;
import java.util.UUID;

import com.trendistashop.entities.user.VerificationAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationAttemptRepository extends JpaRepository<VerificationAttempt, UUID> {
    int countByUserIdAndCreatedAtAfter(UUID userId, LocalDateTime time);
}
