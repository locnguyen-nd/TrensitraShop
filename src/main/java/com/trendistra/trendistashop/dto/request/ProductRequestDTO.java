package com.trendistra.trendistashop.dto.request;

import com.trendistra.trendistashop.dto.response.*;
import com.trendistra.trendistashop.enums.ProductTagEnum;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDTO {
    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 255, message = "Product name must be between 2 and 255 characters")
    private String name;
    @NotNull(message = "Summary ID is required")
    private String summary;
    @NotNull(message = "Description ID is required")
    private String description;
    private Boolean status;
    @NotNull(message = "Origin price is required")
    @DecimalMin(value = "0.0", message = "Origin price must be non-negative")
    private BigDecimal originPrice;
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price must be non-negative")
    private BigDecimal price;
    private Boolean isFreeShip;
    private ProductTagEnum tag;
    @NotNull(message = "Category ID is required")
    private UUID categoryId;
    private List<UUID> discountIds;
    private List<VariantRequestDTO> variants;
    private Map<UUID, List<String>> colorImageMapping;  // Mỗi màu đi kèm danh sách tên file

}
