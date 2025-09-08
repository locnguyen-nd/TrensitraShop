package com.trendistashop.dto.request;

import com.trendistashop.enums.DiscountApply;
import com.trendistashop.enums.DiscountType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class DiscountRequest {
        private String code;
        private String description;
        private DiscountType discountType;
        private DiscountApply discountApply;
        @Min(value = 0)
        private BigDecimal discountValue;
        private BigDecimal maxDiscountValue;
        private BigDecimal minOrderValue;
        @URL
        private String frame;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private Boolean isActive;
}
