package com.trendistra.trendistashop.repositories.auth;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trendistra.trendistashop.entities.user.VerificationAttempt;

@Repository
public interface VerificationAttemptRepository extends JpaRepository<VerificationAttempt, UUID> {
    int countByUserIdAndCreatedAtAfter(UUID userId, LocalDateTime time);
}
