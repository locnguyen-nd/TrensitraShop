package com.trendistra.trendistashop.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class OrderItemDTO {
    private UUID id ;
    private UUID productId;
    private  String productName;
    private String productSlug;
    private VariantDTO variantDTO;
    private Integer quantity;
    private BigDecimal itemPrice;


}
