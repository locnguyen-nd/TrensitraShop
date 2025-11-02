package com.trendistashop.controllers.admin;

import com.trendistashop.docs.category.UpdateCategoryDocs;
import com.trendistashop.docs.example.BannerRequestExamples;
import com.trendistashop.dto.request.BannerRequestDTO;
import com.trendistashop.dto.response.BannerResponseDTO;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.enums.BannerTypeEnum;
import com.trendistashop.services.IHomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/banner")
@CrossOrigin
@Tag(name = "Banner API", description = "API quản lý banner")
public class BannerController {
    @Autowired
    private IHomeService homeService;

    @GetMapping
    @Operation(summary = "Lấy danh sách banner theo loại với filter", description = "Lấy danh sách banner theo loại, hỗ trợ filter theo event và isActive, nhóm theo event.")
    public ResponseEntity<TypeResponse<List<BannerResponseDTO>>> getBannerWithTypeAndFilter(
            @RequestParam(required = false) BannerTypeEnum type,
            @RequestParam(required = false) String event,
            @RequestParam(required = false, defaultValue = "true") Boolean isActive) {
        String bannerType = (type != null) ? type.name() : null;
        TypeResponse< List<BannerResponseDTO>> group = homeService.getBannerWithTypeAndFilter(bannerType, event, isActive);
        return ResponseEntity.status(group.getStatusCode()).body(group);
    }

    @PostMapping("/create")
    @Operation(summary = "Tạo banner", description = "Tạo banner bao gồm ảnh và thông tin khác.")
    public ResponseEntity<TypeResponse<BannerResponseDTO>> createBanner(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = BannerRequestDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Create Banner Example",
                                            value = BannerRequestExamples.CREATE_BANNER_REQUEST
                                    )
                            }
                    )
            )
            @RequestBody @Valid BannerRequestDTO bannerDTO
    ) {
        TypeResponse<BannerResponseDTO> response = homeService.createBanner(bannerDTO);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Lấy banner theo ID", description = "Lấy chi tiết banner theo ID.")
    public ResponseEntity<TypeResponse<BannerResponseDTO>> getBannerById(@PathVariable Long id) {
        TypeResponse<BannerResponseDTO> response = homeService.getBannerById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @PutMapping(value = "/update/{id}")
    @UpdateCategoryDocs
    @Operation(summary = "Cập nhật banner", description = "Cập nhật banner bao gồm ảnh và thông tin khác.")
    public ResponseEntity<TypeResponse<BannerResponseDTO>> updateBanner(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = BannerRequestDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Update Banner Example",
                                            value = BannerRequestExamples.UPDATE_BANNER_REQUEST
                                    )
                            }
                    )
            )
            @PathVariable Long id,
            @RequestBody BannerRequestDTO bannerDTO) {
        TypeResponse<BannerResponseDTO> response = homeService.updateBanner(id, bannerDTO);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Xóa banner", description = "Xóa banner theo ID.")
    public ResponseEntity<TypeResponse<Void>> deleteBanner(@PathVariable Long id) {
        TypeResponse<Void> response = homeService.deleteBanner(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @PutMapping(value = "/toggle-status/{id}")
    @Operation(summary = "Chuyển đổi trạng thái hiển thị của banner", description = "Chuyển đổi trạng thái hoạt động (active/inactive) của banner theo ID.")
    public ResponseEntity<TypeResponse<BannerResponseDTO>> toggleBannerStatus(
            @PathVariable Long id,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) BannerTypeEnum bannerTypeEnum) {
        TypeResponse<BannerResponseDTO> response = homeService.setDisplay(id, isActive, bannerTypeEnum);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
