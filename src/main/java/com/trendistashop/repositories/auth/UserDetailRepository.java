package com.trendistashop.repositories.auth;

import com.trendistashop.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public interface UserDetailRepository extends JpaRepository<UserEntity, UUID>, JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByEmail(String username);

    @Modifying
    @Query("UPDATE UserEntity u SET u.verificationCode = null, u.codeExpiry = null WHERE u.codeExpiry < :currentTime AND u.enabled = false")
    void deleteExpiredVerificationCodes(LocalDateTime currentTime);
    @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE r.name = :roleName")
    List<UserEntity> findByRoleName(String roleName);
    @Query("SELECT u FROM UserEntity u")
    Stream<UserEntity> findAllAsStream();
    default List<UserEntity> findAllByIdInChunks() {
        try (Stream<UserEntity> stream = findAllAsStream()) {
            return stream.collect(Collectors.toList());
        }
    }
}
