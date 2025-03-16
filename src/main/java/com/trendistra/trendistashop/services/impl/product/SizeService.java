package com.trendistra.trendistashop.services.impl.product;

import com.trendistra.trendistashop.Util.ResponseHelper;
import com.trendistra.trendistashop.dto.response.TypeResponse;
import com.trendistra.trendistashop.entities.product.Size;
import com.trendistra.trendistashop.repositories.product.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SizeService {
    @Autowired
    private SizeRepository sizeRepository;
    // Create a new Size
    public TypeResponse<Size> createSize(Size size) {
        try {
            Size newSize = sizeRepository.save(size);
            return ResponseHelper.ok(newSize, "Tạo size mới thành công");
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    // Get all Sizes
    public TypeResponse<List<Size>> getAllSizes() {
        try {
            List<Size> sizes = sizeRepository.findAll();
            return ResponseHelper.ok(sizes, "Lấy danh sách size thành công");
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    // Get a Size by ID
    public TypeResponse<Size> getSizeById(UUID id) {
        try {
            Optional<Size> size = sizeRepository.findById(id);
            if (size.isEmpty()) {
                return ResponseHelper.notFound("Không tìm thấy size với ID: " + id);
            }
            return ResponseHelper.ok(size.get(), "Lấy size thành công");
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    // Update a Size
    public TypeResponse<Size> updateSize(UUID id, Size updatedSize) {
        try {
            Optional<Size> size = sizeRepository.findById(id);
            if (size.isEmpty()) {
                return ResponseHelper.notFound("Không tìm thấy size với ID: " + id);
            }
            Size existingSize = size.get();
            existingSize.setValue(updatedSize.getValue());
            return ResponseHelper.ok(sizeRepository.save(existingSize), "Cập nhật size thành công");
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    // Delete a Size
    public TypeResponse<Void> deleteSize(UUID id) {
        try {
            Optional<Size> size = sizeRepository.findById(id);
            if (size.isEmpty()) {
                return ResponseHelper.notFound("Không tìm thấy size với ID: " + id);
            }
            sizeRepository.delete(size.get());
            return ResponseHelper.ok(null, "Xóa size thành công");
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }
}
