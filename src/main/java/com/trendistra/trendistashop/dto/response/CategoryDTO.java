package com.trendistra.trendistashop.dto.response;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CategoryDTO {
    private UUID id;
    private String name;
    private String slug;
    private String description;
    private String imageUrl;
    private UUID parentId;
    private GenderDTO gender;
    private List<CategoryDTO> items;
}
