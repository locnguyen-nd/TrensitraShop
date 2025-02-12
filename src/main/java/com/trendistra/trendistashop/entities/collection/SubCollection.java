package com.trendistra.trendistashop.entities.collection;

import com.trendistra.trendistashop.entities.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "sub_collection")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubCollection {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private String name;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    @ManyToOne
    @JoinColumn(name = "collection_id", nullable = false)
    private Collection collection;

    @OneToMany(mappedBy = "subCollection", cascade = CascadeType.ALL)
    @Where(clause = "owner_type = 'SUB_COLLECTION'")
    private List<Media> media = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "sub_collection_products",
            joinColumns = @JoinColumn(name = "sub_collection_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();
}
