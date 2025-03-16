package com.trendistra.trendistashop.controllers.admin;

import com.trendistra.trendistashop.docs.size.CreateSizeDocs;
import com.trendistra.trendistashop.docs.size.DeleteSizeDocs;
import com.trendistra.trendistashop.docs.size.GetAllSizeDocs;
import com.trendistra.trendistashop.docs.size.GetSizeDocs;
import com.trendistra.trendistashop.docs.size.UpdateSizeDocs;
import com.trendistra.trendistashop.docs.size.examples.SizeRequestExamples;
import com.trendistra.trendistashop.dto.response.TypeResponse;
import com.trendistra.trendistashop.entities.product.Size;
import com.trendistra.trendistashop.services.impl.product.SizeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/sizes")
@CrossOrigin
@Tag(name = "Sizes")
public class SizeController {
    @Autowired
    private SizeService sizeService;

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
    public ResponseEntity<TypeResponse<List<Size>>> getAllSizes() {
        TypeResponse<List<Size>> response = sizeService.getAllSizes();
        return ResponseEntity.status(response.getStatusCode()).body(response);
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
