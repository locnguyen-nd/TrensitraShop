package com.trendistra.trendistashop.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemRequest {
    @NotNull(message = "Product ID is required")
    private UUID productId;
    private UUID productVariantId;
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}
