package com.trendistra.trendistashop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubCollectionResponseDTO {
    private UUID id;
    private String name;
    private String description;
    private List<MediaDTO> media;
    private List<ProductDTO> products;
}
