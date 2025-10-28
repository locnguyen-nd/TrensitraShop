package com.trendistashop.entities.collection;

import com.trendistashop.entities.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "sub_themes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubTheme {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String imageUrl;
    private Integer priority = 0;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_id", nullable = false)
    private Collection collection;
    @ManyToMany(mappedBy = "subThemes")
    private List<Product> products  = new ArrayList<>();;
}
