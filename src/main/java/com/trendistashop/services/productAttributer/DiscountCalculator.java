package com.trendistashop.services.productAttributer;

import com.trendistashop.entities.product.Discount;

import java.math.BigDecimal;

public interface DiscountCalculator {
    BigDecimal calculateDiscountAmount(Discount discount, BigDecimal total);
    BigDecimal applyMaxDiscount(BigDecimal discountAmount, Discount discount);

}

