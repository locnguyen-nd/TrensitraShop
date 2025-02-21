package com.trendistra.trendistashop.dto.request;

import com.trendistra.trendistashop.dto.response.CartItemDTO;
import com.trendistra.trendistashop.dto.response.DiscountDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
