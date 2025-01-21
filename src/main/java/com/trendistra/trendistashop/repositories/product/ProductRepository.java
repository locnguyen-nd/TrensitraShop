package com.trendistra.trendistashop.repositories.product;

import com.trendistra.trendistashop.entities.product.Product;
import com.trendistra.trendistashop.enums.ProductTagEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    @Query("SELECT DISTINCT p.name FROM Product p " +
            "WHERE LOWER(p.name) LIKE %:keyword% " +
            "ORDER BY CASE " +
            "  WHEN LOWER(p.name) = :keyword THEN 0 " +
            "  WHEN LOWER(p.name) LIKE :keyword || '%' THEN 1 " +
            "  ELSE 2 END, p.name")
    List<String> findProductNames(
            @Param("keyword") String keyword,
            Pageable pageable
    );
}
