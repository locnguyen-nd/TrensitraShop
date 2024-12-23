package com.trendistra.trendistashop.services.impl.product;

import com.trendistra.trendistashop.dto.response.ColorDTO;
import com.trendistra.trendistashop.entities.product.Color;
import com.trendistra.trendistashop.repositories.product.ColorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ColorService {
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private ModelMapper modelMapper;
    // Create
    public ColorDTO createColor(ColorDTO colorDto) {
        // Check if color with same name already exists
        if (colorRepository.existsByName(colorDto.getName())) {
            throw new RuntimeException("Color with this name already exists");
        }
        Color color = Color.builder()
                .name(colorDto.getName())
                .code(colorDto.getCode())
                .value(colorDto.getValue())
                .build();

        Color savedColor = colorRepository.save(color);
        return modelMapper.map(savedColor, ColorDTO.class);
    }
    // Read All
    public List<ColorDTO> getAllColors() {
        return colorRepository.findAll().stream()
                .map(color ->   modelMapper.map(color, ColorDTO.class))
                .collect(Collectors.toList());
    }
    // Read One
    public ColorDTO getColorById(UUID id) {
        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Color not found"));

        return modelMapper.map(color, ColorDTO.class);
    }
    // Update
    public ColorDTO updateColor(UUID id, ColorDTO colorDto) {
        Color existingColor = colorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Color not found"));

        // Update name and value
        existingColor.setName(colorDto.getName());
        existingColor.setCode(colorDto.getCode());
        existingColor.setValue(colorDto.getValue());

        Color updatedColor = colorRepository.save(existingColor);
        return modelMapper.map(updatedColor, ColorDTO.class);
    }
    // Delete
    public void deleteColor(UUID id) {
        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Color not found"));

        colorRepository.delete(color);
    }
}
