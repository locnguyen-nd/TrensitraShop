package com.trendistra.trendistashop.repositories.collection;

import com.trendistra.trendistashop.entities.collection.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CollectionRepository extends JpaRepository<Collection, UUID> {
    Collection findBySlug(String slug);
}
