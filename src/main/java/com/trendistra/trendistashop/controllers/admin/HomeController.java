package com.trendistra.trendistashop.controllers.admin;

import com.trendistra.trendistashop.docs.category.UpdateCategoryDocs;
import com.trendistra.trendistashop.docs.home.examples.BannerRequestExamples;
import com.trendistra.trendistashop.dto.response.BannerDTO;
import com.trendistra.trendistashop.dto.response.TypeResponse;
import com.trendistra.trendistashop.services.IHomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/banner")
@CrossOrigin
@Tag(name = "HomePage")
public class HomeController {
        @Autowired
        private IHomeService homeService;

        @GetMapping("/get-type-group-event/{type}")
        @Operation(summary = "Lấy danh sách banner theo loại", description = "Lấy danh sách banner theo loại nhóm theo event.")
        public ResponseEntity<TypeResponse<Map<String, List<BannerDTO>>>> getBannerWithType(
                        @PathVariable String type) {
                TypeResponse<Map<String, List<BannerDTO>>> group = homeService.getBannerWithType(type);
                return ResponseEntity.status(group.getStatusCode()).body(group);
        }

        @PostMapping("/create")
        @com.trendistra.trendistashop.docs.home.CreateBannerDocs
        @Operation(summary = "Tạo banner", description = "Tạo banner bao gồm ảnh và thông tin khác.")
        public ResponseEntity<TypeResponse<BannerDTO>> createBanner(
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(schema = @Schema(implementation = BannerDTO.class), examples = @ExampleObject(value = BannerRequestExamples.CREATE_BANNER_REQUEST))) @RequestBody BannerDTO bannerDTO) {
                TypeResponse<BannerDTO> response = homeService.createBanner(bannerDTO);
                return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        @PutMapping(value = "/update/{id}")
        @UpdateCategoryDocs
        @Operation(summary = "Cập nhật banner", description = "Cập nhật banner bao gồm ảnh và thông tin khác.")
        public ResponseEntity<TypeResponse<BannerDTO>> updateBanner(
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(schema = @Schema(implementation = BannerDTO.class), examples = @ExampleObject(value = BannerRequestExamples.UPDATE_BANNER_REQUEST))) @PathVariable Long id,
                        @RequestBody BannerDTO bannerDTO) {
                TypeResponse<BannerDTO> response = homeService.updateBanner(id, bannerDTO);
                return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        @DeleteMapping("/delete/{id}")
        @Operation(summary = "Xóa banner", description = "Xóa banner theo ID.")
        public ResponseEntity<TypeResponse<Void>> deleteBanner(@PathVariable Long id) {
                TypeResponse<Void> response = homeService.deleteBanner(id);
                return ResponseEntity.status(response.getStatusCode()).body(response);
        }
}
