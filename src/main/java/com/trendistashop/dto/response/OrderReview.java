package com.trendistashop.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Locnd
 */
@Data
@Builder
public class OrderReview {
    private List<VariantDTO> variantList;
    private Integer quantity;
    private BigDecimal shippingFee;
    private BigDecimal total;
    private BigDecimal discount;
    private List<DiscountApply> appliedDiscounts;
}
