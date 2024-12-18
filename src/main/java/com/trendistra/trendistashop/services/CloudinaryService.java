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
            String originalFilename = file.getOriginalFilename(); ;
            String uniqueFileName;
            if(fileName == null) {
                // Sinh tên file duy nhất
                 uniqueFileName = folder + "_" +
                        (originalFilename != null ? originalFilename.substring(0, originalFilename.lastIndexOf(".")) : ".jpg");
            } else {
                uniqueFileName = fileName.substring(0, fileName.lastIndexOf("."));
            }
            String selectFolder = folder.isEmpty() ? "root/" : (folder + "/"); // chọn folder upload
            // Log thông tin file
            logger.info("Uploading file: {}", uniqueFileName);
            // Upload với chi tiết log
            Map<?, ?> uploadResult = cloudinary.uploader()
                    .upload(file.getBytes(),
                            ObjectUtils.asMap(
                                    "folder", selectFolder,
                                    "public_id", uniqueFileName,
                                    "overwrite", true
                            )
                    );

            // Log kết quả upload
            logger.info("Upload successful. Response: {}", uploadResult);
            // Trả về URL an toàn
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            logger.error("Error uploading file to Cloudinary", e);
            throw e;
        }
    }
    public void deleteFile(String imageUrl) {
        try {
            // Extract public_id from the URL
            String publicId = extractPublicIdFromUrl(imageUrl);

            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            logger.error("Error deleting file from Cloudinary", e);
        }
    }
    private String extractPublicIdFromUrl(String url) {
        // Implement logic to extract public_id from Cloudinary URL
        // This might vary based on your Cloudinary URL format
        String[] parts = url.split("/");
        String fileName = parts[parts.length - 1];
        return fileName.substring(0, fileName.lastIndexOf("."));
    }
}