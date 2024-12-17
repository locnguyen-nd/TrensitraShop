package com.trendistra.trendistashop.Util;

import org.springframework.web.multipart.MultipartFile;
import java.util.Arrays;
import java.util.List;

public class FileValidationUtil {// Allowed image mime types
    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList(
            "image/jpeg",
            "image/png",
            "image/gif",
            "image/webp"
    );
    // Maximum file size (10MB)
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    public static boolean isValidImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }
        // Check file size
        if (file.getSize() > MAX_FILE_SIZE) {
            return false;
        }
        // Check content type
        return ALLOWED_CONTENT_TYPES.contains(file.getContentType());
    }
}
