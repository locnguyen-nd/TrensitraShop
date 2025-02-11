package com.trendistra.trendistashop.services.impl.product;

import com.trendistra.trendistashop.dto.response.DiscountDTO;
import com.trendistra.trendistashop.entities.category.Category;
import com.trendistra.trendistashop.entities.product.Discount;
import com.trendistra.trendistashop.entities.product.Product;
import com.trendistra.trendistashop.enums.DiscountType;
import com.trendistra.trendistashop.exceptions.ResourceNotFoundEx;
import com.trendistra.trendistashop.repositories.category.CategoryRepository;
import com.trendistra.trendistashop.repositories.product.DiscountRepository;
import com.trendistra.trendistashop.repositories.product.ProductRepository;
import com.trendistra.trendistashop.services.CloudinaryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InvalidClassException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DiscountService {
    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    // Create
    @Transactional
    public DiscountDTO createDiscount(DiscountDTO discountDto,
                                      List<UUID> categoryTds,
                                      List<UUID> productIds,
                                      MultipartFile imageFile) throws IOException {

        // Check if discount with same name already exists
        if (discountRepository.existsByName(discountDto.getName())) {
            throw new RuntimeException("Discount with this name already exists");
        }

        // Validate dates
        discountDto = validateDiscount(discountDto);

        Discount discount = Discount.builder()
                .name(discountDto.getName())
                .description(discountDto.getDescription())
                .discountType(discountDto.getDiscountType())
                .discountValue(discountDto.getDiscountValue())
                .maxDiscountValue(discountDto.getMaxDiscountValue())
                .minOrderValue(discountDto.getMinOrderValue())
                .startDate(discountDto.getStartDate())
                .endDate(discountDto.getEndDate())
                .isActive(discountDto.getIsActive() != null ? discountDto.getIsActive() : true)
                .build();
        // Upload ảnh
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(imageFile, null, "Discount Frame");
            discount.setFrame(imageUrl);
        }
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
        return mapToDiscountDto(createDiscount);
    }

    private DiscountDTO validateDiscount(DiscountDTO discountDTO) {
        if (discountDTO.getStartDate() == null) {
            discountDTO.setStartDate(LocalDateTime.now());
        }
        if (discountDTO.getStartDate().isAfter(discountDTO.getEndDate())) {
            throw new RuntimeException("Start date must be before end date");
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
    public DiscountDTO getDiscountById(UUID id) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discount not found"));

        return mapToDiscountDto(discount);
    }

    public List<DiscountDTO> getAllDiscount() {
        List<Discount> discounts = discountRepository.findAll();
        return discounts.stream().map(this::mapToDiscountDto).collect(Collectors.toList());
    }

    // Update
    @Transactional
    public DiscountDTO updateDiscount(UUID id,
                                      DiscountDTO discountDto,
                                      List<UUID> categoryTds,
                                      List<UUID> productIds,
                                      MultipartFile imageFile) throws IOException {

        // Validate dates
        discountDto = validateDiscount(discountDto);
        // Find existing discount
        Discount existingDiscount = discountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discount not found with id: " + id));
        // Update discount
        existingDiscount.setName(discountDto.getName());
        existingDiscount.setDescription(discountDto.getDescription());
        existingDiscount.setDiscountType(discountDto.getDiscountType());
        existingDiscount.setDiscountValue(discountDto.getDiscountValue());
        existingDiscount.setMaxDiscountValue(discountDto.getMaxDiscountValue());
        existingDiscount.setMinOrderValue(discountDto.getMinOrderValue());
        existingDiscount.setStartDate(discountDto.getStartDate());
        existingDiscount.setEndDate(discountDto.getEndDate());
        existingDiscount.setIsActive(discountDto.getIsActive() != null ? discountDto.getIsActive() : true);
        // Upload ảnh
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(imageFile, null, "Discount Frame");
            existingDiscount.setFrame(imageUrl);
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
        return mapToDiscountDto(updatedDiscount);

    }

    // Delete
    public void deleteDiscount(UUID id) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discount not found"));

        discountRepository.delete(discount);
    }


    // Additional method to manage discount activation
    public void setDiscountStatus(UUID id, boolean status) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discount not found"));
        discount.setIsActive(status);
        discountRepository.save(discount);
    }

    public DiscountDTO applyDiscountToOrder(UUID discountId, BigDecimal orderTotal) {
        Discount discountOptional = discountRepository.findById(discountId).orElseThrow(
                () -> new RuntimeException("Discount not found"));
        if (!discountOptional.getIsActive()
                || LocalDateTime.now().isBefore(discountOptional.getStartDate())
                || LocalDateTime.now().isAfter(discountOptional.getEndDate())) {
            return null;
        }
        // check minimum order value
        if (orderTotal.compareTo(discountOptional.getMinOrderValue()) < 0) {
            return null;
        }
        // Calculate discount amount
        BigDecimal discountAmount = calculateDiscountAmount(discountOptional, orderTotal);
        DiscountDTO discountDTO = mapToDiscountDto(discountOptional);
        discountDTO.setValueApply(discountAmount);
        return discountDTO;
    }

    private BigDecimal calculateDiscountAmount(Discount discount, BigDecimal orderTotal) {
        BigDecimal discountAmount;
        switch (discount.getDiscountType()) {
            case AMOUNT:
                discountAmount = discount.getDiscountValue();
                break;
            case PERCENT:
                discountAmount = orderTotal.multiply(discount.getDiscountValue().divide(BigDecimal.valueOf(100)));
                // Apply max discount if applicable
                if (discount.getMaxDiscountValue() != null) {
                    discountAmount = discountAmount.min(discount.getMaxDiscountValue());
                }
                break;
            default:
                discountAmount = BigDecimal.ZERO;
        }
        return discountAmount;
    }

    // Helper method to convert Discount to DiscountDto
    public DiscountDTO mapToDiscountDto(Discount discount) {
        return DiscountDTO.builder()
                .id(discount.getId())
                .name(discount.getName())
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

        finalPrice = applyMaxDiscountLimit(basePrice, finalPrice, discount);
        product.setPrice(finalPrice.max(BigDecimal.ZERO));

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

