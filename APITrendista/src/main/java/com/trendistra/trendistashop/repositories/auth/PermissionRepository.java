package com.trendistra.trendistashop.repositories.auth;

import com.trendistra.trendistashop.entities.auth.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository <PermissionEntity, Long>{
}
