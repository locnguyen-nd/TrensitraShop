package com.trendistra.trendistashop.entities.product;

import com.trendistra.trendistashop.enums.DiscountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "Discount")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Discount {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType; // % or VNĐ

    @Column(name = "discount_value", precision = 10, scale = 2, nullable = false)
    private BigDecimal discountValue; // 30% or VNĐ

    @Column(name = "max_discount_value", precision = 10, scale = 2)
    private BigDecimal maxDiscountValue; // 50k tối đa bao nhiêu nếu discountValue > maxDiscountValue thì lấy maxDiscountValue

    @Column(name = "min_order_value", precision = 10, scale = 2)
    private BigDecimal minOrderValue; // 500k đơn hàng tối thiểu để áp dụng khuyến mãi
    private String frame; // khung chương trình discount (url ảnh )

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate; // ngày bắt đầu

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate; // ngày  kết thuc
    @Column(name = "usage_limit")
    private Integer usageLimit; // Số lượng voucher có thể sử dụng

    @Column(name = "max_usage_per_customer")
    private Integer maxUsagePerCustomer; // Số lần sử dụng voucher cho mỗi khách hàng
    @Column(name = "is_active")
    private Boolean isActive = true; // trạng thái
}
