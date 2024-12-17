package com.trendistra.trendistashop.entities.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;
@Entity
@Table(name = "ProductVariant")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariant {
    @Id
    @GeneratedValue
    private UUID id ;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id", nullable = false)
    private Color color ;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id", nullable = false)
    private Size size;
    @Column (nullable = false)
    private Integer stockQuantity;
    @Column(nullable = false)
    private String codeVariant; // PVN6014-NSU-XL : Name + colorName  + Size
    @ManyToOne
    @JoinColumn(name = "product_id" , nullable = false)
    @JsonIgnore
    private Product product;

}
