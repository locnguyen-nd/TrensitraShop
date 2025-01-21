package com.trendistra.trendistashop.dto.response;

import lombok.Data;

import java.util.List;
@Data
public class GenderCategoryGroup {
    private GenderDTO gender;
    private List<CategoryDTO> categories;
    public GenderCategoryGroup(GenderDTO gender, List<CategoryDTO> categories) {
        this.gender = gender;
        this.categories = categories;
    }
}
