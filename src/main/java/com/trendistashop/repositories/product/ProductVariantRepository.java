package com.trendistashop.repositories.product;

import com.trendistashop.entities.product.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, UUID> {
    List<ProductVariant> findByProductId(UUID productId);
    @Query("SELECT v.stockQuantity FROM ProductVariant v WHERE v.id = :variantId")
    Integer findStockById(@Param("variantId") UUID variantId);
}
