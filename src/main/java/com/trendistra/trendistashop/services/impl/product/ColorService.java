package com.trendistra.trendistashop.services.impl.product;

import com.trendistra.trendistashop.dto.response.ColorDTO;
import com.trendistra.trendistashop.entities.product.Color;
import com.trendistra.trendistashop.repositories.product.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ColorService {
    @Autowired
    private ColorRepository colorRepository;
    // Create
    public ColorDTO createColor(ColorDTO colorDto) {
        // Check if color with same name already exists
        if (colorRepository.existsByName(colorDto.getName())) {
            throw new RuntimeException("Color with this name already exists");
        }

        Color color = Color.builder()
                .name(colorDto.getName())
                .value(colorDto.getValue())
                .build();

        Color savedColor = colorRepository.save(color);

        return ColorDTO.builder()
                .id(savedColor.getId())
                .name(savedColor.getName())
                .value(savedColor.getValue())
                .build();
    }
    // Read All
    public List<ColorDTO> getAllColors() {
        return colorRepository.findAll().stream()
                .map(color -> ColorDTO.builder()
                        .id(color.getId())
                        .name(color.getName())
                        .value(color.getValue())
                        .build())
                .collect(Collectors.toList());
    }
    // Read One
    public ColorDTO getColorById(UUID id) {
        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Color not found"));

        return ColorDTO.builder()
                .id(color.getId())
                .name(color.getName())
                .value(color.getValue())
                .build();
    }
    // Update
    public ColorDTO updateColor(UUID id, ColorDTO colorDto) {
        Color existingColor = colorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Color not found"));

        // Update name and value
        existingColor.setName(colorDto.getName());
        existingColor.setValue(colorDto.getValue());

        Color updatedColor = colorRepository.save(existingColor);

        return ColorDTO.builder()
                .id(updatedColor.getId())
                .name(updatedColor.getName())
                .value(updatedColor.getValue())
                .build();
    }
    // Delete
    public void deleteColor(UUID id) {
        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Color not found"));

        colorRepository.delete(color);
    }
}
