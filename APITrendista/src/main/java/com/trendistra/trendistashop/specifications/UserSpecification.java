package com.trendistra.trendistashop.specifications;

import com.trendistra.trendistashop.entities.auth.UserEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class UserSpecification {
    public static Specification<UserEntity> hasRoleName(String roleName) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("roles").get("name"),roleName));
    }
}
