package com.trendistashop.controllers.admin;

import com.trendistashop.dto.response.ColorDTO;
import com.trendistashop.dto.response.PageDTO;
import com.trendistashop.entities.product.Size;
import com.trendistashop.docs.size.CreateSizeDocs;
import com.trendistashop.docs.size.DeleteSizeDocs;
import com.trendistashop.docs.size.GetAllSizeDocs;
import com.trendistashop.docs.size.GetSizeDocs;
import com.trendistashop.docs.size.UpdateSizeDocs;
import com.trendistashop.docs.size.examples.SizeRequestExamples;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.helper.PageConverter;
import com.trendistashop.services.impl.product.SizeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/sizes")
@CrossOrigin
@Tag(name = "Size API" , description = "API quản lý kích thước sản phẩm")
public class SizeController {
    @Autowired
    private SizeService sizeService;
    @Autowired
    private PageConverter pageConvert;

    @Operation(summary = "Tạo size")
    @CreateSizeDocs
    @PostMapping
    public ResponseEntity<TypeResponse<Size>> createSize(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                schema = @Schema(implementation = Size.class),
                examples = @ExampleObject(value = SizeRequestExamples.CREATE_SIZE_REQUEST)
            )
        )
        @RequestBody Size size
    ) {
        TypeResponse<Size> response = sizeService.createSize(size);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @Operation(summary = "Lấy tất cả size")
    @GetAllSizeDocs
    @GetMapping
    public ResponseEntity<TypeResponse<PageDTO<Size>>> getAllSizes(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "30") int size
    ) {
        TypeResponse<Page<Size>> sizes = sizeService.getAllSizes(keyword, page, size);
        PageDTO<Size> pageDTO = pageConvert.toPageDTO(sizes.getData());
        return ResponseEntity.status(sizes.getStatusCode()).body(new TypeResponse<>(
                true,
                sizes.getMessage(),
                sizes.getErrors(),
                pageDTO,
                sizes.getStatusCode())
        );
    }

    @Operation(summary = "Lấy size theo id")
    @GetSizeDocs
    @GetMapping("/{id}")
    public ResponseEntity<TypeResponse<Size>> getSizeById(@PathVariable UUID id) {
        TypeResponse<Size> response = sizeService.getSizeById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @Operation(summary = "Cập nhật size theo id")
    @UpdateSizeDocs
    @PutMapping("/{id}")
    public ResponseEntity<TypeResponse<Size>> updateSize(@PathVariable UUID id, @RequestBody Size updatedSize) {
        TypeResponse<Size> response = sizeService.updateSize(id, updatedSize);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @Operation(summary = "Xóa size theo id")
    @DeleteSizeDocs
    @DeleteMapping("/{id}")
    public ResponseEntity<TypeResponse<Void>> deleteSize(@PathVariable UUID id) {
        TypeResponse<Void> response = sizeService.deleteSize(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
