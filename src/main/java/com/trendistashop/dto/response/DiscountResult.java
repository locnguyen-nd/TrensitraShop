package com.trendistashop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Locnd
 */
@Data
@AllArgsConstructor
public class DiscountResult {
    private BigDecimal discountAmount;
    private BigDecimal shippingFee;
    private List<DiscountApply> appliedDiscounts;
}
