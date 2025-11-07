package com.trendistashop.dto.request;

import com.trendistashop.enums.DiscountApplyFor;
import com.trendistashop.enums.DiscountType;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class DiscountRequest {
        private String code;
        private String description;
        private DiscountType discountType;
        private DiscountApplyFor discountApplyFor;
        @Min(value = 0)
        private BigDecimal discountValue;
        private BigDecimal maxDiscountValue;
        private BigDecimal minOrderValue;
        @URL
        private String frame;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private Boolean isActive;
        
        // Fields for range filtering
        private BigDecimal maxDiscountValueFrom;
        private BigDecimal maxDiscountValueTo;
        private BigDecimal minOrderValueFrom;
        private BigDecimal minOrderValueTo;
        private Integer usageLimit;
        private Integer maxUsagePerCustomer;
}
