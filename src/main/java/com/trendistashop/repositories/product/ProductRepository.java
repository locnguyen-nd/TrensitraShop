package com.trendistashop.repositories.product;

import com.trendistashop.entities.product.Product;
import com.trendistashop.enums.ProductTagEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaSpecificationExecutor<Product>, JpaRepository<Product , UUID>  {
    boolean existsByName(String name);
    Page<Product> findProductsByTag(ProductTagEnum tag, Pageable pageable);

    Product findProductsBySlug(String slug);
    @Query(value = """
    SELECT DISTINCT p.* FROM product p
    WHERE LOWER(p.name) LIKE :pattern 
       OR LOWER(p.slug) LIKE :pattern
    ORDER BY 
        CASE 
            WHEN LOWER(p.name) = :exact THEN 0
            WHEN LOWER(p.name) LIKE :startsWith THEN 1
            WHEN LOWER(p.slug) = :exact THEN 2
            WHEN LOWER(p.slug) LIKE :startsWith THEN 3
            ELSE 4 
        END,
        p.name
    """,
            countQuery = "SELECT COUNT(DISTINCT p.id) FROM product p WHERE LOWER(p.name) LIKE :pattern OR LOWER(p.slug) LIKE :pattern",
            nativeQuery = true)
    List<Product> findProductNames(
            @Param("pattern") String pattern,
            @Param("exact") String exact,
            @Param("startsWith") String startsWith,
            Pageable pageable
    );
}
