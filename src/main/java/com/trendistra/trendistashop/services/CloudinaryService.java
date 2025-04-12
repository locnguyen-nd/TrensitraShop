package com.trendistra.trendistashop.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryService {
    private static final Logger logger = LoggerFactory.getLogger(CloudinaryService.class);
    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }


    public String uploadFile(MultipartFile file, String fileName, String folder) throws IOException {
        try {
            // Validate file
            if (file == null || file.isEmpty()) {
                throw new IllegalArgumentException("File is empty");
            }
            String uniqueFileName;

            if (fileName == null || fileName.trim().isEmpty()) {
                uniqueFileName = folder + "_" + UUID.randomUUID().toString();
            } else {
                // Nếu fileName có chứa extension thì loại bỏ phần extension cũ
                String baseName = fileName.contains(".")
                        ? fileName.substring(0, fileName.lastIndexOf("."))
                        : fileName;
                uniqueFileName = baseName + "_" + UUID.randomUUID().toString();
            }

            // Chọn folder upload
            String selectFolder = (folder == null || folder.trim().isEmpty()) ? "root/" : (folder + "/");

            logger.info("Uploading file: {}", uniqueFileName);
            String contentType = file.getContentType();
            String resourceType = "image";
            if (contentType != null && contentType.startsWith("video")) {
                resourceType = "video";
            }
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", selectFolder,
                            "public_id", uniqueFileName,
                            "overwrite", true,
                            "resource_type", resourceType
                    )
            );

            logger.info("Upload successful. Response: {}", uploadResult);

            // Trả về URL được Cloudinary tạo (sẽ có extension duy nhất)
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            logger.error("Error uploading file to Cloudinary", e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error during file upload", e);
            throw new IOException("Failed to upload file", e);
        }
    }
    public void deleteFile(String imageUrl) {
        try {
            // Extract public_id từ URL Cloudinary
            String publicId = extractPublicIdFromUrl(imageUrl);

            // Xóa ảnh từ Cloudinary
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            logger.info("Deleted image: {}", publicId);
        } catch (Exception e) {
            logger.error("Error deleting file from Cloudinary: {}", e.getMessage());
        }
    }
    private String extractPublicIdFromUrl(String url) {
        // Implement logic to extract public_id from Cloudinary URL
        // This might vary based on your Cloudinary URL format
        String[] parts = url.split("/");
        String fileName = parts[parts.length - 1];
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    /**
     * ✅ Deletes all files in a folder using a prefix match.
     */
    public void deleteFilesInFolder(String folderPath) {
        try {
            cloudinary.api().deleteResourcesByPrefix(folderPath, ObjectUtils.emptyMap());
            logger.info("All files deleted in folder: {}", folderPath);
        } catch (Exception e) {
            logger.error("Error deleting files in folder '{}': {}", folderPath, e.getMessage());
        }
    }
    /**
     * ✅ Deletes a folder in Cloudinary.
     */
    public void deleteFolder(String folderPath) {
        try {
            cloudinary.api().deleteFolder(folderPath, ObjectUtils.emptyMap());
            logger.info("Cloudinary folder deleted: {}", folderPath);
        } catch (Exception e) {
            logger.error("Error deleting folder '{}': {}", folderPath, e.getMessage());
        }
    }
}