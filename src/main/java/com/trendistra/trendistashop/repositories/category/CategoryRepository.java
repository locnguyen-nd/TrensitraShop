package com.trendistra.trendistashop.repositories.category;

import com.trendistra.trendistashop.entities.category.Category;

import org.springframework.data.jpa.repository.JpaRepository;
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

}
