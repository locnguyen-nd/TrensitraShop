package com.trendistashop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionResponseDTO {
    private UUID id;
    private String name;
    private String slug;
    private String description;
    private String thumbnail;
    private String bannerUrl;
    private Boolean status;
    private Integer orderIndex;
    private List<SubThemeResponse> subThemes = new ArrayList<>();
    private List<ProductDTO> products = new ArrayList<>();
}
