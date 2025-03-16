package com.trendistra.trendistashop.controllers.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendistra.trendistashop.docs.category.GetAllCategoryDocs;
import com.trendistra.trendistashop.docs.category.GetCategoryDocs;
import com.trendistra.trendistashop.docs.category.GetGenderDocs;
import com.trendistra.trendistashop.docs.category.GetListCategoryDocs;
import com.trendistra.trendistashop.dto.response.CategoryDTO;
import com.trendistra.trendistashop.dto.response.GenderCategoryGroup;
import com.trendistra.trendistashop.dto.response.GenderDTO;
import com.trendistra.trendistashop.dto.response.TypeResponse;
import com.trendistra.trendistashop.services.ICategoryService;
import com.trendistra.trendistashop.services.impl.category.CategoryService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/categories")
@CrossOrigin
@Tag(name = "Categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ICategoryService iCategoryService;

    @Operation(summary = "Tạo danh mục")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryDTO> createCategory(
            @RequestPart("category") String categoryDTOJson,
            @RequestParam(required = false) MultipartFile imageFile
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CategoryDTO categoryDTO = objectMapper.readValue(categoryDTOJson, CategoryDTO.class);
        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO, imageFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @Operation(summary = "Tạo giới tính")
    @PostMapping(value = "/gender", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GenderDTO> createGender(
            @RequestPart ("gender") String genderDTO,
            @RequestParam MultipartFile images
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        GenderDTO genderDtoMap = objectMapper.readValue(genderDTO, GenderDTO.class);
        GenderDTO genderDTO1 = categoryService.createGender(genderDtoMap, images);
        return ResponseEntity.status(HttpStatus.CREATED).body(genderDTO1);
    }

    @Operation(summary = "Cập nhật danh mục")
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable UUID id,
            @Parameter(description = "Thông tin danh mục") @RequestPart("categoryDTO") @Valid String categoryDTOJson,
            @Parameter(description = "Ảnh danh mục (tuỳ chọn)")
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CategoryDTO categoryDTO = objectMapper.readValue(categoryDTOJson, CategoryDTO.class);
        CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO, imageFile);
        return ResponseEntity.ok(updatedCategory);
    }

    @Operation(summary = "Xóa danh mục")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable UUID id) throws IOException {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().body(Map.of(
                "message", "Delete successful",
                "status", HttpStatus.OK
        ));
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
    public ResponseEntity<TypeResponse<CategoryDTO>> getCategoryBySlug(@PathVariable String slug){
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
    public ResponseEntity<TypeResponse<List<GenderCategoryGroup>>>  getAllCategories() {
        TypeResponse<List<GenderCategoryGroup>> groupedCategories = iCategoryService.getAllCategoriesGroupByGender(null);
        return ResponseEntity.status(groupedCategories.getStatusCode()).body(groupedCategories);
    }

    @Operation(summary = "Lấy danh sách giới tính")
    @GetGenderDocs
    @GetMapping("/genders")
    public ResponseEntity<TypeResponse<List<GenderDTO>>> getAllGender() {
        TypeResponse<List<GenderDTO>> genders = iCategoryService.getAllGender();
        return ResponseEntity.status(genders.getStatusCode()).body(genders);
    }

    @Operation(summary = "Lấy danh mục theo slug giới tính")
    @GetAllCategoryDocs
    @GetMapping("/gender/slug/{slug}")
    public ResponseEntity<TypeResponse<List<GenderCategoryGroup>>> getAllByGenderBySlug(@PathVariable String slug) {
        TypeResponse<List<GenderCategoryGroup>> groups = iCategoryService.getAllCategoriesGroupByGender(slug);
        return ResponseEntity.status(groups.getStatusCode()).body(groups);
    }
}
