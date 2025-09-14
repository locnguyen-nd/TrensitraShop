package com.trendistashop.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.trendistashop.enums.CloudinaryEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CloudinaryService {
    private static final Logger logger = LoggerFactory.getLogger(CloudinaryService.class);
    private final Cloudinary cloudinary;
    private final Executor taskExecutor;
    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 1000;

    public CloudinaryService(Cloudinary cloudinary, @Qualifier("taskExecutor") Executor taskExecutor) {
        this.cloudinary = cloudinary;
        this.taskExecutor = taskExecutor;
    }

    public Executor getExecutor() {
        return taskExecutor;
    }

    @Async("taskExecutor")
    public CompletableFuture<Map<String, String>> uploadFileAsync(MultipartFile file, String fileName, CloudinaryEnum folderEnum, String subFolder) {
        return uploadFileWithRetry(file, fileName, folderEnum, subFolder, 0);
    }

    private CompletableFuture<Map<String, String>> uploadFileWithRetry(
            MultipartFile file, String fileName, CloudinaryEnum folderEnum, String subFolder, int retryCount) {

        try {
            if (file == null || file.isEmpty()) {
                return CompletableFuture.completedFuture(Map.of(
                        "url", "",
                        "public_id", "",
                        "error", "File is empty or null"
                ));
            }

            String originalFilename = Optional.ofNullable(file.getOriginalFilename()).orElse("unknown");
            String contentType = file.getContentType();
            String resourceType = (contentType != null && contentType.startsWith("video")) ? "video" : "image";

            long maxFileSize = resourceType.equals("video") ? 50L * 1024 * 1024 : 10L * 1024 * 1024;
            if (file.getSize() > maxFileSize) {
                return CompletableFuture.completedFuture(Map.of(
                        "url", "",
                        "public_id", "",
                        "error", "File size exceeds limit of " + (resourceType.equals("video") ? "50MB" : "10MB")
                ));
            }

            String uniqueFileName = generateUniqueFileName(fileName, originalFilename);
            String folderPath = buildFolderPath(folderEnum, subFolder);

            Map<String, Object> uploadOptions = new HashMap<>();
            uploadOptions.put("folder", folderPath);
            uploadOptions.put("public_id", uniqueFileName);
            uploadOptions.put("overwrite", true);
            uploadOptions.put("resource_type", resourceType);

            if ("image".equals(resourceType)) {
                uploadOptions.put("quality", "auto");
                uploadOptions.put("fetch_format", "auto");
                uploadOptions.put("transformation", "c_limit,w_1920");
            }

            long startTime = System.currentTimeMillis();
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), uploadOptions);
            logger.info("Upload successful for file: {} in {} ms",
                    uniqueFileName, System.currentTimeMillis() - startTime);

            return CompletableFuture.completedFuture(Map.of(
                    "url", uploadResult.get("secure_url").toString(),
                    "public_id", uploadResult.get("public_id").toString()
            ));
        } catch (UnknownHostException e) {
            if (retryCount < MAX_RETRIES) {
                logger.warn("Retry {}/{} for file {} due to network error",
                        retryCount + 1, MAX_RETRIES, file.getOriginalFilename());
                return CompletableFuture.supplyAsync(() -> null,
                                CompletableFuture.delayedExecutor(RETRY_DELAY_MS, TimeUnit.MILLISECONDS))
                        .thenCompose(v -> uploadFileWithRetry(file, fileName, folderEnum, subFolder, retryCount + 1));
            }
            return CompletableFuture.completedFuture(Map.of(
                    "url", "",
                    "public_id", "",
                    "error", "Failed after retries: " + e.getMessage()
            ));
        } catch (Exception e) {
            logger.error("Unexpected error uploading file {}: {}", file.getOriginalFilename(), e.getMessage(), e);
            return CompletableFuture.completedFuture(Map.of(
                    "url", "",
                    "public_id", "",
                    "error", "Unexpected error: " + e.getMessage()
            ));
        }
    }

    public Map<String, String> uploadFile(MultipartFile file, String fileName, CloudinaryEnum folderEnum, String subFolder) throws IOException {
        Map<String, String> result = uploadFileAsync(file, fileName, folderEnum, subFolder).join();
        if (result.containsKey("error")) {
            throw new IOException(result.get("error"));
        }
        return result;
    }

    @Async("taskExecutor")
    public CompletableFuture<List<Map<String, String>>> uploadFilesAsync(List<MultipartFile> files, CloudinaryEnum folderEnum, String subFolder) {
        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("No files provided for upload");
        }

        long startTime = System.currentTimeMillis();

        List<CompletableFuture<Map<String, String>>> futures = files.parallelStream()
                .map(file -> uploadFileAsync(file, null, folderEnum, subFolder))
                .collect(Collectors.toList());

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> {
                    List<Map<String, String>> results = futures.stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.toList());

                    long successCount = results.stream().filter(r -> !r.containsKey("error")).count();
                    logger.info("Uploaded {} of {} files in {} ms",
                            successCount, files.size(), System.currentTimeMillis() - startTime);

                    if (successCount < files.size()) {
                        logger.warn("Some files failed. Success: {}, Fail: {}",
                                successCount, files.size() - successCount);
                    }
                    return results;
                })
                .exceptionally(ex -> {
                    logger.error("Error uploading files: {}", ex.getMessage(), ex);
                    return Collections.emptyList();
                });
    }

    public List<Map<String, String>> uploadFiles(List<MultipartFile> files, CloudinaryEnum folderEnum, String subFolder) throws IOException {
        List<Map<String, String>> results = uploadFilesAsync(files, folderEnum, subFolder).join();
        List<String> errors = results.stream()
                .filter(result -> result.containsKey("error"))
                .map(result -> result.get("error"))
                .collect(Collectors.toList());
        if (!errors.isEmpty()) {
            throw new IOException("Failed to upload some files: " + String.join(", ", errors));
        }
        return results;
    }

    public void deleteFile(String url) {
        try {
            String publicId = extractPublicIdFromUrl(url);
            cloudinary.uploader().destroy(publicId, ObjectUtils.asMap("invalidate", true));
            logger.info("Deleted file with public_id: {}", publicId);
        } catch (Exception e) {
            logger.error("Error deleting file from Cloudinary: {}", e.getMessage());
        }
    }

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

    public void deleteFilesInFolder(CloudinaryEnum folderEnum, String subFolder) {
        String folderPath = buildFolderPath(folderEnum, subFolder);
        try {
            cloudinary.api().deleteResourcesByPrefix(folderPath, ObjectUtils.asMap("invalidate", true));
            logger.info("All files deleted in folder: {}", folderPath);
        } catch (Exception e) {
            logger.error("Error deleting files in folder '{}': {}", folderPath, e.getMessage());
        }
    }

    public void deleteFolder(CloudinaryEnum folderEnum, String subFolder) {
        String folderPath = buildFolderPath(folderEnum, subFolder);
        try {
            cloudinary.api().deleteFolder(folderPath, ObjectUtils.emptyMap());
            logger.info("Cloudinary folder deleted: {}", folderPath);
        } catch (Exception e) {
            logger.error("Error deleting folder '{}': {}", folderPath, e.getMessage());
        }
    }

    private String generateUniqueFileName(String fileName, String originalFileName) {
        String baseName = fileName != null && !fileName.trim().isEmpty()
                ? fileName.contains(".") ? fileName.substring(0, fileName.lastIndexOf(".")) : fileName
                : originalFileName != null && originalFileName.contains(".")
                ? originalFileName.substring(0, originalFileName.lastIndexOf("."))
                : UUID.randomUUID().toString();
        return baseName + "_" + UUID.randomUUID().toString();
    }

    private String buildFolderPath(CloudinaryEnum folderEnum, String subFolder) {
        String baseFolder = folderEnum != null ? folderEnum.getFolderPath() : "root";
        return subFolder != null && !subFolder.trim().isEmpty()
                ? baseFolder + "/" + subFolder
                : baseFolder;
    }

    private String extractPublicIdFromUrl(String url) {
        String[] parts = url.split("/upload/");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid Cloudinary URL: " + url);
        }
        String path = parts[1].substring(parts[1].indexOf("/") + 1);
        return path.substring(0, path.lastIndexOf("."));
    }
}