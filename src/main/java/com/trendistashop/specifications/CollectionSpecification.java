package com.trendistashop.specifications;

import com.trendistashop.entities.BaseEntity;
import com.trendistashop.entities.collection.Collection;
import com.trendistashop.entities.collection.SubTheme;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CollectionSpecification {
    public static Specification<Collection> withFilters(Boolean status, String keyword) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            if (keyword != null && !keyword.isBlank()) {
                Predicate parentPredicate = criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + keyword.toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("slug")), "%" + keyword.toLowerCase() + "%")
                );
                // OR predicate cho subTheme name (nếu có keyword, thêm vào OR tổng để tránh yêu cầu bắt buộc match cả hai)
                Predicate subThemePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.join("subThemes").get("name")),
                        "%" + keyword.toLowerCase() + "%"
                );
                predicates.add(criteriaBuilder.or(parentPredicate, subThemePredicate));
            }
            assert query != null;
            query.orderBy(criteriaBuilder.asc(root.get("orderIndex")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
