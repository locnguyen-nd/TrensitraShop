package com.trendistra.trendistashop.services.impl.category;

import com.trendistra.trendistashop.dto.response.CategoryDTO;
import com.trendistra.trendistashop.dto.response.DiscountDTO;
import com.trendistra.trendistashop.dto.response.GenderCategoryGroup;
import com.trendistra.trendistashop.dto.response.GenderDTO;
import com.trendistra.trendistashop.entities.category.Category;
import com.trendistra.trendistashop.entities.category.Gender;
import com.trendistra.trendistashop.entities.product.Discount;
import com.trendistra.trendistashop.exceptions.DataAccessException;
import com.trendistra.trendistashop.exceptions.ResourceNotFoundEx;
import com.trendistra.trendistashop.helper.GenerateSlug;
import com.trendistra.trendistashop.repositories.category.CategoryRepository;
import com.trendistra.trendistashop.repositories.category.GenderRepository;
import com.trendistra.trendistashop.services.CloudinaryService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.Normalizer;
import java.util.*;
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
    @Autowired
    private ModelMapper modelMapper;

    public List<GenderDTO> getAllGender () {
        return genderRepository.findAll().stream()
                .map(gender -> modelMapper.map(gender, GenderDTO.class)).collect(Collectors.toList());
    }
    public GenderDTO createGender(GenderDTO genderDTO, MultipartFile imageGender) throws IOException {
        Boolean existsByName = genderRepository.existsByName(genderDTO.getName());
        if(existsByName) {
            throw new DataAccessException("Giới tính này đã tồn tại");
        }
        if(imageGender != null || !imageGender.isEmpty()) {
            genderDTO.setImageUrl(cloudinaryService.uploadFile(imageGender,null, "GENDERS" ));
        }
        if(genderDTO.getSlug() == null) {
            genderDTO.setSlug(generateSlug.generateSlug(genderDTO.getName()));
        }
        Gender gender = Gender.builder()
                .name(genderDTO.getName())
                .slug(genderDTO.getSlug())
                .imageUrl(genderDTO.getImageUrl())
                .build();
        return modelMapper.map(genderRepository.save(gender), GenderDTO.class);
    }


    // Tạo mới category
    @Transactional
    public CategoryDTO createCategory(CategoryDTO categoryDTO, MultipartFile imageFile) throws IOException {
        Category category = new Category();
        // Upload ảnh
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(imageFile, null, "CATEGORIES");
            category.setImageUrl(imageUrl);
        }
        // Thiết lập các trường
        category.setName(categoryDTO.getName());
        category.setSlug(generateSlug.generateSlug(categoryDTO.getName()));
        category.setDescription(categoryDTO.getDescription());

        // Thiết lập danh mục cha
        if (categoryDTO.getParentId() != null) {
            Category parentCategory = categoryRepository.findById(categoryDTO.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundEx("Parent category not found"));
            category.setParent(parentCategory);
        }
        // Thiết lập giới tính
        Gender gender = genderRepository.findById(categoryDTO.getGender().getId())
                .orElseThrow(() -> new ResourceNotFoundEx("Gender not found"));
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
                .orElseThrow(() -> new ResourceNotFoundEx("Category not found"));

        // Xóa ảnh cũ nếu có
        if (existingCategory.getImageUrl() != null) {
            String oldPublicId = extractPublicIdFromUrl(existingCategory.getImageUrl());
            cloudinaryService.deleteFile(oldPublicId);
        }

        // Upload ảnh mới
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(imageFile, null, "CATEGORIES");
            existingCategory.setImageUrl(imageUrl);
        }

        // Cập nhật các trường
        existingCategory.setName(categoryDTO.getName());
        existingCategory.setSlug(generateSlug.generateSlug(categoryDTO.getName()));
        existingCategory.setDescription(categoryDTO.getDescription());

        // Cập nhật danh mục cha
        if (categoryDTO.getParentId() != null) {
            Category parentCategory = categoryRepository.findById(categoryDTO.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundEx("Parent category not found"));
            existingCategory.setParent(parentCategory);
        }

        // Cập nhật giới tính
        Gender gender = genderRepository.findById(categoryDTO.getGender().getId())
                .orElseThrow(() -> new ResourceNotFoundEx("Gender not found"));
        existingCategory.setGender(gender);

        // Lưu thay đổi
        Category updatedCategory = categoryRepository.save(existingCategory);
        return convertToDTO(updatedCategory);
    }

    // Xóa category
    @Transactional
    public void deleteCategory(UUID id) throws IOException {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEx("Category not found"));

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
                .orElseThrow(() -> new ResourceNotFoundEx("Category not found"));
        return convertToDTO(category);
    }
    public CategoryDTO getCategoryBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundEx("Category not found"));
        return convertToDTO(category);
    }
    // Lấy tất cả categories
    public List<GenderCategoryGroup> getAllCategoriesGroupByGender(String genderSlug) {
        List<Category> categories;
        if (genderSlug != null && !genderSlug.isEmpty()) {
            categories = categoryRepository.findByGenderSlug(genderSlug); // Giả sử bạn có phương thức findByGenderSlug
        } else {
            categories = categoryRepository.findAll(); // Nếu không có genderSlug, lấy tất cả danh mục
        }
        Map<Gender, List<Category>> groupedByGender = categories.stream()
                .collect(Collectors.groupingBy(category -> category.getGender()));
        // Chuyển đổi sang DTO và xây dựng Map<GenderDTO, List<CategoryDTO>>
       List<GenderCategoryGroup> result = new ArrayList<>();
        groupedByGender.forEach((gender, genderCategories) -> {
            // Chuyển Gender sang GenderDTO
            GenderDTO genderDTO = convertGenderDTO(gender);
            // Lọc danh mục cha và ánh xạ sang DTO
            List<CategoryDTO> parentCategories = genderCategories.stream()
                    .filter(category -> category.getParent() == null) // Lọc danh mục cha
                    .map(parent -> mapToCategoryDTO(parent, genderCategories)) // Ánh xạ sang DTO
                    .collect(Collectors.toList());
            // Đưa vào Map
            result.add(new GenderCategoryGroup(genderDTO, parentCategories));
        });
        return result;
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
        return "CATEGORIES/" + filename.split("\\.")[0];
    }

    // Chuyển đổi giữa Entity và DTO
    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setSlug(category.getSlug());
        dto.setDescription(category.getDescription());
        dto.setImageUrl(category.getImageUrl());
        dto.setIndex(category.getIndexNum());
        if (category.getGender() != null) {
            dto.setGender(convertGenderDTO(category.getGender()));
        }
        if (category.getParent() != null) {
            dto.setParentId(category.getParent().getId());
        }
        return dto;
    }
    private GenderDTO convertGenderDTO(Gender gender){
        GenderDTO genderDTO = new GenderDTO().builder()
                .id(gender.getId())
                .slug(gender.getSlug())
                .name(gender.getName())
                .imageUrl(gender.getImageUrl())
                .build();
        return  genderDTO;
    }

    private  CategoryDTO mapToCategoryDTO (Category parent , List<Category> allCategories) {
        List<Category> childCategories = allCategories.stream()
                .filter(category -> parent.equals(category.getParent())) // Lọc danh mục con
                .collect(Collectors.toList());

        // Chuyển đổi danh mục con sang DTO
        List<CategoryDTO> children = new ArrayList<>();
        childCategories.sort(Comparator.comparingInt(category-> category.getIndexNum() != null ? category.getIndexNum() : Integer.MAX_VALUE));
        for (int i = 0; i < childCategories.size(); i++) {
            Category child = childCategories.get(i);
            CategoryDTO childDTO = mapToCategoryDTO(child, allCategories);
            children.add(childDTO);
        }
        return new CategoryDTO().builder()
                .id(parent.getId())
                .slug(parent.getSlug())
                .name(parent.getName())
                .description(parent.getDescription())
                .imageUrl(parent.getImageUrl())
                .items(children)
                .index(parent.getIndexNum())
                .build();
    }
}
