package com.trendistashop.repositories.collection;

import com.trendistashop.entities.collection.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CollectionRepository extends JpaRepository<Collection, UUID> {
    Collection findBySlug(String slug);
}
