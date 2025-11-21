package com.trendistashop.dto.response;

import com.trendistashop.enums.ProductTagEnum;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private UUID id;
    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 255, message = "Product name must be between 2 and 255 characters")
    private String name;
    @NotBlank(message = "Product code is required")
    @Size(min = 3, max = 50, message = "Product code must be between 3 and 50 characters")
    private String code;
    private String slug;
    private String thumbnail;
    private Boolean status;
    @NotNull(message = "Origin price is required")
    @DecimalMin(value = "0.0", message = "Origin price must be non-negative")
    private BigDecimal originPrice;
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price must be non-negative")
    private BigDecimal price;
    private ProductTagEnum tag;
    private Integer availableQuantities;
    private Integer views;
    private Double ratingAverage;
    private Integer ratingTotal;
    private Integer unitsSold;
    @NotNull(message = "Category ID is required")
    private UUID categoryId;
    private String categoryName;
    private String categorySlug;
    private UUID genderId;
    private String genderName;
    private String genderSlug;
    private Boolean isFreeShip;
    @NotNull(message = "Summary ID is required")
    private String summary;
    @NotNull(message = "Description ID is required")
    private String description;
    private BigDecimal discountValue;
    private List<VariantDTO> productVariants;
    private List<DiscountDTO> discounts;
}
