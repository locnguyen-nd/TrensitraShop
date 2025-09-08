package com.trendistashop.controllers.media;

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
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/media")
@AllArgsConstructor
@CrossOrigin
@Tag(name = "Media", description = "API cho phép upload, update, delete media (ảnh và video) lên Cloudinary.")
public class MediaController {
    private final CloudinaryService cloudinaryService;

    /**
     * Upload a single file to Cloudinary.
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @MediaDocs
    @Operation(summary = "Upload a single media file (image or video) to Cloudinary")
    public ResponseEntity<TypeResponse<List<Map<String, Object>>>> uploadMedia(
            @RequestParam("folder") CloudinaryEnum folder,
            @RequestParam(value = "subFolder", required = false) String subFolder,
            @RequestParam("file") MultipartFile file) {

        // Validate folder
        if (folder == null) {
            return ResponseEntity.badRequest()
                    .body(ResponseHelper.badRequest("Folder parameter is required"));
        }

        // Validate file
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ResponseHelper.badRequest("No file provided for upload."));
        }

        // Validate file type and size
        if (!FileValidationUtil.isValidMedia(file)) {
            return ResponseEntity.status(422)
                    .body(ResponseHelper.validationError(file.getOriginalFilename(),
                            "Invalid file. Allowed types: images (jpeg, png, gif, webp up to 10MB) and videos (mp4, mpeg, quicktime, avi up to 50MB)."));
        }

        try {
            // Upload file
            Map<String, String> result = cloudinaryService.uploadFile(file, null, folder, subFolder);
            List<Map<String, Object>> response = List.of(Map.of(
                    "url", result.get("url"),
                    "public_id", result.get("public_id"),
                    "isThumbnail", false
            ));
            return ResponseEntity.status(201)
                    .body(ResponseHelper.created(response, "File uploaded successfully."));
        } catch (IOException e) {
            return ResponseEntity.status(500)
                    .body(ResponseHelper.serverError("Failed to upload file: " + file.getOriginalFilename()));
        }
    }

    /**
     * Upload multiple files to Cloudinary.
     */
    @PostMapping(value = "/upload-multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @MediaDocs
    @Operation(summary = "Upload multiple media files (images or videos) to Cloudinary")
    public ResponseEntity<TypeResponse<List<Map<String, String>>>> uploadMultipleMedia(
            @RequestParam("folder") CloudinaryEnum folder,
            @RequestParam(value = "subFolder", required = false) String subFolder,
            @RequestParam("files") List<MultipartFile> files) {

        // Validate folder
        if (folder == null) {
            return ResponseEntity.badRequest()
                    .body(ResponseHelper.badRequest("Folder parameter is required"));
        }

        // Validate files
        if (files == null || files.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ResponseHelper.badRequest("No files provided for upload."));
        }

        // Validate each file
        for (MultipartFile file : files) {
            if (!FileValidationUtil.isValidMedia(file)) {
                return ResponseEntity.status(422)
                        .body(ResponseHelper.validationError(file.getOriginalFilename(),
                                "Invalid file. Allowed types: images (jpeg, png, gif, webp up to 10MB) and videos (mp4, mpeg, quicktime, avi up to 50MB)."));
            }
        }

        try {
            // Upload files
            List<Map<String, String>> results = cloudinaryService.uploadFiles(files, folder, subFolder);
            List<Map<String, String>> response = results.stream().map(result -> Map.of(
                    "url", result.get("url")
            )).collect(Collectors.toList());
            return ResponseEntity.status(201)
                    .body(ResponseHelper.created(response, "Files uploaded successfully."));
        } catch (IOException e) {
            return ResponseEntity.status(500)
                    .body(ResponseHelper.serverError("Failed to upload files: " + e.getMessage()));
        }
    }

    /**
     * Delete a file from Cloudinary.
     */
    @DeleteMapping("/delete")
    @MediaDocs
    @Operation(summary = "Delete a file from Cloudinary using its URL")
    public ResponseEntity<TypeResponse<Void>> deleteFile(@RequestParam("url") String url) {
        if (url == null || url.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ResponseHelper.badRequest("File URL is required"));
        }
        try {
            cloudinaryService.deleteFile(url);
            return ResponseEntity.ok(ResponseHelper.ok(null, "File deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ResponseHelper.serverError("Failed to delete file: " + e.getMessage()));
        }
    }

    /**
     * Update a file in Cloudinary by replacing it with a new file.
     */
    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @MediaDocs
    @Operation(summary = "Update a file on Cloudinary")
    public ResponseEntity<TypeResponse<Map<String, Object>>> updateFile(
            @RequestParam("oldUrl") String oldUrl,
            @RequestParam("folder") CloudinaryEnum folder,
            @RequestParam(value = "subFolder", required = false) String subFolder,
            @RequestParam("file") MultipartFile file) {

        // Validate parameters
        if (oldUrl == null || oldUrl.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ResponseHelper.badRequest("Old file URL is required"));
        }
        if (folder == null) {
            return ResponseEntity.badRequest()
                    .body(ResponseHelper.badRequest("Folder parameter is required"));
        }
        if (!FileValidationUtil.isValidMedia(file)) {
            return ResponseEntity.status(422)
                    .body(ResponseHelper.validationError(file.getOriginalFilename(),
                            "Invalid file. Allowed types: images (jpeg, png, gif, webp up to 10MB) and videos (mp4, mpeg, quicktime, avi up to 50MB)."));
        }

        try {
            // Delete old file
            cloudinaryService.deleteFile(oldUrl);
            // Upload new file
            Map<String, String> result = cloudinaryService.uploadFile(file, null, folder, subFolder);
            Map<String, Object> response = Map.of(
                    "url", result.get("url"),
                    "public_id", result.get("public_id"),
                    "isThumbnail", false
            );
            return ResponseEntity.ok(ResponseHelper.ok(response, "File updated successfully"));
        } catch (IOException e) {
            return ResponseEntity.status(500)
                    .body(ResponseHelper.serverError("Failed to update file: " + e.getMessage()));
        }
    }

    /**
     * Delete a folder and its contents in Cloudinary.
     */
    @DeleteMapping("/delete-folder")
    @MediaDocs
    @Operation(summary = "Delete a folder and its contents in Cloudinary")
    public ResponseEntity<TypeResponse<Void>> deleteFolder(
            @RequestParam("folder") CloudinaryEnum folder,
            @RequestParam(value = "subFolder", required = false) String subFolder) {
        if (folder == null) {
            return ResponseEntity.badRequest()
                    .body(ResponseHelper.badRequest("Folder parameter is required"));
        }
        try {
            cloudinaryService.deleteFilesInFolder(folder, subFolder);
            cloudinaryService.deleteFolder(folder, subFolder);
            return ResponseEntity.ok(ResponseHelper.ok(null, "Folder deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ResponseHelper.serverError("Failed to delete folder: " + e.getMessage()));
        }
    }
}