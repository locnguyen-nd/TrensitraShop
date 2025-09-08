package com.trendistashop.services.impl.product;

import com.trendistashop.constants.ResponseMessage;
import com.trendistashop.dto.request.DiscountRequest;
import com.trendistashop.dto.response.DiscountApply;
import com.trendistashop.dto.response.DiscountDTO;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.entities.category.Category;
import com.trendistashop.entities.product.Discount;
import com.trendistashop.entities.product.Product;
import com.trendistashop.entities.user.Order;
import com.trendistashop.enums.DiscountType;
import com.trendistashop.exceptions.ResourceNotFoundEx;
import com.trendistashop.helper.GenerateCodeDiscount;
import com.trendistashop.repositories.category.CategoryRepository;
import com.trendistashop.repositories.order.OrderRepository;
import com.trendistashop.repositories.product.DiscountRepository;
import com.trendistashop.repositories.product.ProductRepository;
import com.trendistashop.services.CloudinaryService;
import com.trendistashop.utils.ResponseHelper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DiscountService {
    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    private GenerateCodeDiscount generateCodeDiscount;

    // Create
    @Transactional
    public TypeResponse<DiscountDTO> createDiscount(DiscountRequest discountDto,
                                       List<UUID> categoryTds,
                                       List<UUID> productIds)  {
        // Check if discount with same name already exists

        if (discountDto.getCode() == null || discountDto.getCode().isBlank()) {
            discountDto.setCode(generateCodeDiscount.generateUniqueDiscountCode());
        } else if (discountRepository.existsByCode(discountDto.getCode())) {
            throw new ResourceNotFoundEx("Discount with this code already exists, you can blank code to system generate code");
        }

        // Validate dates
        discountDto = validateDiscount(discountDto);

        Discount discount = Discount.builder()
                .code(discountDto.getCode())
                .description(discountDto.getDescription())
                .discountType(discountDto.getDiscountType())
                .discountApply(discountDto.getDiscountApply())
                .discountValue(discountDto.getDiscountValue())
                .frame(discountDto.getFrame())
                .maxDiscountValue(discountDto.getMaxDiscountValue())
                .minOrderValue(discountDto.getMinOrderValue())
                .startDate(discountDto.getStartDate())
                .endDate(discountDto.getEndDate())
                .isActive(discountDto.getIsActive() != null ? discountDto.getIsActive() : true)
                .build();
        Discount createDiscount = discountRepository.save(discount);
//        System.out.println(categoryTds.size());
        // For Categories
        if (categoryTds != null && !categoryTds.isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(categoryTds);
            categories.forEach(category -> category.getDiscounts().add(createDiscount));
            categoryRepository.saveAll(categories);
        }
        // For Products
        if (productIds != null && !productIds.isEmpty()) {
            List<Product> products = productRepository.findAllById(productIds);
            products.forEach(product -> product.getDiscounts().add(createDiscount));
            productRepository.saveAll(products);
        }
        return ResponseHelper.ok(mapToDiscountDto(createDiscount), ResponseMessage.FETCH_SUCCESS);
    }

    private DiscountRequest validateDiscount(DiscountRequest discountDTO) {
        if (discountDTO.getStartDate() == null) {
            discountDTO.setStartDate(LocalDateTime.now());
        }
        if (discountDTO.getStartDate().isAfter(discountDTO.getEndDate())) {
            throw new ResourceNotFoundEx("Start date must be before end date");
        }
        if (discountDTO.getEndDate() == null || discountDTO.getEndDate().isBefore(discountDTO.getStartDate())) {
            throw new IllegalArgumentException("Invalid discount end date");
        }
        //  validate discount value on type
        if (discountDTO.getDiscountType() == DiscountType.PERCENT) {
            if (discountDTO.getDiscountValue().compareTo(BigDecimal.valueOf(100)) > 0) {
                throw new IllegalArgumentException("Percentage discount cannot exceed 100%");
            }
        }
        // Max discount is set for percentage discount
        if (discountDTO.getMaxDiscountValue() == null) {
            discountDTO.setMaxDiscountValue(BigDecimal.valueOf(1000));
        }
        // Default to active if not specified
        if (discountDTO.getIsActive() == null) {
            discountDTO.setIsActive(true);
        }
        return discountDTO;
    }

    // Read One
    public TypeResponse<DiscountDTO> getDiscountById(UUID id) {
        try {
            Discount discount = discountRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundEx("Discount not found"));
            DiscountDTO discountDTO = mapToDiscountDto(discount);
            return ResponseHelper.ok(discountDTO, ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }

    public TypeResponse<List<DiscountDTO>> getAllDiscount() {
        try {
            List<Discount> discounts = discountRepository.findAll();
            List result =  discounts.stream().map(this::mapToDiscountDto).collect(Collectors.toList());
            return ResponseHelper.ok(result, ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }

    }

    // Update
    @Transactional
    public TypeResponse<DiscountDTO> updateDiscount(UUID id,
                                      DiscountRequest discountDto,
                                      List<UUID> categoryTds,
                                      List<UUID> productIds)  {
        try {
            // Validate dates
            discountDto = validateDiscount(discountDto);
            // Find existing discount
            Discount existingDiscount = discountRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundEx("Discount not found with id: " + id));
            // Update discount
            existingDiscount.setCode(discountDto.getCode());
            existingDiscount.setDescription(discountDto.getDescription());
            existingDiscount.setDiscountType(discountDto.getDiscountType());
            existingDiscount.setDiscountApply(discountDto.getDiscountApply());
            existingDiscount.setDiscountValue(discountDto.getDiscountValue());
            existingDiscount.setMaxDiscountValue(discountDto.getMaxDiscountValue());
            existingDiscount.setMinOrderValue(discountDto.getMinOrderValue());
            existingDiscount.setStartDate(discountDto.getStartDate());
            existingDiscount.setEndDate(discountDto.getEndDate());
            existingDiscount.setIsActive(discountDto.getIsActive() != null ? discountDto.getIsActive() : true);
            // Upload ảnh
            if (existingDiscount.getFrame() != null && !existingDiscount.getFrame().equals(discountDto.getFrame())) {
                cloudinaryService.deleteFile(existingDiscount.getFrame());
            } else {
                existingDiscount.setFrame(discountDto.getFrame());
            }
            Discount updatedDiscount = discountRepository.save(existingDiscount);
            // For Categories
            if (categoryTds != null && !categoryTds.isEmpty()) {
                List<Category> categories = categoryRepository.findAllById(categoryTds);
                // Remove old relationships
                List<Category> oldCategories = categoryRepository.findAll().stream()
                        .filter(category -> category.getDiscounts().contains(existingDiscount))
                        .collect(Collectors.toList());
                oldCategories.forEach(category -> category.getDiscounts().remove(existingDiscount));
                categoryRepository.saveAll(oldCategories);

                categories.forEach(category -> category.getDiscounts().add(updatedDiscount));
                categoryRepository.saveAll(categories);
            } else {
                List<Category> oldCategories = categoryRepository.findAll().stream()
                        .filter(category -> category.getDiscounts().contains(existingDiscount))
                        .collect(Collectors.toList());
                oldCategories.forEach(category -> category.getDiscounts().remove(existingDiscount));
                categoryRepository.saveAll(oldCategories);
            }

            // For Products
            if (productIds != null && !productIds.isEmpty()) {
                List<Product> products = productRepository.findAllById(productIds);
                List<Product> oldProducts = productRepository.findAll().stream()
                        .filter(product -> product.getDiscounts().contains(existingDiscount))
                        .collect(Collectors.toList());
                oldProducts.forEach(product -> product.getDiscounts().remove(existingDiscount));
                productRepository.saveAll(oldProducts);

                products.forEach(product -> product.getDiscounts().add(updatedDiscount));
                productRepository.saveAll(products);
            } else {
                List<Product> oldProducts = productRepository.findAll().stream()
                        .filter(product -> product.getDiscounts().contains(existingDiscount))
                        .collect(Collectors.toList());
                oldProducts.forEach(product -> product.getDiscounts().remove(existingDiscount));
                productRepository.saveAll(oldProducts);
            }
            return ResponseHelper.created(mapToDiscountDto(updatedDiscount), ResponseMessage.UPDATE_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.badRequest(ResponseMessage.UPDATE_FAILED);
        }

    }

    // Delete
    public TypeResponse<Void> deleteDiscount(UUID id) {
        try {
            Discount discount = discountRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundEx("Discount not found"));
            for (Product product : discount.getProducts()) {
                product.getDiscounts().remove(discount);
            }
            for (Category category : discount.getCategories()) {
                category.getDiscounts().remove(discount);
            }
            discountRepository.save(discount);
            cloudinaryService.deleteFile(discount.getFrame());
            discountRepository.delete(discount);
            return ResponseHelper.ok(null, ResponseMessage.DELETE_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.DELETE_FAILED);
        }
    }


    // Additional method to manage discount activation
    public TypeResponse<Void> setDiscountStatus(UUID id, boolean status) {
        try{
            Discount discount = discountRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundEx("Discount not found"));
            discount.setIsActive(status);
            discountRepository.save(discount);
            return ResponseHelper.ok(null, ResponseMessage.UPDATE_SUCCESS);
        } catch (Exception e){
            return ResponseHelper.serverError(ResponseMessage.UPDATE_FAILED);
        }
    }

    public TypeResponse<DiscountApply> applyDiscountToOrder(String discountCode, UUID orderId) {
        try {
            Discount discountOptional = discountRepository.findDiscountByCode(discountCode);
            Order order = orderRepository.findById(orderId).get();
            if (discountOptional == null || order == null) {
                throw new ResourceNotFoundEx("Discount or Order not found with code and id: " + discountCode + orderId);
            }
            if (order.getDiscount() != null && order.getDiscount().getCode().equals(discountCode)) {
                return null;
            }
            if (!discountOptional.getIsActive()
                    || LocalDateTime.now().isBefore(discountOptional.getStartDate())
                    || LocalDateTime.now().isAfter(discountOptional.getEndDate())) {
                return null;
            }
            if (discountOptional.getDiscountApply() == com.trendistashop.enums.DiscountApply.ORDER
                    && order.getTotalAmount().compareTo(discountOptional.getMinOrderValue()) < 0) {
                return null;
            }
            // Calculate discount amount
            BigDecimal discountAmount = calculateDiscountAmount(discountOptional, order.getTotalAmount());
            BigDecimal originPrice = order.getOrderItems().stream()
                    .findFirst()
                    .map(orderItem -> orderItem.getProduct().getOriginPrice())
                    .orElse(BigDecimal.ZERO);

            BigDecimal saved = originPrice.subtract(discountAmount);
            DiscountApply discountApply = DiscountApply.builder()
                    .code(discountOptional.getCode())
                    .valueApply(discountAmount)
                    .saved(saved)
                    .build();
            order.setDiscount(discountOptional);
            orderRepository.save(order);
            return ResponseHelper.ok(discountApply, ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }

    public TypeResponse<BigDecimal> applyDiscountToNewProduct(String discountCode, BigDecimal price) {
        try {
            Discount discountOptional = discountRepository.findDiscountByCode(discountCode);
            if (discountOptional == null) {
                throw new ResourceNotFoundEx("Discount not found with code: " + discountCode);
            }
            if (!discountOptional.getIsActive()
                    || LocalDateTime.now().isBefore(discountOptional.getStartDate())
                    || LocalDateTime.now().isAfter(discountOptional.getEndDate())) {
                return ResponseHelper.ok(price, ResponseMessage.FETCH_SUCCESS);
            }
            BigDecimal discountAmount = calculateDiscountAmount(discountOptional, price);
            BigDecimal priceAfterDiscount = price.subtract(discountAmount);
            return ResponseHelper.ok(priceAfterDiscount.max(BigDecimal.ZERO), ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }


    private BigDecimal calculateDiscountAmount(Discount discount, BigDecimal price) {
        BigDecimal discountAmount;
        if (discount.getDiscountApply() == com.trendistashop.enums.DiscountApply.PRODUCT) {
            if (discount.getDiscountType() == DiscountType.PERCENT) {
                discountAmount = price.multiply(discount.getDiscountValue().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP));
            } else {
                discountAmount = discount.getDiscountValue();
            }
        } else {
            if (price.compareTo(discount.getMinOrderValue()) < 0) {
                return BigDecimal.ZERO;
            }
            if (discount.getDiscountType() == DiscountType.PERCENT) {
                discountAmount = price.multiply(discount.getDiscountValue().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP));
                if (discount.getMaxDiscountValue() != null) {
                    discountAmount = discountAmount.min(discount.getMaxDiscountValue());
                }
            } else {
                discountAmount = discount.getDiscountValue();
            }
        }
        return discountAmount;
    }

    // Helper method to convert Discount to DiscountDto
    public DiscountDTO mapToDiscountDto(Discount discount) {
        return DiscountDTO.builder()
                .id(discount.getId())
                .code(discount.getCode())
                .frame(discount.getFrame())
                .description(discount.getDescription())
                .discountType(discount.getDiscountType())
                .discountValue(discount.getDiscountValue())
                .maxDiscountValue(discount.getMaxDiscountValue())
                .minOrderValue(discount.getMinOrderValue())
                .startDate(discount.getStartDate())
                .endDate(discount.getEndDate())
                .isActive(discount.getIsActive())
                .build();
    }

    public BigDecimal calculateFinalPriceAndUpdateProduct(UUID productId) {
        Product product = findProductById(productId);
        BigDecimal discountValue = calculateDiscount(product);
        updateProductPrice(product);
        return discountValue;
    }
    private Product findProductById(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundEx("Product not found"));
    }

    private BigDecimal calculateDiscount(Product product) {
        if (!isValidProduct(product)) {
            setOriginalPrice(product);
            return BigDecimal.ZERO;
        }

        List<Discount> applicableDiscounts = findApplicableDiscounts(product);
        if (applicableDiscounts.isEmpty()) {
            setOriginalPrice(product);
            return BigDecimal.ZERO;
        }

        Discount highestDiscount = findHighestDiscount(applicableDiscounts);
        return calculateAndApplyDiscount(product, highestDiscount);
    }

    private boolean isValidProduct(Product product) {
        return product != null && product.getCategory() != null;
    }
    private void setOriginalPrice(Product product) {
        product.setPrice(product.getOriginPrice());
    }

    private List<Discount> findApplicableDiscounts(Product product) {
        List<UUID> discountIds = new ArrayList<>();
        discountIds.addAll(getProductDiscountIds(product));
        discountIds.addAll(getCategoryDiscountIds(product.getCategory()));

        return discountRepository.findAllById(discountIds);
    }

    private List<UUID> getProductDiscountIds(Product product) {
        return product.getDiscounts().stream()
                .filter(discount -> discount.getIsActive() == true)
                .map(Discount::getId)
                .collect(Collectors.toList());
    }

    private List<UUID> getCategoryDiscountIds(Category category) {
        return category.getDiscounts().stream()
                .filter(discount -> discount.getIsActive() == true)
                .map(Discount::getId)
                .collect(Collectors.toList());
    }

    private Discount findHighestDiscount(List<Discount> discounts) {
        return discounts.stream()
                .max(Comparator.comparing(Discount::getDiscountValue))
                .orElseThrow(() -> new IllegalStateException("No discount found in non-empty discount list"));
    }

    private BigDecimal calculateAndApplyDiscount(Product product, Discount discount) {
        BigDecimal basePrice = product.getOriginPrice();
        BigDecimal discountValue;
        BigDecimal finalPrice;

        if (discount.getDiscountType() == DiscountType.PERCENT) {
            discountValue = discount.getDiscountValue();
            finalPrice = calculatePercentageDiscount(basePrice, discountValue);
        } else {
            finalPrice = calculateFixedDiscount(basePrice, discount.getDiscountValue());
            discountValue = calculateEffectiveDiscountPercentage(basePrice, discount.getDiscountValue());
        }
        if(com.trendistashop.enums.DiscountApply.PRODUCT.equals(discount.getDiscountApply()))
        {
            product.setPrice(finalPrice.max(BigDecimal.ZERO));
        }
         discountValue = applyMaxDiscountLimit(basePrice, finalPrice, discount);

        return discountValue;
    }

    private BigDecimal calculatePercentageDiscount(BigDecimal basePrice, BigDecimal discountPercentage) {
        return basePrice.multiply(
                BigDecimal.valueOf(100).subtract(discountPercentage)
                        .divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP)
        );
    }

    private BigDecimal calculateFixedDiscount(BigDecimal basePrice, BigDecimal discountAmount) {
        return basePrice.subtract(discountAmount);
    }

    private BigDecimal calculateEffectiveDiscountPercentage(BigDecimal basePrice, BigDecimal discountAmount) {
        return discountAmount.divide(basePrice, 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    private BigDecimal applyMaxDiscountLimit(BigDecimal basePrice, BigDecimal finalPrice, Discount discount) {
        if (discount.getMaxDiscountValue() != null) {
            BigDecimal actualDiscount = basePrice.subtract(finalPrice);
            if (actualDiscount.compareTo(discount.getMaxDiscountValue()) > 0) {
                return basePrice.subtract(discount.getMaxDiscountValue());
            }
        }
        return finalPrice;
    }

    private void updateProductPrice(Product product) {
        productRepository.save(product);
    }
}

