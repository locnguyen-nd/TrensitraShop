package com.trendistashop.controllers.user;
import com.trendistashop.docs.example.CollectionRequestExample;
import com.trendistashop.docs.example.ProductRequestExamples;
import com.trendistashop.dto.request.CollectionRequestDTO;
import com.trendistashop.dto.request.ProductRequestDTO;
import com.trendistashop.dto.response.CollectionResponseDTO;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.services.ICollectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/collections")
@Tag(name = "Collection API", description = "API quản lý bộ sưu tập")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Collection API" , description = "API quản lý bộ sưu tập")
public class CollectionController {

    private final ICollectionService collectionService;

    @PostMapping
    @Operation(summary = "Tạo bộ sưu tập mới", description = "Tạo bộ sưu tập với các chủ đề con")
    public ResponseEntity<TypeResponse<CollectionResponseDTO>> createCollection(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = CollectionRequestDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Create Collection Example",
                                            value = CollectionRequestExample.CREATE_COLLECTION
                                    )
                            }
                    )
            )
            @Valid @RequestBody CollectionRequestDTO requestDTO) {
        TypeResponse<CollectionResponseDTO> response = collectionService.createCollection(requestDTO);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{slug}")
    @Operation(summary = "Lấy bộ sưu tập theo ID", description = "Lấy chi tiết bộ sưu tập bao gồm chủ đề con")
    public ResponseEntity<TypeResponse<CollectionResponseDTO>> getCollectionBySlug(
            @Parameter(description = "ID bộ sưu tập") @PathVariable String slug) {
        TypeResponse<CollectionResponseDTO> response = collectionService.getCollectionBySlug(slug);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping
    @Operation(summary = "Lấy danh sách bộ sưu tập với filter", description = "Hỗ trợ filter và group động theo status, activeOnly, orderIndex range")
    public ResponseEntity<TypeResponse<List<CollectionResponseDTO>>> getCollectionsWithFilter(
            @Parameter(description = "Filter theo status (true/false)") @RequestParam(required = false) Boolean status,
            @Parameter(description = "Lấy collection có tên or slug theo key") @RequestParam(required = false) String keyword
            ) {
        TypeResponse<List<CollectionResponseDTO>> response = collectionService.getCollectionsWithFilter(status, keyword);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật bộ sưu tập", description = "Cập nhật thông tin bộ sưu tập và chủ đề con")
    public ResponseEntity<TypeResponse<CollectionResponseDTO>> updateCollection(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = CollectionRequestDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Update Collection Example",
                                            value = CollectionRequestExample.UPDATE_COLLECTION
                                    )
                            }
                    )
            )
            @Parameter(description = "ID collection") @PathVariable UUID id,
            @Valid @RequestBody CollectionRequestDTO requestDTO) {
        TypeResponse<CollectionResponseDTO> response = collectionService.updateCollection(id, requestDTO);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa bộ sưu tập", description = "Soft delete bộ sưu tập (set status = false)")
    public ResponseEntity<TypeResponse<Void>> deleteCollection(
            @Parameter(description = "ID bộ sưu tập") @PathVariable UUID id) {
        TypeResponse<Void> response = collectionService.deleteCollection(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}