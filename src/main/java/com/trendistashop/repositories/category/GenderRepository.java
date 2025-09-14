package com.trendistashop.repositories.category;

import com.trendistashop.entities.category.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GenderRepository extends JpaRepository<Gender, UUID> {
    boolean existsByName (String name);
    @Query("SELECT DISTINCT c.name,c.slug FROM Gender c " +
            "WHERE LOWER(c.slug) LIKE %:keyword% " +
            "ORDER BY CASE " +
            "  WHEN LOWER(c.slug) = :keyword THEN 0 " +
            "  WHEN LOWER(c.slug) LIKE :keyword || '%' THEN 1 " +
            "  ELSE 2 END, c.slug")
    List<Gender> searchGenderWithKeyword(
            @Param("keyword") String keyword);
}
