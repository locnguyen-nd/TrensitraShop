package com.trendistra.trendistashop.entities.product;

import com.trendistra.trendistashop.entities.BaseEntity;
import com.trendistra.trendistashop.entities.category.Category;
import com.trendistra.trendistashop.enums.ProductTagEnum;
import com.trendistra.trendistashop.enums.SizeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "Product")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {
    @Id
    @GeneratedValue
    private UUID id ;
    @Column(nullable = false)
    private String name;
    private String code;
    private String slug;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String summary;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    private Boolean status;
    private BigDecimal originPrice;
    private BigDecimal price;
    private Boolean isFreeShip;
    private Integer views;
    private Integer ratingAverage;
    private Integer ratingTotal;
    private String featuredImage;
    private Integer unitsSold ;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductTagEnum tag;
    @ElementCollection(targetClass = SizeEnum.class)
    @Enumerated(EnumType.STRING) // Lưu enum dưới dạng chuỗi (S, M, L, ...)
    @CollectionTable(name = "product_sizes", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "size")
    private List<SizeEnum> sizes;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany(fetch = FetchType.EAGER , orphanRemoval = true)
    private List<ProductVariant> productVariants;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "product_discount",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "discount_id")
    )
    private List<Discount> discounts = new ArrayList<>();
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();
    // Tăng số lượt xem
    public void incrementView() {
        this.views++;
    }

}
