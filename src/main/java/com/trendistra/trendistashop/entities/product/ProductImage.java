package com.trendistra.trendistashop.entities.product;

import com.trendistra.trendistashop.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table(name = "ProductImage")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImage extends BaseEntity {
    @Id
    @GeneratedValue
    private UUID id ;
    private String url;
    private Boolean isThumbnail;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;
}
