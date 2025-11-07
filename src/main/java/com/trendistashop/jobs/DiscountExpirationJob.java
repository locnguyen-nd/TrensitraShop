package com.trendistashop.jobs;

import com.trendistashop.entities.product.Discount;
import com.trendistashop.entities.product.Product;
import com.trendistashop.entities.product.ProductVariant;
import com.trendistashop.repositories.product.DiscountRepository;
import com.trendistashop.repositories.product.ProductRepository;
import com.trendistashop.services.impl.product.DiscountService;
import com.trendistashop.services.impl.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Slf4j
public class DiscountExpirationJob {
    private final DiscountService discountService;
    private final ProductService productService;

    @Scheduled(cron = "0 0 * * * *") // Mỗi giờ
    public void resetExpiredDiscounts() {
        LocalDateTime now = LocalDateTime.now();
        discountService.getExpiredDiscounts().forEach(discount -> {
            log.info("Discount {} expired. Resetting prices...", discount.getCode());
            discount.setIsActive(false);
            discountService.updatePricesForAffectedProducts(discount);
        });
    }
}