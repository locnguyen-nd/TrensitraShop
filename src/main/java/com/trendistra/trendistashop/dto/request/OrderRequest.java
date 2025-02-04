package com.trendistra.trendistashop.dto.request;

import com.trendistra.trendistashop.dto.response.CartItemDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    private UUID userId;
    private Date orderDate;
    @NotBlank(message = "Address not empty")
    private UUID addressId;
    @NotBlank(message = "Item not empty")
    private List<CartItemDTO> orderItems;
    private BigDecimal totalAmount;
    private Double discount;
    private String paymentMethod;
    private Date expectedDeliverDate;
    private String bankApp;
    private String note;
}
