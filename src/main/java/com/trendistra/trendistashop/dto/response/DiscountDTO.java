package com.trendistra.trendistashop.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trendistra.trendistashop.enums.DiscountType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountDTO {
    private UUID id;
    @NotBlank(message = "name not blank")
    private String name;
    @NotBlank(message = "description not blank")
    private String description; // điều kiện áp dụng
    @NotBlank(message = "discount type not blank")
    private DiscountType discountType;
    @NotBlank(message = "discount value not blank")
    @Min(value = 0)
    private BigDecimal discountValue;
    @NotBlank(message = "max discount value not blank")
    @Min(value = 0)
    private BigDecimal maxDiscountValue;
    private String frame; // khung chương trình discount (url ảnh )
    private BigDecimal minOrderValue;
    @NotBlank(message = "start date value not blank")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime startDate;
    @NotBlank(message = "end date value not blank")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime endDate;
    private BigDecimal valueApply;
    private Boolean isActive;
}
