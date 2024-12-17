package com.trendistra.trendistashop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageDTO {
    private UUID id;
    private String url;
    private Boolean isThumbnail;
    private UUID productId;
    private UUID colorId;
}
