package com.trendistra.trendistashop.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trendistra.trendistashop.entities.BaseEntity;
import com.trendistra.trendistashop.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Table(name = "orders")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseEntity {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private UserEntity user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id", nullable = false)
    @JsonIgnore
    private Address address;
    @Column(nullable = false)
    private BigDecimal totalAmount;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;
    @Column(nullable = false)
    private String paymentMethod;
    @Column(nullable = true)
    private String shipmentTrackingNumber; // Số theo dõi lô hàng
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expectedDeliveryDate; // ngày giao dự kiến
    @OneToMany(fetch = FetchType.LAZY , mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
    private Double discount;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Payment payment;

}
