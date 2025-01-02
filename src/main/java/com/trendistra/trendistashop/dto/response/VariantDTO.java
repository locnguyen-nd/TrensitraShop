package com.trendistra.trendistashop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariantDTO {
    private UUID id;
    private String colorCode;
    private UUID colorId;
    private UUID sizeId;
    private  String sizeName;
    private  Integer stockQuantity;
    private String codeVariant;
}
