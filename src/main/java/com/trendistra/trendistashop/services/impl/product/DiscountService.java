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
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
        if(discountDTO.getStartDate() == null) {
            discountDTO.setStartDate(LocalDateTime.now());
        }
        if (discountDTO.getStartDate().isAfter(discountDTO.getEndDate())) {
            throw new RuntimeException("Start date must be before end date");
        }
        if(discountDTO.getEndDate() == null || discountDTO.getEndDate().isBefore(discountDTO.getStartDate())) {
            throw  new IllegalArgumentException("Invalid discount end date");
        }
        //  validate discount value on type
        if(discountDTO.getDiscountType() == DiscountType.PERCENT) {
            if (discountDTO.getDiscountValue().compareTo(BigDecimal.valueOf(100)) > 0) {
                throw  new IllegalArgumentException("Percentage discount cannot exceed 100%");
            }
        }
        // Max discount is set for percentage discount
        if(discountDTO.getMaxDiscountValue() == null) {
            discountDTO.setMaxDiscountValue(BigDecimal.valueOf(1000));
        }
        // Default to active if not specified
        if(discountDTO.getIsActive() == null) {
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
    public DiscountDTO updateDiscount(UUID id, DiscountDTO discountDto) {
        Discount existingDiscount = discountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discount not found"));

        // Validate dates
        if (discountDto.getStartDate().isAfter(discountDto.getEndDate())) {
            throw new RuntimeException("Start date must be before end date");
        }

        // Update fields
        existingDiscount.setName(discountDto.getName());
        existingDiscount.setDescription(discountDto.getDescription());
        existingDiscount.setDiscountType(discountDto.getDiscountType());
        existingDiscount.setDiscountValue(discountDto.getDiscountValue());
        existingDiscount.setMaxDiscountValue(discountDto.getMaxDiscountValue());
        existingDiscount.setMinOrderValue(discountDto.getMinOrderValue());
        existingDiscount.setStartDate(discountDto.getStartDate());
        existingDiscount.setEndDate(discountDto.getEndDate());
        existingDiscount.setIsActive(discountDto.getIsActive());

        Discount updatedDiscount = discountRepository.save(existingDiscount);

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
        if(!discountOptional.getIsActive()
                || LocalDateTime.now().isBefore(discountOptional.getStartDate())
                || LocalDateTime.now().isAfter(discountOptional.getEndDate())) {
            return null;
        }
        // check minimum order value
        if(orderTotal.compareTo(discountOptional.getMinOrderValue()) < 0){
            return null;
        }
        // Calculate discount amount
        BigDecimal discountAmount = calculateDiscountAmount(discountOptional, orderTotal);
        DiscountDTO discountDTO = mapToDiscountDto(discountOptional);
        discountDTO.setValueApply(discountAmount);
        return  discountDTO;
    }

    private  BigDecimal calculateDiscountAmount (Discount discount, BigDecimal orderTotal) {
        BigDecimal discountAmount;
        switch (discount.getDiscountType()){
            case AMOUNT:
                discountAmount = discount.getDiscountValue();
                break;
            case PERCENT:
                discountAmount = orderTotal.multiply(discount.getDiscountValue().divide(BigDecimal.valueOf(100)));
                // Apply max discount if applicable
                if(discount.getMaxDiscountValue() != null) {
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

}

