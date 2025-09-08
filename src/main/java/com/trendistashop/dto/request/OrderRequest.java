package com.trendistashop.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    @NotNull(message = "Order ID cannot be null")
    private UUID orderId;
    @NotNull(message = "Address ID cannot be null")
    private UUID addressId;
    @Min(value = 0)
    private BigDecimal discountValue;
    @NotBlank(message = "Payment method not empty (QR/COD)")
    private String paymentMethod;
    private String note;
}
