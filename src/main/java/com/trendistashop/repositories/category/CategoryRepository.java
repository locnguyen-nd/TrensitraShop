package com.trendistashop.repositories.category;

import com.trendistashop.entities.category.Category;

import com.trendistashop.entities.category.Gender;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
        Category findBySlug(String slug);

        List<Category> findByParentId(UUID parent);

        List<Category> findByGenderId(UUID gender);
        @Query("""
        SELECT c FROM Category c
        WHERE (:slug IS NULL OR c.gender.slug = :slug)
          AND (
               :isActive IS NULL
            OR (:isActive = TRUE AND c.deletedAt IS NULL)
            OR (:isActive = FALSE AND c.deletedAt IS NOT NULL)
          )
    """)
        List<Category> findByGenderSlug(
                @Param("slug") String slug,
                @Param("isActive") Boolean isActive
        );
    @Query(value = """
    SELECT DISTINCT c.name, c.slug FROM Category c
    WHERE LOWER(c.name) LIKE :pattern 
       OR LOWER(c.slug) LIKE :pattern
    ORDER BY 
        CASE 
            WHEN LOWER(c.slug) = :exact THEN 0
            WHEN LOWER(c.slug) LIKE :startsWith THEN 1
            WHEN LOWER(c.name) = :exact THEN 2
            WHEN LOWER(c.name) LIKE :startsWith THEN 3
            ELSE 4 
        END,
        c.slug
    """,
            countQuery = "SELECT COUNT(DISTINCT c.id) FROM Category c WHERE LOWER(c.name) LIKE :pattern OR LOWER(c.slug) LIKE :pattern",
            nativeQuery = true)
    List<Object[]> findCategoryNameAndSlugs(
            @Param("pattern") String pattern,
            @Param("exact") String exact,
            @Param("startsWith") String startsWith,
            Pageable pageable
    );
        @Query("SELECT c FROM Category c LEFT JOIN FETCH c.parent WHERE c.slug = :slug")
        Category findBySlugWithParent(@Param("slug") String slug);
        @Query("SELECT DISTINCT c.name,c.slug FROM Category c " +
                "WHERE LOWER(c.slug) LIKE %:keyword% " +
                "ORDER BY CASE " +
                "  WHEN LOWER(c.slug) = :keyword THEN 0 " +
                "  WHEN LOWER(c.slug) LIKE :keyword || '%' THEN 1 " +
                "  ELSE 2 END, c.slug")
        List<Category> searchWithKeyword(
                @Param("keyword") String keyword);
}
