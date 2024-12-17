package com.trendistra.trendistashop.repositories.product;

import com.trendistra.trendistashop.entities.product.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ColorRepository extends JpaRepository<Color, UUID> {
    boolean existsByName(String name);
}
