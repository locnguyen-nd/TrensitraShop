package com.trendistra.trendistashop.entities.category;

import com.trendistra.trendistashop.entities.BaseEntity;
import com.trendistra.trendistashop.entities.product.Discount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "Category")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category extends BaseEntity {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "slug", nullable = false)
    private String slug;
    private String description;
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Category parent; // Danh mục cha
    @ManyToOne(optional = false)
    @JoinColumn(name = "gender_id", referencedColumnName = "id", nullable = false)
    private Gender gender; // Giới tính (liên kết với Gender)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "category_discount",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "discount_id")
    )
    private List<Discount> discounts = new ArrayList<>();
}
