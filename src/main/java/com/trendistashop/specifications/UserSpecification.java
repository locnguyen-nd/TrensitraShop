package com.trendistashop.specifications;
import com.trendistashop.entities.user.UserEntity;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    public static Specification<UserEntity> hasRoleName(String roleName) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("roles").get("name"),roleName));
    }
}
