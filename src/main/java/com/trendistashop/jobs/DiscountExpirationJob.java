package com.trendistashop.jobs;

import com.trendistashop.entities.product.Discount;
import com.trendistashop.entities.product.Product;
import com.trendistashop.entities.product.ProductVariant;
import com.trendistashop.repositories.product.DiscountRepository;
import com.trendistashop.repositories.product.ProductRepository;
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
    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    @Scheduled(cron = "0 0 0 * * *") // Mỗi 0h sáng
    @Async // Chạy bất đồng bộ
    public void checkExpiredDiscounts() {
        log.info("Bắt đầu kiểm tra discount hết hạn lúc {}", LocalDateTime.now());

        // Tìm các discount còn active nhưng đã hết hạn
//        List<Discount> expiredDiscounts = discountRepository.findAllByIsActiveTrueAndEndDateBeforeNow(new Date());
        List<Discount> expiredDiscounts = new ArrayList<>();
        if (expiredDiscounts.isEmpty()) {
            log.info("Không có discount nào hết hạn.");
            return;
        }

        // Vô hiệu hóa các discount hết hạn
        expiredDiscounts.forEach(discount -> {
            discount.setIsActive(false);
            log.debug("Vô hiệu hóa discount ID: {}, Code: {}", discount.getId(), discount.getCode());
        });
        discountRepository.saveAll(expiredDiscounts);

        // Lấy danh sách sản phẩm bị ảnh hưởng
        Set<Product> affectedProducts = expiredDiscounts.stream()
                .flatMap(discount -> Stream.concat(
                        discount.getProducts().stream(),
                        discount.getCategories().stream()
                                .flatMap(category -> category.getProducts().stream())
                ))
                .collect(Collectors.toSet());

        // Cập nhật giá và discount cho các sản phẩm bị ảnh hưởng
        int updatedProducts = 0;
        for (Product product : affectedProducts) {
            try {
                updateProductAfterDiscountExpiration(product, expiredDiscounts);
                updatedProducts++;
            } catch (Exception e) {
                log.error("Lỗi khi cập nhật sản phẩm ID: {} - {}", product.getId(), e.getMessage());
            }
        }

        // Lưu tất cả sản phẩm đã cập nhật
        if (!affectedProducts.isEmpty()) {
            productRepository.saveAll(affectedProducts);
        }

        // Ghi log chi tiết
        log.info("Đã vô hiệu hóa {} discount hết hạn và cập nhật {} sản phẩm.",
                expiredDiscounts.size(), updatedProducts);
    }

    private void updateProductAfterDiscountExpiration(Product product, List<Discount> expiredDiscounts) {
        // Loại bỏ các discount hết hạn khỏi sản phẩm
        List<Discount> activeDiscounts = product.getDiscounts().stream()
                .filter(discount -> !expiredDiscounts.contains(discount) && discount.getIsActive())
                .collect(Collectors.toList());
        product.setDiscounts(activeDiscounts);

        // Tính lại giá dựa trên ProductVariant và discount còn active
        if (product.getProductVariants() != null && !product.getProductVariants().isEmpty()) {
            // Lấy giá thấp nhất từ ProductVariant làm originPrice
            BigDecimal minPrice = product.getProductVariants().stream()
                    .map(ProductVariant::getPrice)
                    .filter(Objects::nonNull)
                    .min(BigDecimal::compareTo)
                    .orElse(BigDecimal.ZERO);
            product.setOriginPrice(minPrice);

            // Tính giá cuối cùng sau khi áp dụng các discount còn active
            BigDecimal finalPrice = calculateFinalPrice(product, activeDiscounts);
            product.setPrice(finalPrice);
        } else {
            // Nếu không có biến thể, đặt giá về 0
            product.setOriginPrice(BigDecimal.ZERO);
            product.setPrice(BigDecimal.ZERO);
        }
    }

    private BigDecimal calculateFinalPrice(Product product, List<Discount> activeDiscounts) {
        BigDecimal basePrice = product.getOriginPrice() != null ? product.getOriginPrice() : BigDecimal.ZERO;
        if (activeDiscounts.isEmpty()) {
            return basePrice;
        }

        // Tìm discount có giá trị lớn nhất (giả sử discountValue là số tiền giảm)
        BigDecimal maxDiscount = activeDiscounts.stream()
                .map(Discount::getDiscountValue) // Giả sử Discount có getDiscountValue trả về số tiền giảm
                .filter(Objects::nonNull)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        return basePrice.subtract(maxDiscount).max(BigDecimal.ZERO);
    }

}