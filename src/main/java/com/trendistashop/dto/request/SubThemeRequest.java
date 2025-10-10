package com.trendistashop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubThemeRequest {
    private String name;
    private String description;
    private String imageUrl;
    private Integer priority;
    private List<UUID> productIds;
}