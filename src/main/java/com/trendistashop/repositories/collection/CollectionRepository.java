package com.trendistashop.repositories.collection;

import com.trendistashop.entities.collection.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface CollectionRepository extends JpaRepository<Collection, UUID>, JpaSpecificationExecutor<Collection> {
    Collection findBySlug(String slug);
    Collection findByName(String name);
}
