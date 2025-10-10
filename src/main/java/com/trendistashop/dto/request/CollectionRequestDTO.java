package com.trendistashop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionRequestDTO {
    private String name;
    private String description;
    private String thumbnail;
    private String bannerUrl;
    private Boolean status;
    private Integer orderIndex;
    private List<UUID> productIds;
    private List<SubThemeRequest> subThemes;
}
