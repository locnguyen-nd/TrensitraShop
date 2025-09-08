package com.trendistashop.entities.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.trendistashop.entities.product.Product;
import com.trendistashop.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "order_item")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    private UUID productVariantId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonBackReference("order-items")
    private Order order;
    @Column(nullable = false)
    private Integer quantity;
    private BigDecimal itemPrice;
}
