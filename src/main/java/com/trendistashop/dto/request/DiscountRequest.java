package com.trendistashop.dto.request;

import com.trendistashop.enums.DiscountApply;
import com.trendistashop.enums.DiscountType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class DiscountRequest {
        private String code;
        private String description;
        @NotBlank(message = "discount type not blank")
        private DiscountType discountType;
        @NotBlank(message = "discount apply not blank")
        private DiscountApply discountApply;
        @NotBlank(message = "discount value not blank")
        @Min(value = 0)
        private BigDecimal discountValue;
        private BigDecimal maxDiscountValue;
        private BigDecimal minOrderValue;
        private String frame;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private Boolean isActive;
}
