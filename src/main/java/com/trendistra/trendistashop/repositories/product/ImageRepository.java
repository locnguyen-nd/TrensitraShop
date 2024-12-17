package com.trendistra.trendistashop.repositories.product;

import com.trendistra.trendistashop.entities.product.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<ProductImage , UUID> {
    List<ProductImage> findByProductIdAndColorId(UUID productId, UUID colorId);
    @Query("SELECT p FROM ProductImage p WHERE  p.product.id = :productId AND p.isThumbnail = true")
    ProductImage findImageThumbnail( @Param("productId") UUID productId);
}
