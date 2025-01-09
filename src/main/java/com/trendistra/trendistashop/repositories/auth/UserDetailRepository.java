package com.trendistra.trendistashop.repositories.auth;

import com.trendistra.trendistashop.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDetailRepository extends JpaRepository<UserEntity, UUID>, JpaSpecificationExecutor<UserEntity> {
    Optional <UserEntity> findByEmail(String username);

    @Modifying
    @Query("UPDATE UserEntity u SET u.verificationCode = null, u.codeExpiry = null WHERE u.codeExpiry < :currentTime AND u.enabled = false")
    void deleteExpiredVerificationCodes(LocalDateTime currentTime);
}
