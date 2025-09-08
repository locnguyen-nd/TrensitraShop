package com.trendistashop.repositories.collection;

import com.trendistashop.entities.collection.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MediaRepository extends JpaRepository<Media, UUID> {
}
