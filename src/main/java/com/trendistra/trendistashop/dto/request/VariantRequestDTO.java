package com.trendistra.trendistashop.dto.request;

import lombok.Data;

import java.util.UUID;
@Data
public class VariantRequestDTO {
    private UUID colorId ;
    private UUID sizeId;
    private Integer stockQuantity;
}
