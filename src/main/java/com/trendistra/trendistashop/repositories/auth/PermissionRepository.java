package com.trendistra.trendistashop.repositories.auth;

import com.trendistra.trendistashop.entities.user.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PermissionRepository extends JpaRepository <PermissionEntity, UUID>{
    boolean existsByName(String name);
}
