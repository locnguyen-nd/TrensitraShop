package com.trendistra.trendistashop.specifications;

import com.trendistra.trendistashop.entities.category.Category;
import com.trendistra.trendistashop.entities.product.Product;
import com.trendistra.trendistashop.entities.product.ProductVariant;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class ProductSpecification {
    public static Specification<Product> hasCategoryId(UUID categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) {
                return criteriaBuilder.conjunction(); // Không thêm điều kiện nếu categoryId là null
            }
            return criteriaBuilder.equal(root.get("category").get("id"), categoryId);
        };
    }
    public static Specification<Product> hasParentCategoryId(UUID parentId) {
        return (root, query, criteriaBuilder) -> {
            if (parentId == null) {
                return criteriaBuilder.conjunction(); // Không thêm điều kiện nếu parentId là null
            }
            Join<Product, Category> categoryJoin = root.join("category");
            return criteriaBuilder.equal(categoryJoin.get("parent").get("id"), parentId);
        };
    }

    public static Specification<Product> hasGenderId(UUID genderId) {
        return (root, query, criteriaBuilder) -> {
            if (genderId == null) {
                return criteriaBuilder.conjunction(); // Không thêm điều kiện nếu genderId là null
            }
            Join<Product, Category> categoryJoin = root.join("category");
            return criteriaBuilder.equal(categoryJoin.get("gender").get("id"), genderId);
        };
    }
    public static Specification<Product> hasColorId(UUID colorId) {
        return (root, query, criteriaBuilder) -> {
            if (colorId == null) {
                return criteriaBuilder.conjunction(); // Không thêm điều kiện nếu colorId là null
            }
            Join<Product, ProductVariant> variantJoin = root.join("productVariants", JoinType.INNER);
            return criteriaBuilder.equal(variantJoin.get("color").get("id"), colorId);
        };
    }
    public static Specification<Product> hasSizeId(UUID sizeId) {
        return (root, query, criteriaBuilder) -> {
            if (sizeId == null) {
                return criteriaBuilder.conjunction(); // Không thêm điều kiện nếu colorId là null
            }
            Join<Product, ProductVariant> variantJoin = root.join("productVariants",JoinType.INNER);
            return criteriaBuilder.equal(variantJoin.get("size").get("id"), sizeId);
        };
    }
    public static  Specification<Product> hasStatus(Boolean status) {
        return ((root, query, criteriaBuilder) ->
             criteriaBuilder.equal(root.get("status"), status)
        );
    }
    // Bộ lọc giá
    public static Specification<Product> hasPriceBetween(Double minPrice, Double maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice == null && maxPrice == null) {
                return criteriaBuilder.conjunction();
            }
            if (minPrice != null && maxPrice != null) {
                return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
            }
            if (minPrice != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
        };
    }
}
