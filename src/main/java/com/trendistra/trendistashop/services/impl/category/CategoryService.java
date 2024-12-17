package com.trendistra.trendistashop.services.impl.category;

import com.trendistra.trendistashop.dto.response.CategoryDTO;
import com.trendistra.trendistashop.dto.response.DiscountDTO;
import com.trendistra.trendistashop.dto.response.GenderDTO;
import com.trendistra.trendistashop.entities.category.Category;
import com.trendistra.trendistashop.entities.category.Gender;
import com.trendistra.trendistashop.entities.product.Discount;
import com.trendistra.trendistashop.helper.GenerateSlug;
import com.trendistra.trendistashop.repositories.category.CategoryRepository;
import com.trendistra.trendistashop.repositories.category.GenderRepository;
import com.trendistra.trendistashop.services.CloudinaryService;
import com.trendistra.trendistashop.services.impl.product.DiscountService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.Normalizer;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private  CloudinaryService cloudinaryService;
    @Autowired
    private GenderRepository genderRepository;
    private GenerateSlug generateSlug;

    public List<GenderDTO> getAllGender () {
        return genderRepository.findAll().stream()
                .map(gender -> new GenderDTO(
                        gender.getId(),
                        gender.getName()
                )).collect(Collectors.toList());
    }

    // Tạo mới category
    @Transactional
    public CategoryDTO createCategory(CategoryDTO categoryDTO, MultipartFile imageFile) throws IOException {
        Category category = new Category();
        // Upload ảnh
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(imageFile, null, "categories");
            category.setImageUrl(imageUrl);
        }
        // Thiết lập các trường
        category.setName(categoryDTO.getName());
        category.setSlug(generateSlug.generateSlug(categoryDTO.getName()));
        category.setDescription(categoryDTO.getDescription());

        // Thiết lập danh mục cha
        if (categoryDTO.getParentId() != null) {
            Category parentCategory = categoryRepository.findById(categoryDTO.getParentId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent category not found"));
            category.setParent(parentCategory);
        }
        // Thiết lập giới tính
        Gender gender = genderRepository.findById(categoryDTO.getGenderDTO().getId())
                .orElseThrow(() -> new EntityNotFoundException("Gender not found"));
        category.setGender(gender);
        // Lưu category
        Category savedCategory = categoryRepository.save(category);
        // Chuyển đổi sang DTO
        return convertToDTO(savedCategory);
    }

    // Cập nhật category
    @Transactional
    public CategoryDTO updateCategory(UUID id, CategoryDTO categoryDTO, MultipartFile imageFile) throws IOException {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        // Xóa ảnh cũ nếu có
        if (existingCategory.getImageUrl() != null) {
            String oldPublicId = extractPublicIdFromUrl(existingCategory.getImageUrl());
            cloudinaryService.deleteFile(oldPublicId);
        }

        // Upload ảnh mới
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(imageFile, null, "categories");
            existingCategory.setImageUrl(imageUrl);
        }

        // Cập nhật các trường
        existingCategory.setName(categoryDTO.getName());
        existingCategory.setSlug(generateSlug.generateSlug(categoryDTO.getName()));
        existingCategory.setDescription(categoryDTO.getDescription());

        // Cập nhật danh mục cha
        if (categoryDTO.getParentId() != null) {
            Category parentCategory = categoryRepository.findById(categoryDTO.getParentId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent category not found"));
            existingCategory.setParent(parentCategory);
        }

        // Cập nhật giới tính
        Gender gender = genderRepository.findById(categoryDTO.getGenderDTO().getId())
                .orElseThrow(() -> new EntityNotFoundException("Gender not found"));
        existingCategory.setGender(gender);

        // Lưu thay đổi
        Category updatedCategory = categoryRepository.save(existingCategory);
        return convertToDTO(updatedCategory);
    }

    // Xóa category
    @Transactional
    public void deleteCategory(UUID id) throws IOException {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        // Xóa ảnh trên Cloudinary nếu có
        if (category.getImageUrl() != null) {
            String publicId = extractPublicIdFromUrl(category.getImageUrl());
            cloudinaryService.deleteFile(publicId);
        }

        categoryRepository.delete(category);
    }

    // Lấy category theo ID
    public CategoryDTO getCategoryById(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        return convertToDTO(category);
    }


    // Lấy tất cả categories
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public List<CategoryDTO> getAllCategoriesByParenId(UUID parentId) {
        return categoryRepository.findByParentId(parentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public List<CategoryDTO> getAllCategoriesByGenderId(UUID genderId) {
        return categoryRepository.findByGenderId(genderId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    // Trích xuất publicId từ Cloudinary URL
    private String extractPublicIdFromUrl(String url) {
        String[] parts = url.split("/");
        String filename = parts[parts.length - 1];
        return "categories/" + filename.split("\\.")[0];
    }

    // Chuyển đổi giữa Entity và DTO
    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setSlug(category.getSlug());
        dto.setDescription(category.getDescription());
        dto.setImageUrl(category.getImageUrl());
        if (category.getGender() != null) {
            dto.setGenderDTO(convertGenderDTO(category.getGender()));
        }
        if(!category.getDiscounts().isEmpty()) {
            dto.setDiscounts(category.getDiscounts().stream()
                    .filter(discount -> discount.getIsActive().equals(true))
                    .map( discount ->
                    mapToDiscountDto(discount)
            ).collect(Collectors.toList()));
        }
        if (category.getParent() != null) {
            dto.setParentId(category.getParent().getId());
        }
        return dto;
    }
    private GenderDTO convertGenderDTO(Gender gender){
        GenderDTO genderDTO = new GenderDTO();
        genderDTO.setId(gender.getId());
        genderDTO.setName(gender.getName());
        return  genderDTO;
    }
    public DiscountDTO mapToDiscountDto(Discount discount) {
        return DiscountDTO.builder()
                .id(discount.getId())
                .name(discount.getName())
                .frame(discount.getFrame())
                .description(discount.getDescription())
                .discountType(discount.getDiscountType())
                .discountValue(discount.getDiscountValue())
                .maxDiscountValue(discount.getMaxDiscountValue())
                .minOrderValue(discount.getMinOrderValue())
                .startDate(discount.getStartDate())
                .endDate(discount.getEndDate())
                .isActive(discount.getIsActive())
                .build();
    }
}
