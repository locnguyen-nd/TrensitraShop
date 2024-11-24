package com.trendistra.trendistashop.repositories.auth;

import com.trendistra.trendistashop.entities.auth.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String roleName);
}
