package com.trendistashop.controllers.admin;

import com.trendistashop.docs.color.CreateColorDocs;
import com.trendistashop.docs.color.DeleteColorDocs;
import com.trendistashop.docs.color.GetAllColorDocs;
import com.trendistashop.docs.color.GetColorDocs;
import com.trendistashop.docs.color.UpdateColorDocs;
import com.trendistashop.dto.response.ColorDTO;
import com.trendistashop.dto.response.PageDTO;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.helper.PageConverter;
import com.trendistashop.services.impl.product.ColorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/colors")
@CrossOrigin
@Tag(name = "Color API", description = "API quản lý màu sắc sản phẩm")
public class ColorController {
    @Autowired
    private ColorService colorService;
    @Autowired
    private PageConverter pageConvert;

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
    public ResponseEntity<TypeResponse<PageDTO<ColorDTO>>> getAllColors(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "30") int size
    ) {
        TypeResponse<Page<ColorDTO>> colors = colorService.getAllColors(keyword, page, size);
        PageDTO<ColorDTO> pageDTO = pageConvert.toPageDTO(colors.getData());
        return ResponseEntity.status(colors.getStatusCode()).body(new TypeResponse<>(
                true,
                colors.getMessage(),
                colors.getErrors(),
                pageDTO,
                colors.getStatusCode())
        );
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
