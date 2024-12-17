package com.trendistra.trendistashop.services.impl.product;

import com.trendistra.trendistashop.entities.product.Size;
import com.trendistra.trendistashop.exceptions.ResourceNotFoundEx;
import com.trendistra.trendistashop.repositories.product.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SizeService {
    @Autowired
    private SizeRepository sizeRepository;
    // Create a new Size
    public Size createSize(Size size) {
        return sizeRepository.save(size);
    }

    // Get all Sizes
    public List<Size> getAllSizes() {
        return sizeRepository.findAll();
    }

    // Get a Size by ID
    public Size getSizeById(UUID id) {
        return sizeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEx("Size not found with ID: " + id));
    }

    // Update a Size
    public Size updateSize(UUID id, Size updatedSize) {
        Size existingSize = getSizeById(id);
        existingSize.setName(updatedSize.getName());
        existingSize.setValue(updatedSize.getValue());
        return sizeRepository.save(existingSize);
    }

    // Delete a Size
    public void deleteSize(UUID id) {
        Size size = getSizeById(id);
        sizeRepository.delete(size);
    }
}
