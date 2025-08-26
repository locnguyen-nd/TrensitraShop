package com.trendistra.trendistashop.services.impl.product;

import com.trendistra.trendistashop.utils.ResponseHelper;
import com.trendistra.trendistashop.constants.ResponseMessage;
import com.trendistra.trendistashop.dto.response.ColorDTO;
import com.trendistra.trendistashop.dto.response.TypeResponse;
import com.trendistra.trendistashop.entities.product.Color;
import com.trendistra.trendistashop.repositories.product.ColorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
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
    public TypeResponse<List<ColorDTO>> getAllColors() {
        List<ColorDTO> colors = colorRepository.findAll().stream()
                .map(color -> modelMapper.map(color, ColorDTO.class))
                .collect(Collectors.toList());
        return ResponseHelper.ok(colors, ResponseMessage.FETCH_SUCCESS);
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
