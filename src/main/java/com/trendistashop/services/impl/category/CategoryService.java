package com.trendistashop.services.impl.category;

import com.trendistashop.entities.category.Category;
import com.trendistashop.entities.category.Gender;
import com.trendistashop.services.CloudinaryService;
import com.trendistashop.utils.ResponseHelper;
import com.trendistashop.constants.ResponseMessage;
import com.trendistashop.dto.request.CategoryCreUpDTO;
import com.trendistashop.dto.response.CategoryDTO;
import com.trendistashop.dto.response.GenderCategoryGroup;
import com.trendistashop.dto.response.GenderDTO;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.helper.GenerateSlug;
import com.trendistashop.repositories.category.CategoryRepository;
import com.trendistashop.repositories.category.GenderRepository;
import com.trendistashop.services.ICategoryService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
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
                return ResponseHelper.notFound(ResponseMessage.GENDER_NOT_FOUND);
            }
            log.info("Get Gender successfully");
            return ResponseHelper.ok(categories, ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }

    @Override
    public TypeResponse<GenderDTO> createGender(GenderDTO genderDTO) {
        boolean existsByName = genderRepository.existsByName(genderDTO.getName());
        if (existsByName) {
            return ResponseHelper.badRequest(ResponseMessage.GENDER_EXISTS);
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
            return ResponseHelper.created(modelMapper.map(genderRepository.save(gender), GenderDTO.class),
                    ResponseMessage.CREATE_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseHelper.serverError(ResponseMessage.CREATE_FAILED);
        }
    }

    @Override
    public TypeResponse<Void> deleteGender(UUID id) {
        try {
            Gender gender = genderRepository.findById(id).get();
            if (gender == null) {
                return ResponseHelper.notFound(ResponseMessage.GENDER_NOT_FOUND);
            }
            gender.preDestroy();
            genderRepository.save(gender);
            return ResponseHelper.ok(null, ResponseMessage.DELETE_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseHelper.serverError(ResponseMessage.DELETE_FAILED);
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
                    return ResponseHelper.notFound(ResponseMessage.CATEGORY_NOT_FOUND);
                }
                category.setParent(parentCategory);
            }
            if (categoryDTO.getGender() != null) {
                Gender gender = genderRepository.findById(categoryDTO.getGender()).get();
                if (gender == null) {
                    return ResponseHelper.notFound(ResponseMessage.CATEGORY_NOT_FOUND);
                }
                category.setGender(gender);
            }
            category.setName(categoryDTO.getName());
            category.setSlug(GenerateSlug.generateSlug(categoryDTO.getName()));
            category.setDescription(categoryDTO.getDescription());
            category.setImageUrl(categoryDTO.getImageUrl());
            category.setIndexNum(categoryDTO.getIndex());
            category.setCreatedAt(new Date());
            Category savedCategory = categoryRepository.save(category);
            return ResponseHelper.created(convertToDTO(savedCategory), ResponseMessage.CREATE_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseHelper.serverError(ResponseMessage.CREATE_FAILED);
        }
    }

    // Cập nhật category
    @Transactional
    @Override
    public TypeResponse<CategoryDTO> updateCategory(UUID id, CategoryCreUpDTO categoryDTO) {
        try {
            Category existingCategory = categoryRepository.findById(id).get();
            if (existingCategory == null) {
                return ResponseHelper.notFound(ResponseMessage.CATEGORY_NOT_FOUND);
            }
            // Cập nhật các trường nếu dữ liệu mới khác dữ liệu cũ
            if (!existingCategory.getName().equals(categoryDTO.getName())) {
                existingCategory.setName(categoryDTO.getName());
                existingCategory.setSlug(GenerateSlug.generateSlug(categoryDTO.getName()));
            }
            if(existingCategory.getImageUrl() != null && !existingCategory.getImageUrl().equals(categoryDTO.getImageUrl())) {
                cloudinaryService.deleteFile(existingCategory.getImageUrl());
            }
            existingCategory.setImageUrl(categoryDTO.getImageUrl());
            existingCategory.setDescription(categoryDTO.getDescription());
            if (categoryDTO.getParent() != null) {
                Category parentCategory = categoryRepository.findById(categoryDTO.getParent()).get();
                if (parentCategory == null) {
                    return ResponseHelper.notFound(ResponseMessage.CATEGORY_NOT_FOUND);
                }
                existingCategory.setParent(parentCategory);
            }
            if (categoryDTO.getGender() != null) {
                Gender gender = genderRepository.findById(categoryDTO.getGender()).get();
                if (gender == null) {
                    return ResponseHelper.notFound(ResponseMessage.CATEGORY_NOT_FOUND);
                }
                existingCategory.setGender(gender);
            }
            existingCategory.setIndexNum(categoryDTO.getIndex());
            existingCategory.setUpdatedAt(new Date());
            Category updatedCategory = categoryRepository.save(existingCategory);
            return ResponseHelper.ok(convertToDTO(updatedCategory), ResponseMessage.UPDATE_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseHelper.serverError(ResponseMessage.UPDATE_FAILED);
        }
    }

    // Xóa category
    @Transactional
    @Override
    public TypeResponse<Void> deleteCategory(UUID id) {
        try {
            Category category = categoryRepository.findById(id).get();
            if (category == null) {
                return ResponseHelper.notFound(ResponseMessage.CATEGORY_NOT_FOUND);
            }
            category.preDestroy();
            categoryRepository.save(category);
            return ResponseHelper.ok(null, ResponseMessage.DELETE_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseHelper.serverError(ResponseMessage.DELETE_FAILED);
        }
    }

    @Override
    // Lấy category theo ID
    public TypeResponse<CategoryDTO> getCategoryById(UUID id) {
        try {
            Optional<Category> category = categoryRepository.findById(id);
            if (category.isEmpty()) {
                return ResponseHelper.notFound(ResponseMessage.CATEGORY_NOT_FOUND);
            }
            return ResponseHelper.ok(convertToDTO(category.get()), ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }

    // Lấy category theo slug
    @Override
    public TypeResponse<CategoryDTO> getCategoryBySlug(String slug) {
        try {
            Category category = categoryRepository.findBySlug(slug);
            if (category  ==  null) {
                return ResponseHelper.notFound(ResponseMessage.CATEGORY_NOT_FOUND);
            }
            return ResponseHelper.ok(convertToDTO(category), ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }

    // Lấy tất cả categories
    @Override
    public TypeResponse<List<GenderCategoryGroup>> getAllCategoriesGroupByGender(String genderSlug, Boolean isActive) {
        try {
            List<Category> categories = categoryRepository.findByGenderSlug(genderSlug, null); // lấy tất cả trước
            List<Category> parentCategories = categories.stream()
                    .filter(c -> c.getParent() == null)
                    .collect(Collectors.toList());

            Map<Gender, List<Category>> groupedByGender = new HashMap<>();
            for (Category parent : parentCategories) {
                List<Category> childCategories = categories.stream()
                        .filter(c -> parent.equals(c.getParent()) &&
                                (isActive == null || (isActive ? c.getDeletedAt() == null : c.getDeletedAt() != null)))
                        .collect(Collectors.toList());
                boolean parentMatches = isActive == null || (isActive ? parent.getDeletedAt() == null : parent.getDeletedAt() != null);
                if (parentMatches || !childCategories.isEmpty()) {
                    groupedByGender.computeIfAbsent(parent.getGender(), k -> new ArrayList<>()).add(parent);
                }
            }

            List<GenderCategoryGroup> result = new ArrayList<>();
            groupedByGender.forEach((gender, parents) -> {
                GenderDTO genderDTO = convertGenderDTO(gender);

                List<CategoryDTO> parentDTOs = parents.stream()
                        .map(parent -> mapToCategoryDTO(parent, categories, isActive))
                        .collect(Collectors.toList());

                result.add(new GenderCategoryGroup(genderDTO, parentDTOs));
            });

            return ResponseHelper.ok(result, ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }


    @Override
    public TypeResponse<List<CategoryDTO>> searchCategoryByName(String name) {
        try {
            List<Category> categories = categoryRepository.searchWithKeyword(name);
            if (categories.isEmpty()) {
                return ResponseHelper.notFound(ResponseMessage.CATEGORY_NOT_FOUND);
            }
            List<CategoryDTO> categoryDTOS = categories.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return ResponseHelper.ok(categoryDTOS, ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }
    @Override
    public TypeResponse<List<GenderDTO>> searchGenderByName(String name) {
        try {
            List<Gender> genders = genderRepository.searchGenderWithKeyword(name);
            if (genders.isEmpty()) {
                return ResponseHelper.notFound(ResponseMessage.CATEGORY_NOT_FOUND);
            }
            List<GenderDTO> genderDTOS = genders.stream()
                    .map(this::convertGenderDTO)
                    .collect(Collectors.toList());
            return ResponseHelper.ok(genderDTOS, ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }

    @Override
    public TypeResponse<CategoryDTO> restoreCategory(UUID id) {
        try  {
            Optional<Category> optionalCategory = categoryRepository.findById(id);
            if (optionalCategory.isEmpty()) {
                return ResponseHelper.notFound(ResponseMessage.CATEGORY_NOT_FOUND);
            }
            Category category = optionalCategory.get();
            category.setDeletedAt(null);
            categoryRepository.save(category);
            log.info("Restore category successfully");
            return ResponseHelper.ok(convertToDTO(category), ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            log.error("Restore category error {}", e.getMessage());
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
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
                return ResponseHelper.notFound(ResponseMessage.CATEGORY_NOT_FOUND);
            }
            return ResponseHelper.ok(categories, ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }

    // Lấy tất cả categories theo genderId
    @Override
    public TypeResponse<List<CategoryDTO>> getAllCategoriesByGenderId(UUID genderId) {
        try {
            List<CategoryDTO> categories = categoryRepository.findByGenderId(genderId).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return ResponseHelper.ok(categories, ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
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
        dto.setActive(category.getDeletedAt() == null);
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

    private CategoryDTO mapToCategoryDTO(Category parent, List<Category> allCategories, Boolean isActive) {
        // Lọc danh mục con theo parent và isActive
        List<Category> childCategories = allCategories.stream()
                .filter(category -> parent.equals(category.getParent()) &&
                        (isActive == null ||
                                (isActive ? category.getDeletedAt() == null : category.getDeletedAt() != null)))
                .collect(Collectors.toList());

        List<CategoryDTO> children = new ArrayList<>();
        childCategories.sort(Comparator
                .comparingInt(category -> category.getIndexNum() != null ? category.getIndexNum() : Integer.MAX_VALUE));

        for (Category child : childCategories) {
            children.add(mapToCategoryDTO(child, allCategories, isActive));
        }

        return new CategoryDTO().builder()
                .id(parent.getId())
                .slug(parent.getSlug())
                .name(parent.getName())
                .description(parent.getDescription())
                .imageUrl(parent.getImageUrl())
                .items(children)
                .index(parent.getIndexNum() != null ? parent.getIndexNum() : Integer.MAX_VALUE)
                .active(parent.getDeletedAt() == null)
                .build();
    }
}
