package com.trendistra.trendistashop.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trendistra.trendistashop.entities.product.Product;
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
public class OrderItemDetail {
    private UUID id;
    @JsonProperty("product")
    private Product product;
    private UUID productVariantId;
    private Integer quantity;
    private BigDecimal itemPrice;
}
