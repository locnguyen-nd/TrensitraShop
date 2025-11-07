package com.trendistashop.services.impl.product;

import com.trendistashop.constants.ResponseMessage;
import com.trendistashop.dto.request.DiscountRequest;
import com.trendistashop.dto.response.DiscountApply;
import com.trendistashop.dto.response.DiscountDTO;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.entities.category.Category;
import com.trendistashop.entities.product.Discount;
import com.trendistashop.entities.product.Product;
import com.trendistashop.entities.product.ProductVariant;
import com.trendistashop.entities.user.CartItem;
import com.trendistashop.enums.DiscountApplyFor;
import com.trendistashop.enums.DiscountType;
import com.trendistashop.exceptions.ResourceNotFoundEx;
import com.trendistashop.helper.GenerateCodeDiscount;
import com.trendistashop.repositories.category.CategoryRepository;
import com.trendistashop.repositories.product.DiscountRepository;
import com.trendistashop.repositories.product.ProductRepository;
import com.trendistashop.services.CloudinaryService;
import com.trendistashop.specifications.DiscountSpec;
import com.trendistashop.utils.ResponseHelper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CloudinaryService cloudinaryService;
    private final GenerateCodeDiscount generateCodeDiscount;
    @Transactional
    public TypeResponse<DiscountDTO> createDiscount(DiscountRequest dto,
                                                    List<UUID> categoryIds,
                                                    List<UUID> productIds) {
        try {
            dto = validateAndPrepare(dto);

            if (discountRepository.existsByCode(dto.getCode())) {
                return ResponseHelper.validationError("code", ResponseMessage.DISCOUNT_EXIST);
            }

            Discount discount = Discount.builder()
                    .code(dto.getCode())
                    .description(dto.getDescription())
                    .discountType(dto.getDiscountType())
                    .discountApplyFor(dto.getDiscountApplyFor())
                    .discountValue(dto.getDiscountValue())
                    .frame(dto.getFrame())
                    .maxDiscountValue(dto.getMaxDiscountValue())
                    .minOrderValue(dto.getMinOrderValue())
                    .startDate(dto.getStartDate())
                    .endDate(dto.getEndDate())
                    .isActive(dto.getIsActive())
                    .usageLimit(dto.getUsageLimit())
                    .maxUsagePerCustomer(dto.getMaxUsagePerCustomer())
                    .build();

            discount = discountRepository.save(discount);

            attachToCategories(discount, categoryIds);
            attachToProductsAndUpdatePrice(discount, productIds);

            return ResponseHelper.ok(mapToDiscountDto(discount), ResponseMessage.CREATE_SUCCESS);
        } catch (Exception e) {
            log.error("Error creating discount", e);
            return ResponseHelper.badRequest(ResponseMessage.BAD_REQUEST);
        }
    }

    @Transactional
    public TypeResponse<DiscountDTO> updateDiscount(UUID id,
                                                    DiscountRequest dto,
                                                    List<UUID> categoryIds,
                                                    List<UUID> productIds) {
        try {
            Discount discount = discountRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundEx(ResponseMessage.NOT_FOUND));

            dto = validateAndPrepare(dto);

            discount.setCode(dto.getCode());
            discount.setDescription(dto.getDescription());
            discount.setDiscountType(dto.getDiscountType());
            discount.setDiscountApplyFor(dto.getDiscountApplyFor());
            discount.setDiscountValue(dto.getDiscountValue());
            discount.setMaxDiscountValue(dto.getMaxDiscountValue());
            discount.setMinOrderValue(dto.getMinOrderValue());
            discount.setStartDate(dto.getStartDate());
            discount.setEndDate(dto.getEndDate());
            discount.setIsActive(dto.getIsActive());
            discount.setUsageLimit(dto.getUsageLimit());
            discount.setMaxUsagePerCustomer(dto.getMaxUsagePerCustomer());

            if (dto.getFrame() != null && !dto.getFrame().equals(discount.getFrame())) {
                if (discount.getFrame() != null) {
                    cloudinaryService.deleteFile(discount.getFrame());
                }
                discount.setFrame(dto.getFrame());
            }

            discount = discountRepository.save(discount);

            // Cập nhật quan hệ
            detachFromAllCategories(discount);
            detachFromAllProducts(discount);

            attachToCategories(discount, categoryIds);
            attachToProductsAndUpdatePrice(discount, productIds);

            return ResponseHelper.ok(mapToDiscountDto(discount), ResponseMessage.UPDATE_SUCCESS);
        } catch (ResourceNotFoundEx e) {
            return ResponseHelper.notFound(e.getMessage());
        } catch (Exception e) {
            log.error("Error updating discount", e);
            return ResponseHelper.badRequest(ResponseMessage.UPDATE_FAILED);
        }
    }

    @Transactional
    public TypeResponse<Void> deleteDiscount(UUID id) {
        try {
            Discount discount = discountRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundEx(ResponseMessage.NOT_FOUND));

            detachFromAllCategories(discount);
            detachFromAllProducts(discount);

            if (discount.getFrame() != null) {
                cloudinaryService.deleteFile(discount.getFrame());
            }

            discountRepository.delete(discount);
            return ResponseHelper.ok(null, ResponseMessage.DELETE_SUCCESS);
        } catch (ResourceNotFoundEx e) {
            return ResponseHelper.notFound(e.getMessage());
        } catch (Exception e) {
            log.error("Error deleting discount", e);
            return ResponseHelper.serverError(ResponseMessage.DELETE_FAILED);
        }
    }

    public TypeResponse<DiscountDTO> getDiscountById(UUID id) {
        return discountRepository.findById(id)
                .map(d -> ResponseHelper.ok(mapToDiscountDto(d), ResponseMessage.FETCH_SUCCESS))
                .orElseGet(() -> ResponseHelper.notFound(ResponseMessage.NOT_FOUND));
    }

    public TypeResponse<Page<DiscountDTO>> getAllDiscount(DiscountRequest filter, Pageable pageable) {
        try {
            Page<Discount> page = discountRepository.findAll(DiscountSpec.filter(filter), pageable);
            return ResponseHelper.ok(page.map(this::mapToDiscountDto), ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            log.error("Error fetching discounts", e);
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }
    public TypeResponse<Void> setDiscountStatus(UUID id, boolean status) {
        try {
            Discount discount = discountRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundEx(ResponseMessage.NOT_FOUND));
            discount.setIsActive(status);
            discountRepository.save(discount);

            updatePricesForAffectedProducts(discount);
            return ResponseHelper.ok(null, ResponseMessage.UPDATE_SUCCESS);
        } catch (ResourceNotFoundEx e) {
            return ResponseHelper.notFound(e.getMessage());
        } catch (Exception e) {
            log.error("Error updating discount status", e);
            return ResponseHelper.serverError(ResponseMessage.UPDATE_FAILED);
        }
    }
    @Transactional
    public BigDecimal calculateFinalPriceAndUpdateProduct(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundEx("Product not found"));

        BigDecimal finalPrice = calculateFinalPriceForProduct(product);
        product.setPrice(finalPrice.max(BigDecimal.ZERO));
        if(product.getProductVariants() != null) {
            for (ProductVariant variant : product.getProductVariants()) {
                BigDecimal variantFinalPrice = calculateFinalPriceForVariant(variant, product);
                variant.setPrice(variantFinalPrice.max(BigDecimal.ZERO));
            }
        }
        productRepository.save(product);
        return finalPrice;
    }

    private BigDecimal calculateFinalPriceForProduct(Product product) {
        List<Discount> applicable = getApplicableProductDiscounts(product);
        return applicable.isEmpty()
                ? product.getOriginPrice()
                : applyDiscount(product.getOriginPrice(), getBestDiscount(applicable));
    }
    private BigDecimal calculateFinalPriceForVariant(ProductVariant variant, Product product) {
        BigDecimal basePrice = (variant.getPrice() != null && variant.getPrice().compareTo(BigDecimal.ZERO) > 0)
                ? variant.getPrice()
                : product.getPrice();

        List<Discount> discounts = getApplicableProductDiscounts(product);
        return discounts.isEmpty() ? basePrice : applyDiscount(basePrice, getBestDiscount(discounts));
    }
    private List<Discount> getApplicableProductDiscounts(Product product) {
        LocalDateTime now = LocalDateTime.now();

        return Stream.concat(
                        product.getDiscounts().stream(),
                        getAllCategoryDiscounts(product.getCategory())
                )
                .filter(d -> Boolean.TRUE.equals(d.getIsActive()))
                .filter(d -> d.getDiscountApplyFor() == DiscountApplyFor.PRODUCT)
                .filter(d -> !now.isBefore(d.getStartDate()) && !now.isAfter(d.getEndDate()))
                .distinct()
                .collect(Collectors.toList());
    }

    private Stream<Discount> getAllCategoryDiscounts(Category category) {
        if (category == null) return Stream.empty();
        Stream<Discount> current = category.getDiscounts().stream();
        Stream<Discount> parentDiscounts = getAllCategoryDiscounts(category.getParent());
        return Stream.concat(current, parentDiscounts);
    }

    private Discount getBestDiscount(List<Discount> discounts) {
        return discounts.stream()
                .max(Comparator.comparing(this::getEffectiveDiscountValue))
                .orElseThrow();
    }

    private BigDecimal getEffectiveDiscountValue(Discount d) {
        return d.getDiscountType() == DiscountType.PERCENT
                ? d.getDiscountValue()
                : d.getDiscountValue().divide(BigDecimal.valueOf(1000), 4, RoundingMode.HALF_UP);
    }

    private BigDecimal applyDiscount(BigDecimal basePrice, Discount discount) {
        BigDecimal priceAfter = switch (discount.getDiscountType()) {
            case PERCENT -> {
                BigDecimal rate = BigDecimal.ONE.subtract(
                        discount.getDiscountValue().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)
                );
                yield basePrice.multiply(rate);
            }
            case AMOUNT -> basePrice.subtract(discount.getDiscountValue());
            default -> basePrice;
        };

        if (discount.getMaxDiscountValue() != null) {
            BigDecimal actualDiscount = basePrice.subtract(priceAfter);
            if (actualDiscount.compareTo(discount.getMaxDiscountValue()) > 0) {
                priceAfter = basePrice.subtract(discount.getMaxDiscountValue());
            }
        }

        return priceAfter.max(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);
    }

    private DiscountRequest validateAndPrepare(DiscountRequest dto) {
        if (dto.getStartDate() == null) dto.setStartDate(LocalDateTime.now());
        if (dto.getEndDate() == null || dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new IllegalArgumentException("End date must be after start date");
        }
        if (dto.getDiscountType() == DiscountType.PERCENT
                && dto.getDiscountValue().compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("Percentage cannot exceed 100%");
        }
        if (dto.getIsActive() == null) dto.setIsActive(true);
        if (dto.getMaxDiscountValue() == null) dto.setMaxDiscountValue(BigDecimal.ZERO);
        if (dto.getCode() == null || dto.getCode().isBlank()) {
            dto.setCode(generateCodeDiscount.generateUniqueDiscountCode());
        }
        return dto;
    }

    private void attachToCategories(Discount discount, List<UUID> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) return;
        List<Category> categories = categoryRepository.findAllById(categoryIds);
        categories.forEach(c -> c.getDiscounts().add(discount));
        categoryRepository.saveAll(categories);
    }

    private void attachToProductsAndUpdatePrice(Discount discount, List<UUID> productIds) {
        if (productIds == null || productIds.isEmpty()) return;
        List<Product> products = productRepository.findAllById(productIds);
        products.forEach(p -> p.getDiscounts().add(discount));
        productRepository.saveAll(products);
        products.forEach(p -> calculateFinalPriceAndUpdateProduct(p.getId()));
    }

    private void detachFromAllCategories(Discount discount) {
        categoryRepository.findAll().stream()
                .filter(c -> c.getDiscounts().contains(discount))
                .forEach(c -> c.getDiscounts().remove(discount));
    }

    private void detachFromAllProducts(Discount discount) {
        productRepository.findAll().stream()
                .filter(p -> p.getDiscounts().contains(discount))
                .forEach(p -> {
                    p.getDiscounts().remove(discount);
                    calculateFinalPriceAndUpdateProduct(p.getId());
                });
    }

    public void updatePricesForAffectedProducts(Discount discount) {
        Set<UUID> productIds = new HashSet<>();
        productIds.addAll(discount.getProducts().stream().map(Product::getId).toList());
        discount.getCategories().forEach(cat -> collectProductIdsFromCategory(cat, productIds));
        productIds.forEach(this::calculateFinalPriceAndUpdateProduct);
    }

    private void collectProductIdsFromCategory(Category cat, Set<UUID> ids) {
        if (cat == null) return;
        cat.getProducts().forEach(p -> ids.add(p.getId()));
    }
    public DiscountDTO mapToDiscountDto(Discount discount) {
        return DiscountDTO.builder()
                .id(discount.getId())
                .code(discount.getCode())
                .frame(discount.getFrame())
                .description(discount.getDescription())
                .discountType(discount.getDiscountType())
                .discountApplyFor(discount.getDiscountApplyFor())
                .discountValue(discount.getDiscountValue())
                .maxDiscountValue(discount.getMaxDiscountValue())
                .minOrderValue(discount.getMinOrderValue())
                .startDate(discount.getStartDate())
                .endDate(discount.getEndDate())
                .isActive(discount.getIsActive())
                .usageLimit(discount.getUsageLimit())
                .maxUsagePerCustomer(discount.getMaxUsagePerCustomer())
                .categoryApplies(
                        Optional.ofNullable(discount.getCategories()).orElse(List.of())
                                .stream().map(Category::getId).toList()
                )
                .productApplies(
                        Optional.ofNullable(discount.getProducts()).orElse(List.of())
                                .stream()
                                .map(p -> new DiscountDTO.ProductApplyDTO(p.getId(), p.getName()))
                                .toList()
                )
                .build();
    }

    public DiscountApply previewDiscountForOrder(UUID id, BigDecimal subtotal, List<CartItem> items) {
        Discount discount = discountRepository.findById(id).orElse(null);
        if (discount == null || !Boolean.TRUE.equals(discount.getIsActive())) return null;

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(discount.getStartDate()) || now.isAfter(discount.getEndDate())) return null;
        if (discount.getMinOrderValue() != null && subtotal.compareTo(discount.getMinOrderValue()) < 0) return null;

        BigDecimal saved = BigDecimal.ZERO;
        if (discount.getDiscountApplyFor() == DiscountApplyFor.ORDER) {
            saved = calculateOrderDiscount(subtotal, discount);
        } else if (discount.getDiscountApplyFor() == DiscountApplyFor.SHIPPING) {
            saved = calculateShippingDiscount(discount);
        } else {
            return null;
        }

        return DiscountApply.builder()
                .id(id)
                .code(discount.getCode())
                .valueApply(saved)
                .saved(saved)
                .applyType(discount.getDiscountApplyFor())
                .build();
    }

    private BigDecimal calculateOrderDiscount(BigDecimal subtotal, Discount d) {
        BigDecimal amount = d.getDiscountType() == DiscountType.PERCENT
                ? subtotal.multiply(d.getDiscountValue()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
                : d.getDiscountValue();
        return d.getMaxDiscountValue() != null ? amount.min(d.getMaxDiscountValue()) : amount;
    }

    private BigDecimal calculateShippingDiscount(Discount d) {
        BigDecimal fee = BigDecimal.valueOf(30000);
        if (d.getDiscountType() == DiscountType.AMOUNT) {
            return d.getDiscountValue().min(fee);
        } else {
            BigDecimal percentOff = d.getDiscountValue().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            return percentOff.compareTo(BigDecimal.ONE) >= 0 ? fee : fee.multiply(percentOff);
        }
    }
    public TypeResponse<BigDecimal> applyDiscountToNewProduct(String code, BigDecimal price) {
        Discount discount = discountRepository.findDiscountByCode(code);
        if (discount == null || !isDiscountActive(discount)) {
            return ResponseHelper.ok(price, ResponseMessage.FETCH_SUCCESS);
        }
        BigDecimal finalPrice = applyDiscount(price, discount);
        return ResponseHelper.ok(finalPrice, ResponseMessage.FETCH_SUCCESS);
    }

    private boolean isDiscountActive(Discount d) {
        LocalDateTime now = LocalDateTime.now();
        return Boolean.TRUE.equals(d.getIsActive())
                && !now.isBefore(d.getStartDate())
                && !now.isAfter(d.getEndDate());
    }
    public List<Discount> getExpiredDiscounts() {
        LocalDateTime now = LocalDateTime.now();
        return discountRepository.findByIsActiveTrueAndEndDateBefore(now);
    }
}