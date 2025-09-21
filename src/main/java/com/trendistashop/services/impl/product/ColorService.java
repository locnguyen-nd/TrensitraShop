package com.trendistashop.services.impl.product;

import com.trendistashop.constants.ResponseMessage;
import com.trendistashop.dto.response.ColorDTO;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.entities.product.Color;
import com.trendistashop.repositories.product.ColorRepository;
import com.trendistashop.utils.ResponseHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;
import java.util.*;

@Service
@Slf4j
public class ColorService {
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private ModelMapper modelMapper;

    // Create
    public TypeResponse<ColorDTO> createColor(ColorDTO colorDto) {
        // Check if color with same name already exists
        if (colorRepository.existsByName(colorDto.getName())) {
            return ResponseHelper.badRequest(ResponseMessage.COLOR_EXISTS);
        }
        Color color = Color.builder()
                .name(colorDto.getName())
                .code(colorDto.getCode())
                .value(colorDto.getValue())
                .build();

        Color savedColor = colorRepository.save(color);
        return ResponseHelper.ok(modelMapper.map(savedColor, ColorDTO.class), ResponseMessage.CREATE_SUCCESS);
    }

    // Read All
    public TypeResponse<Page<ColorDTO>> getAllColors(String keyword, int page, int size) {
        try {
            Sort sort = Sort.by("createdAt").descending();
            PageRequest pageRequest = PageRequest.of(page, size , sort);
            Specification<Color> spec = Specification.where(null);
            if(keyword!=null && !keyword.isEmpty()) {
                spec = spec.and((root, query, cb) -> cb.or(
                        cb.like(cb.lower(root.get("name")), "%"+keyword.trim().toLowerCase()+"%", '\\'),
                        cb.like(cb.lower(root.get("code")), "%"+keyword.trim().toLowerCase()+"%", '\\')
                ));
            }
            Page<ColorDTO> colorPage = colorRepository.findAll(spec, pageRequest).map(color -> modelMapper.map(color, ColorDTO.class));
            log.info("Get color successfully: {} color", colorPage.getTotalElements());
            return ResponseHelper.ok(colorPage, ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }

    // Read One
    public TypeResponse<ColorDTO> getColorById(UUID id) {
        Optional<Color> color = colorRepository.findById(id);
        if (color.isEmpty()) {
            return ResponseHelper.notFound(ResponseMessage.COLOR_NOT_FOUND);
        }
        return ResponseHelper.ok(modelMapper.map(color.get(), ColorDTO.class), ResponseMessage.FETCH_SUCCESS);
    }

    // Update
    public TypeResponse<ColorDTO> updateColor(UUID id, ColorDTO colorDto) {
        Optional<Color> existingColor = colorRepository.findById(id);
        if (existingColor.isEmpty()) {
            return ResponseHelper.notFound(ResponseMessage.COLOR_NOT_FOUND);
        }

        Color color = existingColor.get();
        // Update name and value
        color.setName(colorDto.getName());
        color.setCode(colorDto.getCode());
        color.setValue(colorDto.getValue());

        Color updatedColor = colorRepository.save(color);
        return ResponseHelper.ok(modelMapper.map(updatedColor, ColorDTO.class), ResponseMessage.UPDATE_SUCCESS);
    }

    // Delete
    public TypeResponse<Void> deleteColor(UUID id) {
        Optional<Color> color = colorRepository.findById(id);
        if (color.isEmpty()) {
            return ResponseHelper.notFound(ResponseMessage.COLOR_NOT_FOUND);
        }

        colorRepository.delete(color.get());
        return ResponseHelper.ok(null, ResponseMessage.DELETE_SUCCESS);
    }
}
