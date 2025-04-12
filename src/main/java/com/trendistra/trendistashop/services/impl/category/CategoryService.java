package com.trendistra.trendistashop.services.impl.category;

import com.trendistra.trendistashop.Util.ResponseHelper;
import com.trendistra.trendistashop.dto.request.CategoryCreUpDTO;
import com.trendistra.trendistashop.dto.response.CategoryDTO;
import com.trendistra.trendistashop.dto.response.GenderCategoryGroup;
import com.trendistra.trendistashop.dto.response.GenderDTO;
import com.trendistra.trendistashop.dto.response.TypeResponse;
import com.trendistra.trendistashop.entities.category.Category;
import com.trendistra.trendistashop.entities.category.Gender;
import com.trendistra.trendistashop.helper.GenerateSlug;
import com.trendistra.trendistashop.repositories.category.CategoryRepository;
import com.trendistra.trendistashop.repositories.category.GenderRepository;
import com.trendistra.trendistashop.services.CloudinaryService;
import com.trendistra.trendistashop.services.ICategoryService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private GenderRepository genderRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TypeResponse<List<GenderDTO>> getAllGender() {
        try {
            List<GenderDTO> categories = genderRepository.findAll().stream()
                    .map(gender -> modelMapper.map(gender, GenderDTO.class)).collect(Collectors.toList());
            if (categories.isEmpty()) {
                return ResponseHelper.notFound("Không tìm thấy giới tính");
            }
            return ResponseHelper.ok(categories, "Lấy danh sách giới tính thành công");
        } catch (Exception e) {
            return ResponseHelper.serverError("Lỗi server");
        }
    }

    @Override
    public TypeResponse<GenderDTO> createGender(GenderDTO genderDTO) {
        boolean existsByName = genderRepository.existsByName(genderDTO.getName());
        if (existsByName) {
            return ResponseHelper.badRequest("Gender này đã tông tại");
        }
        try {
            if (genderDTO.getName() != null) {
                genderDTO.setSlug(GenerateSlug.generateSlug(genderDTO.getName()));
            }
            Gender gender = Gender.builder()
                    .name(genderDTO.getName())
                    .slug(genderDTO.getSlug())
                    .imageUrl(genderDTO.getImageUrl())
                    .build();
            return ResponseHelper.created(modelMapper.map(genderRepository.save(gender), GenderDTO.class), "Tạo giới tính thành công");
        } catch (Exception e) {
            return ResponseHelper.serverError("Lỗi server");
        }
    }

    @Transactional
    @Override
    public TypeResponse<CategoryDTO> createCategory(CategoryCreUpDTO categoryDTO) {
        try {
            Category category = new Category();
            if (categoryDTO.getParent() != null) {
                Category parentCategory = categoryRepository.findById(categoryDTO.getParent()).get();
                if (parentCategory == null) {
                    return ResponseHelper.notFound("Không tìm thấy danh mục cha");
                }
                category.setParent(parentCategory);
            }
            if (categoryDTO.getGender() != null) {
                Gender gender = genderRepository.findById(categoryDTO.getGender()).get();
                if (gender == null) {
                    return ResponseHelper.notFound("Không tìm thấy danh mục cha");
                }
                category.setGender(gender);
            }
            category.setName(categoryDTO.getName());
            category.setSlug(GenerateSlug.generateSlug(categoryDTO.getName()));
            category.setDescription(categoryDTO.getDescription());
            category.setImageUrl(categoryDTO.getImageUrl());
            category.setCreatedAt(new Date());
            Category savedCategory = categoryRepository.save(category);
            return ResponseHelper.created(convertToDTO(savedCategory), "Tạo danh mục thành công");
        } catch (Exception e) {
            return ResponseHelper.serverError("Lỗi server");
        }
    }

    // Cập nhật category
    @Transactional
    @Override
    public TypeResponse<CategoryDTO> updateCategory(UUID id, CategoryCreUpDTO categoryDTO) {
        try {
            Category existingCategory = categoryRepository.findById(id).get();
            if (existingCategory == null) {
                return ResponseHelper.notFound("Không tìm thấy danh mục cần cập nhật");
            }
            // Cập nhật các trường nếu dữ liệu mới khác dữ liệu cũ
            if (!existingCategory.getName().equals(categoryDTO.getName())) {
                existingCategory.setName(categoryDTO.getName());
                existingCategory.setSlug(GenerateSlug.generateSlug(categoryDTO.getName()));
            }
            existingCategory.setImageUrl(categoryDTO.getImageUrl());
            existingCategory.setDescription(categoryDTO.getDescription());
            if (categoryDTO.getParent() != null) {
                Category parentCategory = categoryRepository.findById(categoryDTO.getParent()).get();
                if (parentCategory == null) {
                    return ResponseHelper.notFound("Không tìm thấy danh mục cha");
                }
                existingCategory.setParent(parentCategory);
            }
            if (categoryDTO.getGender() != null) {
                Gender gender = genderRepository.findById(categoryDTO.getGender()).get();
                if (gender == null) {
                    return ResponseHelper.notFound("Không tìm thấy danh mục cha");
                }
                existingCategory.setGender(gender);
            }
            existingCategory.setUpdatedAt(new Date());
            Category updatedCategory = categoryRepository.save(existingCategory);
            return ResponseHelper.ok(convertToDTO(updatedCategory), "Tạo danh mục thành công");
        } catch (Exception e) {
            return ResponseHelper.serverError("Lỗi server");
        }
    }

    // Xóa category
    @Transactional
    @Override
    public TypeResponse<Void> deleteCategory(UUID id) {
        try {
            Category category = categoryRepository.findById(id).get();
            if (category == null) {
                return ResponseHelper.notFound("Không tìm thấy danh mục cần xóa");
            }
            if (category.getImageUrl() != null) {
                cloudinaryService.deleteFile(category.getImageUrl());
            }
            category.preDestroy();
            category.setImageUrl(null);
            categoryRepository.save(category);
            return ResponseHelper.ok(null, "Xóa danh mục thành công");
        } catch (Exception e) {
            return ResponseHelper.serverError("Lỗi server");
        }
    }

    @Override
    // Lấy category theo ID
    public TypeResponse<CategoryDTO> getCategoryById(UUID id) {
        try {
            Optional<Category> category = categoryRepository.findById(id);
            if (category.isEmpty()) {
                return ResponseHelper.notFound("Không tìm thấy danh mục");
            }
            return ResponseHelper.ok(convertToDTO(category.get()), "Lấy danh mục thành công");
        } catch (Exception e) {
            return ResponseHelper.serverError("Lỗi server");
        }
    }

    // Lấy category theo slug
    @Override
    public TypeResponse<CategoryDTO> getCategoryBySlug(String slug) {
        try {
            Optional<Category> category = categoryRepository.findBySlug(slug);
            if (category.isEmpty()) {
                return ResponseHelper.notFound("Không tìm thấy danh mục");
            }
            return ResponseHelper.ok(convertToDTO(category.get()), "Lấy danh mục thành công");
        } catch (Exception e) {
            return ResponseHelper.serverError("Lỗi server");
        }
    }

    // Lấy tất cả categories
    @Override
    public TypeResponse<List<GenderCategoryGroup>> getAllCategoriesGroupByGender(String genderSlug) {
        try {
            List<Category> categories;
            if (genderSlug != null && !genderSlug.isEmpty()) {
                categories = categoryRepository.findByGenderSlug(genderSlug);
            } else {
                categories = categoryRepository.findAll(); // Nếu không có genderSlug, lấy tất cả danh mục
            }
            Map<Gender, List<Category>> groupedByGender = categories.stream()
                    .collect(Collectors.groupingBy(category -> category.getGender()));
            List<GenderCategoryGroup> result = new ArrayList<>();
            groupedByGender.forEach((gender, genderCategories) -> {
                GenderDTO genderDTO = convertGenderDTO(gender);
                List<CategoryDTO> parentCategories = genderCategories.stream()
                        .filter(category -> category.getParent() == null) // Lọc danh mục cha
                        .map(parent -> mapToCategoryDTO(parent, genderCategories)) // Ánh xạ sang DTO
                        .collect(Collectors.toList());
                // Đưa vào Map
                result.add(new GenderCategoryGroup(genderDTO, parentCategories));
            });
            return ResponseHelper.ok(result, "Lấy danh sách danh mục thành công");
        } catch (Exception e) {
            return ResponseHelper.serverError("Lỗi server");
        }
    }

    // Lấy tất cả categories theo parentId
    @Override
    public TypeResponse<List<CategoryDTO>> getAllCategoriesByParentId(UUID parentId) {
        try {
            List<CategoryDTO> categories = categoryRepository.findByParentId(parentId).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            if (categories.isEmpty()) {
                return ResponseHelper.notFound("Không tìm thấy danh mục");
            }
            return ResponseHelper.ok(categories, "Lấy danh sách danh mục thành công");
        } catch (Exception e) {
            return ResponseHelper.serverError("Lỗi server");
        }
    }

    // Lấy tất cả categories theo genderId
    @Override
    public TypeResponse<List<CategoryDTO>> getAllCategoriesByGenderId(UUID genderId) {
        try {
            List<CategoryDTO> categories = categoryRepository.findByGenderId(genderId).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return ResponseHelper.ok(categories, "Lấy danh sách danh mục thành công");
        } catch (Exception e) {
            return ResponseHelper.serverError("Lỗi server");
        }
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

    private GenderDTO convertGenderDTO(Gender gender) {
        GenderDTO genderDTO = new GenderDTO().builder()
                .id(gender.getId())
                .slug(gender.getSlug())
                .name(gender.getName())
                .imageUrl(gender.getImageUrl())
                .build();
        return genderDTO;
    }

    private CategoryDTO mapToCategoryDTO(Category parent, List<Category> allCategories) {
        List<Category> childCategories = allCategories.stream()
                .filter(category -> parent.equals(category.getParent())) // Lọc danh mục con
                .collect(Collectors.toList());

        // Chuyển đổi danh mục con sang DTO
        List<CategoryDTO> children = new ArrayList<>();
        childCategories.sort(Comparator.comparingInt(category -> category.getIndexNum() != null ? category.getIndexNum() : Integer.MAX_VALUE));
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
