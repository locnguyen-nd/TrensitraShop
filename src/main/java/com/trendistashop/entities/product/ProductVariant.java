package com.trendistashop.entities.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    @Column(name = "origin_price", precision = 19, scale = 2)
    private BigDecimal originPrice; // Giá gốc
    @Column(name = "price", precision = 19, scale = 2)
    private BigDecimal price; // Giá sau discount
    @Column(name = "variant_order" ,nullable = false)
    private Integer order;
    @Column(nullable = false)
    private String codeVariant;
    @ManyToOne
    @JoinColumn(name = "product_id" , nullable = false)
    private Product product;

}
