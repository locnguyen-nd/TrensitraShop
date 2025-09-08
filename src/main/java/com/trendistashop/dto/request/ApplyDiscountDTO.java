package com.trendistashop.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;
@Data
public class ApplyDiscountDTO {
    @NotNull
    private String action; // "apply" or "remove"
    @NotNull
    private UUID discountId;
    private List<UUID> categoryIds;
    private List<UUID> productIds;
}
