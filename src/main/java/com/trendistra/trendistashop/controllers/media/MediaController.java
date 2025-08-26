package com.trendistra.trendistashop.controllers.media;

import com.trendistra.trendistashop.utils.FileValidationUtil;
import com.trendistra.trendistashop.utils.ResponseHelper;
import com.trendistra.trendistashop.docs.media.MediaDocs;
import com.trendistra.trendistashop.dto.response.TypeResponse;
import com.trendistra.trendistashop.services.CloudinaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/media")
@AllArgsConstructor
@CrossOrigin
@Tag(name = "Media", description = "API cho phép upload, update, delete media (ảnh và video) lên Cloudinary.")
public class MediaController {
    private final CloudinaryService cloudinaryService;
    /**
     * API cho phép upload media (ảnh và video)
     * Yêu cầu:
     * - Tham số "folder" chỉ định thư mục trên Cloudinary
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @MediaDocs
    @Operation(summary = "Upload media (ảnh và video) lên Cloudinary")
    public ResponseEntity<TypeResponse<List<String>>> uploadMedia(
            @RequestParam("folder") String folder,
            @RequestParam("file") MultipartFile file) {

        // Validate folder parameter
        if (folder == null || folder.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ResponseHelper.badRequest("Folder parameter is required"));
        }

        // Validate that file is present
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ResponseHelper.badRequest("No file provided for upload."));
        }

        List<String> uploadedUrls = new ArrayList<>();

        // Validate the file using FileValidationUtil (ensuring proper media type and size)
        if (!FileValidationUtil.isValidMedia(file)) {
            return ResponseEntity.status(422)
                    .body(ResponseHelper.validationError(file.getOriginalFilename(),
                            "Invalid file. Allowed types: images (jpeg, png, gif, webp up to 10MB) and videos (mp4, mpeg, quicktime, avi up to 50MB)."));
        }

        try {
            // Upload file to Cloudinary and add URL to list
            String url = cloudinaryService.uploadFile(file, null, folder);
            uploadedUrls.add(url);
        } catch (IOException e) {
            return ResponseEntity.status(500)
                    .body(ResponseHelper.serverError("Failed to upload file: " + file.getOriginalFilename()));
        }

        return ResponseEntity.status(201)
                .body(ResponseHelper.created(uploadedUrls, "File uploaded successfully."));
    }
    /**
     * API xóa ảnh dựa vào URL.
     * Yêu cầu:
     * - Tham số "imageUrl" chứa URL của ảnh cần xóa.
     */
    @DeleteMapping("/delete")
    @MediaDocs
    @Operation(summary = "Xóa ảnh dựa vào URL")
    public ResponseEntity<TypeResponse<Void>> deleteImage(@RequestParam("imageUrl") String imageUrl) {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ResponseHelper.badRequest("Image URL is required"));
        }
        try {
            cloudinaryService.deleteFile(imageUrl);
            return ResponseEntity.ok(ResponseHelper.ok(null, "Image deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ResponseHelper.serverError("Failed to delete image: " + e.getMessage()));
        }
    }
    /**
     * API update ảnh.
     * Quy trình:
     * - Xóa ảnh cũ (oldImageUrl)
     * - Validate và upload file ảnh mới với thông tin folder cho trước.
     * Yêu cầu:
     * - Tham số "oldImageUrl" để xác định ảnh cần update.
     * - Tham số "folder" chỉ định thư mục upload.
     * - Tham số "file" chứa file ảnh mới.
     */
    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @MediaDocs
    @Operation (summary = "Cập nhật ảnh trên Cloudinary")
    public ResponseEntity<TypeResponse<String>> updateImage(
            @RequestParam("oldImageUrl") String oldImageUrl,
            @RequestParam("folder") String folder,
            @RequestParam("file") MultipartFile file) {

        if (oldImageUrl == null || oldImageUrl.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ResponseHelper.badRequest("Old image URL is required"));
        }

        if (folder == null || folder.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ResponseHelper.badRequest("Folder parameter is required"));
        }
        if (!FileValidationUtil.isValidMedia(file)) {
            return ResponseEntity.status(422)
                    .body(ResponseHelper.validationError(file.getOriginalFilename(),
                            "Invalid file. Allowed types: images (jpeg, png, gif, webp up to 10MB) and videos (mp4, mpeg, quicktime, avi up to 50MB)."));
        }
        try {
            cloudinaryService.deleteFile(oldImageUrl);
            String newUrl = cloudinaryService.uploadFile(file, null, folder);
            return ResponseEntity.ok(ResponseHelper.ok(newUrl, "Image updated successfully"));
        } catch (IOException e) {
            return ResponseEntity.status(500)
                    .body(ResponseHelper.serverError("Failed to update image: " + e.getMessage()));
        }
    }

    /**
     * API xóa folder trên Cloudinary.
     * Quy trình:
     * - Xóa toàn bộ files trong folder (nếu cần)
     * - Xóa folder dựa vào folderPath.
     * Yêu cầu:
     * - Tham số "folder" chứa tên folder cần xóa.
     */
    @DeleteMapping("/delete-folder")
    @MediaDocs
    @Operation(summary = "Xóa folder trên Cloudinary")
    public ResponseEntity<TypeResponse<Void>> deleteFolder(@RequestParam("folder") String folder) {
        if (folder == null || folder.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ResponseHelper.badRequest("Folder parameter is required"));
        }
        try {
            cloudinaryService.deleteFilesInFolder(folder);
            cloudinaryService.deleteFolder(folder);
            return ResponseEntity.ok(ResponseHelper.ok(null, "Folder deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ResponseHelper.serverError("Failed to delete folder: " + e.getMessage()));
        }
    }
}
