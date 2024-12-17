package com.trendistra.trendistashop.controllers.admin;

import com.trendistra.trendistashop.dto.response.ColorDTO;
import com.trendistra.trendistashop.services.impl.product.ColorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/colors")
@CrossOrigin
@Tag(name = "Colors")
public class ColorController {
    @Autowired
    private ColorService colorService;
    // Create
    @PostMapping
    public ResponseEntity<ColorDTO> createColor(@RequestBody ColorDTO colorDto) {
        ColorDTO createdColor = colorService.createColor(colorDto);
        return new ResponseEntity<>(createdColor, HttpStatus.CREATED);
    }

    // Read All
    @GetMapping
    public ResponseEntity<List<ColorDTO>> getAllColors() {
        List<ColorDTO> colors = colorService.getAllColors();
        return ResponseEntity.ok(colors);
    }

    // Read One
    @GetMapping("/{id}")
    public ResponseEntity<ColorDTO> getColorById(@PathVariable UUID id) {
        ColorDTO color = colorService.getColorById(id);
        return ResponseEntity.ok(color);
    }
    // Update
    @PutMapping("/{id}")
    public ResponseEntity<ColorDTO> updateColor(
            @PathVariable UUID id,
            @RequestBody ColorDTO colorDto
    ) {
        ColorDTO updatedColor = colorService.updateColor(id, colorDto);
        return ResponseEntity.ok(updatedColor);
    }
    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColor(@PathVariable UUID id) {
        colorService.deleteColor(id);
        return ResponseEntity.noContent().build();
    }
}
