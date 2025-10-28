package com.trendistashop.services;

import java.util.List;
import java.util.UUID;

import com.trendistashop.dto.request.CategoryCreUpDTO;
import com.trendistashop.dto.response.CategoryDTO;
import com.trendistashop.dto.response.GenderCategoryGroup;
import com.trendistashop.dto.response.GenderDTO;
import com.trendistashop.dto.response.TypeResponse;

public interface ICategoryService {
    TypeResponse<CategoryDTO> createCategory(CategoryCreUpDTO categoryDTO);
    TypeResponse<CategoryDTO> updateCategory(UUID id, CategoryCreUpDTO categoryDTO);
    TypeResponse<Void> deleteCategory(UUID id);
    TypeResponse<CategoryDTO> getCategoryById(UUID id);
    TypeResponse<CategoryDTO> getCategoryBySlug(String slug);
    TypeResponse<List<CategoryDTO>> getAllCategoriesByParentId(UUID parentId);
    TypeResponse<List<CategoryDTO>> searchCategoryByName(String name);
    TypeResponse<List<CategoryDTO>> getAllCategoriesByGenderId(UUID genderId);
    TypeResponse<List<GenderCategoryGroup>> getAllCategoriesGroupByGender(String genderSlug, Boolean isActive);
    TypeResponse<GenderDTO> createGender(GenderDTO genderDTO);
    TypeResponse<Void> deleteGender(UUID id);
    TypeResponse<List<GenderDTO>> getAllGender();
    TypeResponse<List<GenderDTO>> searchGenderByName(String name);
    TypeResponse<CategoryDTO> restoreCategory (UUID id);
}
