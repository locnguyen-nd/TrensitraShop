package com.trendistra.trendistashop.repositories.product;

import com.trendistra.trendistashop.entities.product.Product;
import com.trendistra.trendistashop.enums.ProductTagEnum;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaSpecificationExecutor<Product>, JpaRepository<Product , UUID>  {
    boolean existsByName(String name);
    List<Product> findProductsByTag(ProductTagEnum tag);

}
