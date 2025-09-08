package com.trendistashop.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Interface MediaUploadService cung cấp các phương thức xử lý media:
 * - uploadMedia: Upload file media lên Cloudinary và trả về URL của file.
 * - updateMedia: Cập nhật file media (xóa file cũ, upload file mới) và trả về URL của file mới.
 * - deleteMedia: Xóa file media từ Cloudinary dựa trên URL.
 *
 * Các implementation của interface này sẽ tự xử lý các thông tin chi tiết như folder upload hoặc URL cũ.
 */
public interface IMediaUploadService {
    /**
     * Upload file media lên Cloudinary và trả về URL của file.
     *
     * @param file   File media cần upload.
     * @param folder Thư mục upload trên Cloudinary.
     * @return URL của file đã upload.
     * @throws IOException Nếu có lỗi trong quá trình upload.
     */
    String uploadMedia(MultipartFile file, String fileName, String folder) throws IOException;
    String updateMedia(String oldImageUrl, String folder, String fileName,MultipartFile file) ;
    String moveFolderFile(String oldImageUrl, String newFolder, String newName);
    String renameMedia(String oldImageUrl, String subFolder, String newName);

    void deleteFile(String imageUrl);
    void deleteFilesInFolder(String folderPath);
    void deleteFolder(String folderPath);
}
