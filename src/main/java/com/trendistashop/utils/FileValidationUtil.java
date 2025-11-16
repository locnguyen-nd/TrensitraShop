package com.trendistashop.utils;

import org.springframework.web.multipart.MultipartFile;
import java.util.Arrays;
import java.util.List;

public class FileValidationUtil {
    private static final List<String> ALLOWED_IMAGE_TYPES = List.of(
            "image/jpeg", "image/jpg", "image/png", "image/gif", "image/webp"
    );

    private static final List<String> ALLOWED_VIDEO_TYPES = List.of(
            "video/mp4", "video/mpeg", "video/quicktime", "video/x-msvideo"
    );

    private static final long MAX_IMAGE_SIZE = 10 * 1024 * 1024; // 10MB
    private static final long MAX_VIDEO_SIZE = 50 * 1024 * 1024; // 50MB

    public static void validateMediaFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File không được để trống");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.contains("/")) {
            throw new IllegalArgumentException("Không xác định được loại file");
        }

        boolean isImage = ALLOWED_IMAGE_TYPES.stream().anyMatch(contentType::startsWith);
        boolean isVideo = ALLOWED_VIDEO_TYPES.stream().anyMatch(contentType::equals);

        if (isImage && file.getSize() > MAX_IMAGE_SIZE) {
            throw new IllegalArgumentException("Ảnh không được vượt quá 10MB");
        }
        if (isVideo && file.getSize() > MAX_VIDEO_SIZE) {
            throw new IllegalArgumentException("Video không được vượt quá 50MB");
        }
        if (!isImage && !isVideo) {
            throw new IllegalArgumentException("Chỉ hỗ trợ định dạng: JPG, PNG, GIF, WebP, MP4, MOV, AVI");
        }
    }
}
