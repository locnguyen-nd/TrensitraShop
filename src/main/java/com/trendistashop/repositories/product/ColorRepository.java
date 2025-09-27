package com.trendistashop.repositories.product;

import com.trendistashop.entities.product.Color;
import com.trendistashop.entities.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ColorRepository extends JpaSpecificationExecutor<Color>, JpaRepository<Color, UUID> {
    boolean existsByName(String name);
}
