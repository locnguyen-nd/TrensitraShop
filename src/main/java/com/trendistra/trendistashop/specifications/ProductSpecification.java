package com.trendistra.trendistashop.specifications;

import com.trendistra.trendistashop.entities.category.Category;
import com.trendistra.trendistashop.entities.product.Product;
import com.trendistra.trendistashop.entities.product.ProductVariant;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class ProductSpecification {
    public static Specification<Product> hasName(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if(keyword == null || keyword.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            String searchTerm = "%"+ keyword.toLowerCase().trim() + "%";
            // Tìm theo tên sản phẩm
            Predicate productNamePredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    searchTerm
            );
            // Tìm theo tên category
            Predicate categoryNamePredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("category").get("name")),
                    searchTerm
            );
            return criteriaBuilder.or(productNamePredicate, categoryNamePredicate);
        };
    }
    public static Specification<Product> hasCategorySlug(String slug) {
        return (root, query, criteriaBuilder) -> {
            if (slug == null) {
                return criteriaBuilder.conjunction(); // Không thêm điều kiện nếu categoryId là null
            }
            return criteriaBuilder.equal(root.get("category").get("slug"), slug);
        };
    }
    public static Specification<Product> hasParentCategorySlug(String slug) {
        return (root, query, criteriaBuilder) -> {
            if (slug == null) {
                return criteriaBuilder.conjunction(); // Không thêm điều kiện nếu parentId là null
            }
            Join<Product, Category> categoryJoin = root.join("category");
            return criteriaBuilder.equal(categoryJoin.get("parent").get("slug"), slug);
        };
    }

    public static Specification<Product> hasGenderSlug(String slug) {
        return (root, query, criteriaBuilder) -> {
            if (slug == null) {
                return criteriaBuilder.conjunction(); // Không thêm điều kiện nếu genderId là null
            }
            Join<Product, Category> categoryJoin = root.join("category");
            return criteriaBuilder.equal(categoryJoin.get("gender").get("slug"), slug);
        };
    }
    public static Specification<Product> hasColorCode(String code) {
        return (root, query, criteriaBuilder) -> {
            if (code == null) {
                return criteriaBuilder.conjunction(); // Không thêm điều kiện nếu colorId là null
            }
            Join<Product, ProductVariant> variantJoin = root.join("productVariants", JoinType.INNER);
            return criteriaBuilder.equal(variantJoin.get("color").get("code"), code);
        };
    }
    public static Specification<Product> hasSizeValue(String value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null) {
                return criteriaBuilder.conjunction(); // Không thêm điều kiện nếu colorId là null
            }
            Join<Product, ProductVariant> variantJoin = root.join("productVariants",JoinType.INNER);
            return criteriaBuilder.equal(variantJoin.get("size").get("value"), value);
        };
    }
    public static  Specification<Product> hasStatus(Boolean status) {
        return ((root, query, criteriaBuilder) ->
             criteriaBuilder.equal(root.get("status"), status)
        );
    }
    public static  Specification<Product> hasTag(Enum tag) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("tag"), tag)
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
