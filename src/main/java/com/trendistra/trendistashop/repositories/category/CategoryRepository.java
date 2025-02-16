package com.trendistra.trendistashop.repositories.category;

import com.trendistra.trendistashop.entities.category.Category;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface CategoryRepository extends JpaRepository<Category , UUID> {
    Optional<Category> findBySlug(String slug);
    List<Category> findByParentId(UUID parent);
    List<Category> findByGenderId(UUID gender);
    List<Category> findByGenderSlug(String slug);
    @Query("SELECT DISTINCT c.slug FROM Category c " +
            "WHERE LOWER(c.slug) LIKE %:keyword% " +
            "ORDER BY CASE " +
            "  WHEN LOWER(c.slug) = :keyword THEN 0 " +
            "  WHEN LOWER(c.slug) LIKE :keyword || '%' THEN 1 " +
            "  ELSE 2 END, c.slug")
    List<String> findCategorySlugs(
            @Param("keyword") String keyword,
            Pageable pageable
    );
}
