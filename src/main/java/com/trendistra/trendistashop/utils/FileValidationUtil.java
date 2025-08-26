package com.trendistra.trendistashop.utils;

import org.springframework.web.multipart.MultipartFile;
import java.util.Arrays;
import java.util.List;

public class FileValidationUtil {// Allowed image mime types
    private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList(
            "image/jpeg",
            "image/png",
            "image/gif",
            "image/webp"
    );
    private static final List<String> ALLOWED_VIDEO_TYPES = Arrays.asList(
            "video/mp4",
            "video/mpeg",
            "video/quicktime",  // MOV
            "video/x-msvideo"   // AVI
    );
    // Maximum file size (10MB)
    private static final long MAX_IMAGE_SIZE = 10 * 1024 * 1024; // 10MB
    private static final long MAX_VIDEO_SIZE = 50 * 1024 * 1024; // 50MB

    public static boolean isValidMedia(MultipartFile file) {
        if (file == null || file.isEmpty()){
            return false;
        }

        String contentType = file.getContentType();
        if (contentType == null) {
            return false;
        }
        if (ALLOWED_IMAGE_TYPES.contains(contentType)) {
            return file.getSize() <= MAX_IMAGE_SIZE;
        }
        else if (ALLOWED_VIDEO_TYPES.contains(contentType)) {
            return file.getSize() <= MAX_VIDEO_SIZE;
        }
        // Các định dạng khác không được phép
        return false;
    }
}
