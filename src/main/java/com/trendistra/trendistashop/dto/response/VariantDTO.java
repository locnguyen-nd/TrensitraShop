package com.trendistra.trendistashop.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class VariantDTO {
    private UUID id;
    private String colorCode;
    private UUID colorId;
    private  String sizeName;
    private  Integer stockQuantity;
    private String codeVariant;
}
