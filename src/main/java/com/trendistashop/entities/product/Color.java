package com.trendistashop.entities.product;

import com.trendistashop.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Table(name = "Color")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Color extends BaseEntity {
    @Id
    @GeneratedValue
    private UUID id ;
    private String name ;
    private String code;
    private String value;
    @OneToMany(mappedBy = "color", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> productImages;
}
