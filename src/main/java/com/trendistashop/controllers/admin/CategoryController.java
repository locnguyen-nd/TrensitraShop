package com.trendistashop.controllers.admin;

import com.trendistashop.docs.category.*;
import com.trendistashop.services.ICategoryService;
import com.trendistashop.dto.request.CategoryCreUpDTO;
import com.trendistashop.dto.response.CategoryDTO;
import com.trendistashop.dto.response.GenderCategoryGroup;
import com.trendistashop.dto.response.GenderDTO;
import com.trendistashop.dto.response.TypeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/categories")
@CrossOrigin
@Tag(name = "Categories")
public class CategoryController {

    @Autowired
    private ICategoryService iCategoryService;

    @Operation(summary = "Tạo danh mục")
    @CreateCategoryDocs
    @PostMapping("/create")
    public ResponseEntity<TypeResponse<CategoryDTO>> createCategory(
            @RequestBody @Valid CategoryCreUpDTO categoryDTO) {
        TypeResponse<CategoryDTO> response = iCategoryService.createCategory(categoryDTO);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @Operation(summary = "Cập nhật danh mục")
    @UpdateCategoryDocs
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<TypeResponse<CategoryDTO>> updateCategory(
            @PathVariable UUID id,
            @RequestBody @Valid CategoryCreUpDTO category) {
        TypeResponse<CategoryDTO> response = iCategoryService.updateCategory(id, category);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @Operation(summary = "Xóa danh mục")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<TypeResponse<Void>> deleteCategory(@PathVariable UUID id) {
        TypeResponse<Void> response = iCategoryService.deleteCategory(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @Operation(summary = "Lấy danh mục theo id")
    @GetCategoryDocs
    @GetMapping("/{id}")
    public ResponseEntity<TypeResponse<CategoryDTO>> getCategory(@PathVariable UUID id) {
        TypeResponse<CategoryDTO> category = iCategoryService.getCategoryById(id);
        return ResponseEntity.status(category.getStatusCode()).body(category);
    }

    @Operation(summary = "Lấy danh mục theo slug")
    @GetCategoryDocs
    @GetMapping("/slug/{slug}")
    public ResponseEntity<TypeResponse<CategoryDTO>> getCategoryBySlug(@PathVariable String slug) {
        TypeResponse<CategoryDTO> category = iCategoryService.getCategoryBySlug(slug);
        return ResponseEntity.status(category.getStatusCode()).body(category);
    }

    @Operation(summary = "Lấy danh mục theo id parent")
    @GetListCategoryDocs
    @GetMapping("/parent/{id}")
    public ResponseEntity<TypeResponse<List<CategoryDTO>>> getAllByParent(@PathVariable UUID id) {
        TypeResponse<List<CategoryDTO>> categories = iCategoryService.getAllCategoriesByParentId(id);
        return ResponseEntity.status(categories.getStatusCode()).body(categories);
    }

    @Operation(summary = "Lấy danh mục theo id giới tính")
    @GetListCategoryDocs
    @GetMapping("/gender/{id}")
    public ResponseEntity<TypeResponse<List<CategoryDTO>>> getAllByGender(@PathVariable UUID id) {
        TypeResponse<List<CategoryDTO>> categories = iCategoryService.getAllCategoriesByGenderId(id);
        return ResponseEntity.status(categories.getStatusCode()).body(categories);
    }

    @Operation(summary = "Lấy danh sách tất cả danh mục")
    @GetAllCategoryDocs
    @GetMapping
    public ResponseEntity<TypeResponse<List<GenderCategoryGroup>>> getAllCategories() {
        TypeResponse<List<GenderCategoryGroup>> groupedCategories = iCategoryService
                .getAllCategoriesGroupByGender(null);
        return ResponseEntity.status(groupedCategories.getStatusCode()).body(groupedCategories);
    }
    @Operation(summary = "Goi ý tìm kiếm danh mục")
    @GetMapping("/category/search")
    public ResponseEntity<TypeResponse<List<CategoryDTO>>> searchCategoryByName(
            @RequestParam String keyword ) {
        TypeResponse<List<CategoryDTO>> categories = iCategoryService.searchCategoryByName(keyword);
        return ResponseEntity.status(categories.getStatusCode()).body(categories);
    }
    @Operation(summary = "Lấy danh sách giới tính")
    @GetGenderDocs
    @GetMapping("/genders")
    public ResponseEntity<TypeResponse<List<GenderDTO>>> getAllGender() {
        TypeResponse<List<GenderDTO>> genders = iCategoryService.getAllGender();
        return ResponseEntity.status(genders.getStatusCode()).body(genders);
    }
    @Operation(summary = "Tạo giới tính")
    @PostMapping(value = "/create-gender")
    public ResponseEntity<TypeResponse<GenderDTO>> createGender(
            @RequestBody @Valid GenderDTO genderDTO) {
        TypeResponse<GenderDTO> reponse = iCategoryService.createGender(genderDTO);
        return ResponseEntity.status(reponse.getStatusCode()).body(reponse);
    }
    @Operation(summary = "Xóa danh mục")
    @DeleteMapping("/gender/delete/{id}")
    public ResponseEntity<TypeResponse<Void>> deleteGender(@PathVariable UUID id) {
        TypeResponse<Void> response = iCategoryService.deleteGender(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @Operation(summary = "Lấy danh mục theo slug giới tính")
    @GetAllCategoryDocs
    @GetMapping("/gender/slug/{slug}")
    public ResponseEntity<TypeResponse<List<GenderCategoryGroup>>> getAllByGenderBySlug(@PathVariable String slug) {
        TypeResponse<List<GenderCategoryGroup>> groups = iCategoryService.getAllCategoriesGroupByGender(slug);
        return ResponseEntity.status(groups.getStatusCode()).body(groups);
    }
    @Operation(summary = "Goi ý tìm kiếm giới tính")
    @GetMapping("/gender/search")
    public ResponseEntity<TypeResponse<List<GenderDTO>>> searchGenderByName(
            @RequestParam String keyword ) {
        TypeResponse<List<GenderDTO>> genders = iCategoryService.searchGenderByName(keyword);
        return ResponseEntity.status(genders.getStatusCode()).body(genders);
    }
}
