package com.trendistra.trendistashop.repositories.product;

import com.trendistra.trendistashop.entities.product.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface DiscountRepository extends JpaRepository<Discount , UUID> {
    boolean existsByCode(String code);
    Discount findDiscountByCode(String code);
}
