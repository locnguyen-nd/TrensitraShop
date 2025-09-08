package com.trendistashop.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@Data
public class VariantRequestDTO {
    @NotNull(message = "Color ID không được để trống")
    private UUID colorId;
    @NotNull(message = "Size ID không được để trống")
    private UUID sizeId;
    @PositiveOrZero(message = "Số lượng tồn kho phải >= 0")
    private Integer stockQuantity;
    private BigDecimal price;
    private List<ImageRequestDTO> images;
}
