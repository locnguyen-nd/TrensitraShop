package com.trendistashop.repositories.product;

import com.trendistashop.dto.request.DiscountRequest;
import com.trendistashop.entities.product.Discount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DiscountRepository extends JpaRepository<Discount, UUID> {
    boolean existsByCode(String code);
    Discount findDiscountByCode(String code);
    Page<Discount> findAll(Specification<Discount> spec, Pageable pageable);
//    List<Discount> findAllByIsActiveTrueAndEndDateBeforeNow(Date now);
}
