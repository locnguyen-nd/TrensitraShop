package com.trendistashop.specifications;

import com.trendistashop.entities.Banner;
import com.trendistashop.enums.BannerTypeEnum;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BannerSpecification {

    public static Specification<Banner> withFilters(BannerTypeEnum type, String event, Boolean isActive) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (type != null) {
                predicates.add(criteriaBuilder.equal(root.get("type"), type));
            }
            if (event != null && !event.isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("event"), event));
            }
            if (isActive != null) {
                predicates.add(criteriaBuilder.equal(root.get("isActive"), isActive));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}