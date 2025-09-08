package com.trendistashop.entities.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.trendistashop.entities.BaseEntity;
import com.trendistashop.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "payment")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue
    private UUID id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id",nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference("order-payment")
    private Order order;
    @Column(nullable = false)
    private int amount;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
//    @Column(nullable = false)

    private String paymentStatus;
    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String qrCode;
    private Long transactionId;
    private LocalDateTime paidAt;
    private String deepLink;
}
