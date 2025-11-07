package com.trendistashop.entities.product;

import com.trendistashop.entities.category.Category;
import com.trendistashop.entities.BaseEntity;
import com.trendistashop.entities.collection.Collection;
import com.trendistashop.entities.collection.SubTheme;
import com.trendistashop.enums.ProductTagEnum;
import com.trendistashop.enums.SizeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
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
    @OneToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    private List<ProductVariant> productVariants;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "product_discount",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "discount_id")
    )
    private List<Discount> discounts = new ArrayList<>();
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> images = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_id")
    private Collection collection;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_sub_themes",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_theme_id")
    )
    private List<SubTheme> subThemes = new ArrayList<>();
    public void incrementView() {
        this.views++;
    }
    public void addSubTheme(SubTheme subTheme) {
        if (!subThemes.contains(subTheme)) {
            subThemes.add(subTheme);
            if (subTheme.getProducts() == null) {
                subTheme.setProducts(new ArrayList<>());
            }
            if (!subTheme.getProducts().contains(this)) {
                subTheme.getProducts().add(this);
            }
        }
    }

    public void removeSubTheme(SubTheme subTheme) {
        if (subThemes.contains(subTheme)) {
            subThemes.remove(subTheme);
            if (subTheme.getProducts() != null && subTheme.getProducts().contains(this)) {
                subTheme.getProducts().remove(this);
            }
        }
    }

}
