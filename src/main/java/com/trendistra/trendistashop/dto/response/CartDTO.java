package com.trendistra.trendistashop.dto.response;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartDTO {
    @NotNull
    private UUID productId;

    private VariantDTO variantDTO;

    private String productName;

    private BigDecimal price;

    @Min(1)
    private Integer quantity;
}
