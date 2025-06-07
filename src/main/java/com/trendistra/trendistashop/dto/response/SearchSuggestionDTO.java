package com.trendistra.trendistashop.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class SearchSuggestionDTO {
    private List<ProductDTO> products;
    private List<NameSlugDTO> categories;

    @Data
    public static class NameSlugDTO {
        private String name;
        private String slug;
    }
}
