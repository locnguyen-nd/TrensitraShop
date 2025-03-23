package com.trendistra.trendistashop.controllers.admin;

import com.trendistra.trendistashop.docs.color.CreateColorDocs;
import com.trendistra.trendistashop.docs.color.DeleteColorDocs;
import com.trendistra.trendistashop.docs.color.GetAllColorDocs;
import com.trendistra.trendistashop.docs.color.GetColorDocs;
import com.trendistra.trendistashop.docs.color.UpdateColorDocs;
import com.trendistra.trendistashop.dto.response.ColorDTO;
import com.trendistra.trendistashop.dto.response.TypeResponse;
import com.trendistra.trendistashop.services.impl.product.ColorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Operation(summary = "Tạo mới màu")
    @CreateColorDocs
    @PostMapping
    public ResponseEntity<TypeResponse<ColorDTO>> createColor(@RequestBody ColorDTO colorDto) {
        TypeResponse<ColorDTO> createdColor = colorService.createColor(colorDto);
        return ResponseEntity.status(createdColor.getStatusCode()).body(createdColor);
    }

    @Operation(summary = "Lấy tất cả màu")
    @GetAllColorDocs
    @GetMapping
    public ResponseEntity<TypeResponse<List<ColorDTO>>> getAllColors() {
        TypeResponse<List<ColorDTO>> colors = colorService.getAllColors();
        return ResponseEntity.status(colors.getStatusCode()).body(colors);
    }

    @Operation(summary = "Lấy màu theo id")
    @GetColorDocs
    @GetMapping("/{id}")
    public ResponseEntity<TypeResponse<ColorDTO>> getColorById(@PathVariable UUID id) {
        TypeResponse<ColorDTO> color = colorService.getColorById(id);
        return ResponseEntity.status(color.getStatusCode()).body(color);
    }

    @Operation(summary = "Cập nhật màu")
    @UpdateColorDocs
    @PutMapping("/{id}")
    public ResponseEntity<TypeResponse<ColorDTO>> updateColor(
            @PathVariable UUID id,
            @RequestBody ColorDTO colorDto
    ) {
        TypeResponse<ColorDTO> updatedColor = colorService.updateColor(id, colorDto);
        return ResponseEntity.status(updatedColor.getStatusCode()).body(updatedColor);
    }

    @Operation(summary = "Xóa màu")
    @DeleteColorDocs
    @DeleteMapping("/{id}")
    public ResponseEntity<TypeResponse<Void>> deleteColor(@PathVariable UUID id) {
        TypeResponse<Void> deletedColor = colorService.deleteColor(id);
        return ResponseEntity.status(deletedColor.getStatusCode()).body(deletedColor);
    }
}
