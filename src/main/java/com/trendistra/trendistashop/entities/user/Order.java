package com.trendistra.trendistashop.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.trendistra.trendistashop.entities.BaseEntity;
import com.trendistra.trendistashop.entities.product.Discount;
import com.trendistra.trendistashop.enums.OrderStatus;
import com.trendistra.trendistashop.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Table(name = "orders")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseEntity {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
    @Column(nullable = false)
    private BigDecimal totalAmount;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    private String shipmentTrackingNumber; // Số theo dõi lô hàng
    @Temporal(TemporalType.TIMESTAMP)
    private Date expectedDeliveryDate; // ngày giao dự kiến
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonManagedReference("order-items") // Parent side of order-items relationship
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "discount_id") // Tạo khóa ngoại discount_id
    private Discount discount; // Mã giảm giá được áp dụng
    private Long orderCoder;
    private String note;
    private LocalDateTime expiredAt;
    private LocalDateTime orderDate;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonManagedReference("order-payment")
    private Payment payment;
}
