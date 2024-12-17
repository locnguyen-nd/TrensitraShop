package com.trendistra.trendistashop.repositories.product;

import com.trendistra.trendistashop.entities.product.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface SizeRepository extends JpaRepository<Size, UUID> {
}
