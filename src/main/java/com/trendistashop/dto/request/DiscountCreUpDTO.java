package com.trendistashop.dto.request;

import com.trendistashop.enums.DiscountType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@ApiResponse(description = "DTO cho khuyến mãi hàng hóa")
@Data
public class DiscountCreUpDTO {
    private String description;
    @NotNull(message = "discount type must not be null")
    private DiscountType discountType;
    @NotNull(message = "discount value must not be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "discount value must be >= 0")
    private BigDecimal discountValue;
    @NotNull(message = "discount value must not be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "discount value must be >= 0")
    private BigDecimal maxDiscountValue;
    private String frameUrl;
    @DecimalMin(value = "0.0", inclusive = true, message = "min order value must be >= 0")
    private BigDecimal minOrderValue;
    @NotNull(message = "start date must not be null")
    private LocalDateTime startDate;
    @NotNull(message = "end date must not be null")
    private LocalDateTime endDate;
    private Boolean isActive;
    @AssertTrue(message = "Ngày kết thúc phải sau ngày bắt đầu")
    public boolean isValidDateRange() {
        return startDate != null && endDate != null && endDate.isAfter(startDate);
    }
    @AssertTrue(message = "Giá trị giảm tối đa phải lớn hơn hoặc bằng giá trị giảm")
    public boolean isValidDiscountValue() {
        return maxDiscountValue != null && discountValue != null
                && maxDiscountValue.compareTo(discountValue) >= 0;
    }
    @AssertTrue(message = "Discount phần trăm không được vượt quá 100%")
    public boolean isValidPercentValue() {
        return discountType != DiscountType.PERCENT
                || (discountValue != null && discountValue.compareTo(BigDecimal.valueOf(100)) <= 0);
    }

}
