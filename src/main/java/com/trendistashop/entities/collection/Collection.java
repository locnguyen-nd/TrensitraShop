package com.trendistashop.entities.collection;

import com.trendistashop.entities.BaseEntity;
import com.trendistashop.entities.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.*;

@Entity(name = "collection")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Collection extends BaseEntity {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, unique = true)
    private String slug;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    private String thumbnail;
    private String bannerUrl;
    private Boolean status;
    private Integer orderIndex;
    @OneToMany(mappedBy = "collection", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SubTheme> subThemes = new ArrayList<>();
    @OneToMany(mappedBy = "collection", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Product> products  = new ArrayList<>();
}
