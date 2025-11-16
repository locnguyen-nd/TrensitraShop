package com.trendistashop.specifications;

import com.trendistashop.dto.request.DiscountRequest;
import com.trendistashop.entities.product.Discount;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class DiscountSpec {
    public static Specification<Discount> filter(DiscountRequest discountRequest) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Lọc theo code
            if (discountRequest.getCode() != null && !discountRequest.getCode().trim().isEmpty()) {
                String searchTerm = "%" + discountRequest.getCode().toLowerCase().trim() + "%";
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("code")),
                        searchTerm
                ));
            }

            // Lọc theo discountType
            if (discountRequest.getDiscountType() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("discountType"),
                        discountRequest.getDiscountType()
                ));
            }

            // Lọc theo discountApply
            if (discountRequest.getDiscountApplyFor() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("discountApplyFor"),
                        discountRequest.getDiscountApplyFor()
                ));
            }

            // Lọc theo discountValue
            if (discountRequest.getDiscountValue() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("discountValue"),
                        discountRequest.getDiscountValue()
                ));
            }

            // Lọc theo maxDiscountValue
            if (discountRequest.getMaxDiscountValue() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("maxDiscountValue"),
                        discountRequest.getMaxDiscountValue()
                ));
            }
            
            // Lọc theo maxDiscountValue range (from)
            if (discountRequest.getMaxDiscountValueFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("maxDiscountValue"),
                        discountRequest.getMaxDiscountValueFrom()
                ));
            }
            
            // Lọc theo maxDiscountValue range (to)
            if (discountRequest.getMaxDiscountValueTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("maxDiscountValue"),
                        discountRequest.getMaxDiscountValueTo()
                ));
            }

            // Lọc theo minOrderValue
            if (discountRequest.getMinOrderValue() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("minOrderValue"),
                        discountRequest.getMinOrderValue()
                ));
            }
            
            // Lọc theo minOrderValue range (from)
            if (discountRequest.getMinOrderValueFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("minOrderValue"),
                        discountRequest.getMinOrderValueFrom()
                ));
            }
            
            // Lọc theo minOrderValue range (to)
            if (discountRequest.getMinOrderValueTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("minOrderValue"),
                        discountRequest.getMinOrderValueTo()
                ));
            }

            // Lọc theo startDate
            if (discountRequest.getStartDate() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("startDate"),
                        discountRequest.getStartDate()
                ));
            }

            // Lọc theo endDate
            if (discountRequest.getEndDate() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("endDate"),
                        discountRequest.getEndDate()
                ));
            }

            // Lọc theo isActive
            if (discountRequest.getIsActive() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("isActive"),
                        discountRequest.getIsActive()
                ));
            }

            // Kết hợp tất cả các predicates bằng AND
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
