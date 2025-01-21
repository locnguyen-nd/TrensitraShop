package com.trendistra.trendistashop.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class SearchSuggestionDTO {
    private List<String> productNames;
    private List<String> categoryNames;
}
