package com.trendistashop.repositories.category;

import com.trendistashop.entities.category.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GenderRepository extends JpaRepository<Gender, UUID> {
    boolean existsByName (String name);
}
