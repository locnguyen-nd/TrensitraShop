package com.trendistra.trendistashop.repositories.collection;

import com.trendistra.trendistashop.entities.collection.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MediaRepository extends JpaRepository<Media, UUID> {
}
