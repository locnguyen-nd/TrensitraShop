package com.trendistashop.repositories.collection;

import com.trendistashop.entities.collection.SubCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface SubCollectionRepository extends JpaRepository<SubCollection, UUID> {
}
