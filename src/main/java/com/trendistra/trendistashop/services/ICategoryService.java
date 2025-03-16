package com.trendistra.trendistashop.services;

import java.util.List;
import java.util.UUID;

import com.trendistra.trendistashop.dto.response.CategoryDTO;
import com.trendistra.trendistashop.dto.response.GenderCategoryGroup;
import com.trendistra.trendistashop.dto.response.GenderDTO;
import com.trendistra.trendistashop.dto.response.TypeResponse;

public interface ICategoryService {
    TypeResponse<List<GenderDTO>> getAllGender();
    TypeResponse<CategoryDTO> getCategoryById(UUID id);
    TypeResponse<CategoryDTO> getCategoryBySlug(String slug);
    TypeResponse<List<CategoryDTO>> getAllCategoriesByParentId(UUID parentId);
    TypeResponse<List<CategoryDTO>> getAllCategoriesByGenderId(UUID genderId);
    TypeResponse<List<GenderCategoryGroup>> getAllCategoriesGroupByGender(String genderSlug);
}
