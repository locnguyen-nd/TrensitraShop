package com.trendistashop.services.productAttributer;

import com.trendistashop.entities.product.Discount;
import com.trendistashop.entities.product.Product;
import com.trendistashop.enums.DiscountType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DiscountCalculatorImpl implements DiscountCalculator{
    @Override
    public BigDecimal calculateDiscountAmount(Discount discount, BigDecimal total) {
        if (discount == null || total == null) return BigDecimal.ZERO;

        BigDecimal amount;
        switch (discount.getDiscountType()) {
            case AMOUNT:
                amount = discount.getDiscountValue();
                break;
            case PERCENT:
                amount = total.multiply(discount.getDiscountValue().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
                break;
            default:
                amount = BigDecimal.ZERO;
        }
        return applyMaxDiscount(amount, discount);
    }

    @Override
    public BigDecimal applyMaxDiscount(BigDecimal discountAmount, Discount discount) {
        if (discount.getMaxDiscountValue() != null) {
            return discountAmount.min(discount.getMaxDiscountValue());
        }
        return discountAmount;
    }
    public List<Discount> getActiveDiscounts(Product product) {
        Set<Discount> all = new HashSet<>();
        if (product.getDiscounts() != null) all.addAll(product.getDiscounts());
        if (product.getCategory() != null && product.getCategory().getDiscounts() != null) {
            all.addAll(product.getCategory().getDiscounts());
        }
        return all.stream().filter(this::isValidDiscount).collect(Collectors.toList());
    }

    public boolean isValidDiscount(Discount d) {
        return d.getIsActive()
                && (d.getStartDate() == null || LocalDateTime.now().isAfter(d.getStartDate()))
                && (d.getEndDate() == null || LocalDateTime.now().isBefore(d.getEndDate()));
    }
}
