package com.trendistra.trendistashop.controllers.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendistra.trendistashop.dto.response.CategoryDTO;
import com.trendistra.trendistashop.dto.response.GenderDTO;
import com.trendistra.trendistashop.entities.category.Gender;
import com.trendistra.trendistashop.services.impl.category.CategoryService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/categories")
@CrossOrigin
@Tag(name = "Categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryDTO> createCategory(
            @RequestPart("categoryDTO") String categoryDTOJson,
            @RequestParam(required = false) MultipartFile imageFile
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CategoryDTO categoryDTO = objectMapper.readValue(categoryDTOJson, CategoryDTO.class);
        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO, imageFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }
    @PostMapping("/gender")
    public ResponseEntity<GenderDTO> createGender(@RequestBody GenderDTO genderDTO) {
        GenderDTO genderDTO1 = categoryService.createGender(genderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(genderDTO1);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable UUID id,
            @ModelAttribute CategoryDTO categoryDTO,
            @RequestParam(required = false) MultipartFile imageFile
    ) throws IOException {
        CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO, imageFile);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) throws IOException {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable UUID id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }
    @GetMapping("/parent/{id}")
    public ResponseEntity<List<CategoryDTO>> getAllByParent(@PathVariable UUID id) {
        List<CategoryDTO> categories = categoryService.getAllCategoriesByParenId(id);
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/gender/{id}")
    public ResponseEntity<List<CategoryDTO>> getAllByGender(@PathVariable UUID id) {
        List<CategoryDTO> categories = categoryService.getAllCategoriesByGenderId(id);
        return ResponseEntity.ok(categories);
    }
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/genders")
    public ResponseEntity<List<GenderDTO>> getAllGender() {
        List<GenderDTO> genders = categoryService.getAllGender();
        return ResponseEntity.ok(genders);
    }
}
