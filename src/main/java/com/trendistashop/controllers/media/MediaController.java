package com.trendistashop.controllers.media;

import com.trendistashop.constants.ResponseMessage;
import com.trendistashop.docs.media.MediaDocs;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.enums.CloudinaryEnum;
import com.trendistashop.services.CloudinaryService;
import com.trendistashop.utils.FileValidationUtil;
import com.trendistashop.utils.ResponseHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/media")
@AllArgsConstructor
@CrossOrigin
@Tag(name = "Media API", description = "Quản lý media: upload, list, rename, move, delete")
public class MediaController {
    private final CloudinaryService cloudinaryService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @MediaDocs
    @Operation(summary = "Upload một file")
    public ResponseEntity<TypeResponse<List<Map<String, Object>>>> uploadMedia(
            @RequestParam("folder") CloudinaryEnum folder,
            @RequestParam(value = "subFolder", required = false) String subFolder,
            @RequestParam("file") MultipartFile file) {

        try {
            FileValidationUtil.validateMediaFile(file);
            Map<String, String> result = cloudinaryService.uploadFile(file, null, folder, subFolder);

            return ResponseEntity.status(201).body(ResponseHelper.created(
                    List.of(Map.of(
                            "url", result.get("url"),
                            "public_id", result.get("public_id"),
                            "isThumbnail", false
                    )),
                    "Upload thành công"
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(422).body(ResponseHelper.validationError(file.getOriginalFilename(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ResponseHelper.serverError("Upload thất bại: " + e.getMessage()));
        }
    }

    @PostMapping(value = "/upload-multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @MediaDocs
    @Operation(summary = "Upload nhiều file")
    public CompletableFuture<ResponseEntity<TypeResponse<List<Map<String, Object>>>>> uploadMultipleMedia(
            @RequestParam("folder") CloudinaryEnum folder,
            @RequestParam(value = "subFolder", required = false) String subFolder,
            @RequestParam("files") List<MultipartFile> files) {
        for (MultipartFile file : files) {
            try {
                FileValidationUtil.validateMediaFile(file);
            } catch (IllegalArgumentException e) {
                return CompletableFuture.completedFuture(
                        ResponseEntity.status(422)
                                .body(ResponseHelper.validationError(file.getOriginalFilename(), e.getMessage()))
                );
            }
        }

        return cloudinaryService.uploadFilesAsync(files, folder, subFolder)
                .thenApply(results -> {
                    List<Map<String, String>> success = results.stream()
                            .filter(r -> !r.containsKey("error"))
                            .toList();

                    List<String> errors = results.stream()
                            .filter(r -> r.containsKey("error"))
                            .map(r -> r.get("error"))
                            .toList();

                    if (!errors.isEmpty()) {
                        return ResponseEntity.status(207).body(ResponseHelper.partialSuccess(
                                success.stream()
                                        .map(r -> Map.<String, Object>of(
                                                "url", r.get("url"),
                                                "public_id", r.get("public_id"),
                                                "isThumbnail", false
                                        ))
                                        .collect(Collectors.toList()),
                                "Một số file upload thất bại: " + String.join(", ", errors)
                        ));
                    }
                    return ResponseEntity.status(201).body(ResponseHelper.created(
                            success.stream()
                                    .map(r -> Map.<String, Object>of(
                                            "url", r.get("url"),
                                            "public_id", r.get("public_id"),
                                            "isThumbnail", false
                                    ))
                                    .collect(Collectors.toList()),
                            "Upload tất cả thành công"
                    ));
                });
    }

    @GetMapping("/list")
    @Operation(summary = "Lấy danh sách media theo folder")
    public CompletableFuture<ResponseEntity<TypeResponse<Map<String, Object>>>> listMedia(
            @RequestParam("folder") CloudinaryEnum folder,
            @RequestParam(value = "subFolder", required = false) String subFolder,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "created_at") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        return cloudinaryService.listResourcesAsync(folder, subFolder, search, type, page, size, sortBy, sortDir)
                .thenApply(result -> ResponseEntity.ok(ResponseHelper.ok(result, ResponseMessage.FETCH_SUCCESS)))
                .exceptionally(ex -> {
                    Throwable cause = ex.getCause() != null ? ex.getCause() : ex;
                    return ResponseEntity.status(500)
                            .body(ResponseHelper.serverError("Lỗi lấy danh sách: " + cause.getMessage()));
                });
    }

    @PutMapping("/rename")
    @Operation(summary = "Đổi tên file")
    public ResponseEntity<TypeResponse<Map<String, Object>>> renameFile(
            @RequestParam("publicId") String publicId,
            @RequestParam("newName") String newName) {

        if (publicId == null || newName == null || newName.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(ResponseHelper.badRequest("Thiếu publicId hoặc newName"));
        }

        try {
            Map<String, Object> result = cloudinaryService.renameFile(publicId, newName.trim());
            return ResponseEntity.ok(ResponseHelper.ok(result, "Đổi tên thành công"));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(ResponseHelper.serverError("Đổi tên thất bại: " + e.getMessage()));
        }
    }

    @PutMapping("/move")
    @Operation(summary = "Di chuyển file sang folder khác")
    public ResponseEntity<TypeResponse<Map<String, Object>>> moveFile(
            @RequestParam("publicId") String publicId,
            @RequestParam("toFolder") CloudinaryEnum toFolder,
            @RequestParam(value = "toSubFolder", required = false) String toSubFolder) {

        try {
            String newUrl = cloudinaryService.moveFile(publicId, toFolder, toSubFolder);
            return ResponseEntity.ok(ResponseHelper.ok(
                    Map.of("url", newUrl, "public_id", extractPublicId(newUrl)),
                    "Di chuyển thành công"
            ));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(ResponseHelper.serverError("Di chuyển thất bại: " + e.getMessage()));
        }
    }

    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Cập nhật file (xóa cũ, upload mới)")
    public ResponseEntity<TypeResponse<Map<String, Object>>> updateFile(
            @RequestParam("oldUrl") String oldUrl,
            @RequestParam("folder") CloudinaryEnum folder,
            @RequestParam(value = "subFolder", required = false) String subFolder,
            @RequestParam("file") MultipartFile file) {

        try {
            FileValidationUtil.validateMediaFile(file);
            cloudinaryService.deleteFile(oldUrl);
            Map<String, String> result = cloudinaryService.uploadFile(file, null, folder, subFolder);

            return ResponseEntity.ok(ResponseHelper.ok(
                    Map.of(
                            "url", result.get("url"),
                            "public_id", result.get("public_id"),
                            "isThumbnail", false
                    ),
                    "Cập nhật thành công"
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(422).body(ResponseHelper.validationError(file.getOriginalFilename(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ResponseHelper.serverError("Cập nhật thất bại: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Xóa file theo URL")
    public ResponseEntity<TypeResponse<Void>> deleteFile(@RequestParam("url") String url) {
        if (url == null || url.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(ResponseHelper.badRequest("URL không được trống"));
        }
        try {
            cloudinaryService.deleteFile(url);
            return ResponseEntity.ok(ResponseHelper.ok(null, "Xóa thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ResponseHelper.serverError("Xóa thất bại: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete-folder")
    @Operation(summary = "Xóa toàn bộ file trong folder + folder")
    public ResponseEntity<TypeResponse<Void>> deleteFolder(
            @RequestParam("folder") CloudinaryEnum folder,
            @RequestParam(value = "subFolder", required = false) String subFolder) {

        try {
            cloudinaryService.deleteFilesInFolder(folder, subFolder);
            cloudinaryService.deleteFolder(folder, subFolder);
            return ResponseEntity.ok(ResponseHelper.ok(null, "Xóa folder thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ResponseHelper.serverError("Xóa folder thất bại: " + e.getMessage()));
        }
    }

    private String extractPublicId(String url) {
        return url.split("/upload/")[1].split("\\.")[0];
    }
}