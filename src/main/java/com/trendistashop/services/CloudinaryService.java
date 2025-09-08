package com.trendistashop.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.trendistashop.enums.CloudinaryEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class CloudinaryService {
    private static final Logger logger = LoggerFactory.getLogger(CloudinaryService.class);
    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    /**
     * Upload a single file to Cloudinary asynchronously.
     *
     * @param file       The file to upload.
     * @param fileName   Optional file name (without extension).
     * @param folderEnum The folder enum to specify the upload directory.
     * @param subFolder  Optional subfolder (e.g., product_id or category_id).
     * @return CompletableFuture containing Map with URL and public_id.
     * @throws IOException If upload fails.
     */
    @Async
    public CompletableFuture<Map<String, String>> uploadFileAsync(MultipartFile file, String fileName, CloudinaryEnum folderEnum, String subFolder) throws IOException {
        try {
            // Validate file
            if (file == null || file.isEmpty()) {
                throw new IllegalArgumentException("File is empty");
            }

            // Generate unique file name
            String uniqueFileName = generateUniqueFileName(fileName, file.getOriginalFilename());

            // Construct folder path
            String folderPath = buildFolderPath(folderEnum, subFolder);

            // Determine resource type
            String contentType = file.getContentType();
            String resourceType = contentType != null && contentType.startsWith("video") ? "video" : "image";

            logger.info("Uploading file: {} to folder: {}", uniqueFileName, folderPath);

            // Configure upload with optimization
            Map<String, Object> uploadOptions = new HashMap<>();
            uploadOptions.put("folder", folderPath);
            uploadOptions.put("public_id", uniqueFileName);
            uploadOptions.put("overwrite", true);
            uploadOptions.put("resource_type", resourceType);

            // Upload using InputStream
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), uploadOptions);
            logger.info("Upload successful for file: {}", uniqueFileName);

            // Return URL and public_id
            return CompletableFuture.completedFuture(Map.of(
                    "url", uploadResult.get("secure_url").toString(),
                    "public_id", uploadResult.get("public_id").toString()
            ));
        } catch (IOException e) {
            logger.error("Error uploading file to Cloudinary: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error during file upload: {}", e.getMessage(), e);
            throw new IOException("Failed to upload file", e);
        }
    }

    /**
     * Synchronous upload for backward compatibility.
     */
    public Map<String, String> uploadFile(MultipartFile file, String fileName, CloudinaryEnum folderEnum, String subFolder) throws IOException {
        return uploadFileAsync(file, fileName, folderEnum, subFolder).join();
    }

    /**
     * Upload multiple files to Cloudinary concurrently.
     *
     * @param files      List of files to upload.
     * @param folderEnum The folder enum to specify the upload directory.
     * @param subFolder  Optional subfolder.
     * @return List of maps containing URL and public_id for each uploaded file.
     * @throws IOException If any upload fails.
     */
    @Async
    public CompletableFuture<List<Map<String, String>>> uploadFilesAsync(List<MultipartFile> files, CloudinaryEnum folderEnum, String subFolder) throws IOException {
        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("No files provided for upload");
        }

        // Upload files concurrently
        List<CompletableFuture<Map<String, String>>> futures = files.stream()
                .map(file -> {
                    try {
                        return uploadFileAsync(file, null, folderEnum, subFolder);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        // Wait for all uploads to complete
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()));
    }

    /**
     * Synchronous upload multiple files for backward compatibility.
     */
    public List<Map<String, String>> uploadFiles(List<MultipartFile> files, CloudinaryEnum folderEnum, String subFolder) throws IOException {
        return uploadFilesAsync(files, folderEnum, subFolder).join();
    }

    /**
     * Delete a file from Cloudinary using its URL.
     */
    public void deleteFile(String url) {
        try {
            String publicId = extractPublicIdFromUrl(url);
            cloudinary.uploader().destroy(publicId, ObjectUtils.asMap("invalidate", true));
            logger.info("Deleted file with public_id: {}", publicId);
        } catch (Exception e) {
            logger.error("Error deleting file from Cloudinary: {}", e.getMessage());
        }
    }

    /**
     * Move a file to a new folder in Cloudinary.
     */
    public String moveFile(String url, CloudinaryEnum folderEnum, String subFolder) throws IOException {
        try {
            String oldPublicId = extractPublicIdFromUrl(url);
            String newFolderPath = buildFolderPath(folderEnum, subFolder);
            String fileName = oldPublicId.substring(oldPublicId.lastIndexOf("/") + 1);
            String newPublicId = newFolderPath + "/" + fileName;

            Map<?, ?> result = cloudinary.uploader().rename(oldPublicId, newPublicId, ObjectUtils.asMap("overwrite", true));
            logger.info("Moved file from {} to {}", oldPublicId, newPublicId);
            return result.get("secure_url").toString();
        } catch (Exception e) {
            logger.error("Error moving file from {} to {}: {}", url, folderEnum.getFolderPath(), e.getMessage());
            throw new IOException("Failed to move file", e);
        }
    }

    /**
     * Delete all files in a folder using a prefix match.
     */
    public void deleteFilesInFolder(CloudinaryEnum folderEnum, String subFolder) {
        String folderPath = buildFolderPath(folderEnum, subFolder);
        try {
            cloudinary.api().deleteResourcesByPrefix(folderPath, ObjectUtils.asMap("invalidate", true));
            logger.info("All files deleted in folder: {}", folderPath);
        } catch (Exception e) {
            logger.error("Error deleting files in folder '{}': {}", folderPath, e.getMessage());
        }
    }

    /**
     * Delete a folder in Cloudinary.
     */
    public void deleteFolder(CloudinaryEnum folderEnum, String subFolder) {
        String folderPath = buildFolderPath(folderEnum, subFolder);
        try {
            cloudinary.api().deleteFolder(folderPath, ObjectUtils.emptyMap());
            logger.info("Cloudinary folder deleted: {}", folderPath);
        } catch (Exception e) {
            logger.error("Error deleting folder '{}': {}", folderPath, e.getMessage());
        }
    }

    /**
     * Generate a unique file name for upload.
     */
    private String generateUniqueFileName(String fileName, String originalFileName) {
        String baseName = fileName != null && !fileName.trim().isEmpty()
                ? fileName.contains(".") ? fileName.substring(0, fileName.lastIndexOf(".")) : fileName
                : originalFileName != null && originalFileName.contains(".")
                ? originalFileName.substring(0, originalFileName.lastIndexOf("."))
                : UUID.randomUUID().toString();
        return baseName + "_" + UUID.randomUUID().toString();
    }

    /**
     * Build folder path from enum and optional subfolder.
     */
    private String buildFolderPath(CloudinaryEnum folderEnum, String subFolder) {
        String baseFolder = folderEnum != null ? folderEnum.getFolderPath() : "root";
        return subFolder != null && !subFolder.trim().isEmpty()
                ? baseFolder + "/" + subFolder
                : baseFolder;
    }

    /**
     * Extract public_id from Cloudinary URL.
     */
    private String extractPublicIdFromUrl(String url) {
        String[] parts = url.split("/upload/");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid Cloudinary URL: " + url);
        }
        String path = parts[1].substring(parts[1].indexOf("/") + 1);
        return path.substring(0, path.lastIndexOf("."));
    }
}