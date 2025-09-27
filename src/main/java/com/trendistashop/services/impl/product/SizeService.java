package com.trendistashop.services.impl.product;

import com.trendistashop.constants.ResponseMessage;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.entities.product.Size;
import com.trendistashop.repositories.product.SizeRepository;
import com.trendistashop.utils.ResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class SizeService {
    @Autowired
    private SizeRepository sizeRepository;

    // Create a new Size
    public TypeResponse<Size> createSize(Size size) {
        try {
            Size newSize = sizeRepository.save(size);
            return ResponseHelper.ok(newSize, ResponseMessage.CREATE_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.CREATE_FAILED);
        }
    }

    // Get all Sizes
    public TypeResponse<Page<Size>> getAllSizes(String keyword, int page, int size) {
        try {
            Sort sort = Sort.by("createdAt").descending();
            PageRequest pageRequest = PageRequest.of(page, size , sort);
            Specification<Size> spec = Specification.where(null);
            if(keyword!=null && !keyword.isEmpty()) {
                spec = spec.and((root, query, cb) -> cb.or(
                        cb.like(cb.lower(root.get("value")), "%"+keyword.trim().toLowerCase()+"%", '\\')
                ));
            }
            Page<Size> sizePage = sizeRepository.findAll(spec, pageRequest);
            log.info("Get size successfully: {} size", sizePage.getTotalElements());
            return ResponseHelper.ok(sizePage, ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }

    // Get a Size by ID
    public TypeResponse<Size> getSizeById(UUID id) {
        try {
            Optional<Size> size = sizeRepository.findById(id);
            if (size.isEmpty()) {
                return ResponseHelper.notFound(ResponseMessage.SIZE_NOT_FOUND);
            }
            return ResponseHelper.ok(size.get(), ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }

    // Update a Size
    public TypeResponse<Size> updateSize(UUID id, Size updatedSize) {
        try {
            Optional<Size> size = sizeRepository.findById(id);
            if (size.isEmpty()) {
                return ResponseHelper.notFound(ResponseMessage.SIZE_NOT_FOUND);
            }
            Size existingSize = size.get();
            existingSize.setValue(updatedSize.getValue());
            return ResponseHelper.ok(sizeRepository.save(existingSize), ResponseMessage.UPDATE_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.UPDATE_FAILED);
        }
    }

    // Delete a Size
    public TypeResponse<Void> deleteSize(UUID id) {
        try {
            Optional<Size> size = sizeRepository.findById(id);
            if (size.isEmpty()) {
                return ResponseHelper.notFound(ResponseMessage.SIZE_NOT_FOUND);
            }
            sizeRepository.delete(size.get());
            return ResponseHelper.ok(null, ResponseMessage.DELETE_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.DELETE_FAILED);
        }
    }
}
