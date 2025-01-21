package com.trendistra.trendistashop.services.impl.product;

import com.trendistra.trendistashop.dto.response.ProductImageDTO;
import com.trendistra.trendistashop.entities.product.Color;
import com.trendistra.trendistashop.entities.product.Product;
import com.trendistra.trendistashop.entities.product.ProductImage;
import com.trendistra.trendistashop.exceptions.ResourceNotFoundEx;
import com.trendistra.trendistashop.repositories.product.ColorRepository;
import com.trendistra.trendistashop.repositories.product.ImageRepository;
import com.trendistra.trendistashop.repositories.product.ProductRepository;
import com.trendistra.trendistashop.services.CloudinaryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ColorRepository colorRepository;
    @Transactional
    public List<ProductImage>  uploadImagesByColor(
            List<MultipartFile> files,
            Map<UUID, List<String>> colorImageMapping,
            UUID productId
    ) throws IOException {
        if(files == null || files.isEmpty() || colorImageMapping == null) {
            throw new IllegalArgumentException("Invalid input: files or color mapping is empty");
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundEx("Product not found"));
        List<CompletableFuture<ProductImage>> uploadedImages = new ArrayList<>();
        Map<String, MultipartFile> fileMap = mapFilesByName(files);
        boolean[] isThumbnailSet = {false};
        for (Map.Entry<UUID, List<String>> entry : colorImageMapping.entrySet()) {
            UUID colorId = entry.getKey();
            Color color = colorRepository.findById(colorId)
                    .orElseThrow(() -> new ResourceNotFoundEx("Color not found"));

            List<String> imageFileNames = entry.getValue();
            for (String fileName : imageFileNames) {
                MultipartFile file = findFileByName(fileMap, fileName); // Tìm file từ Map
                if (file != null && !file.isEmpty()) {
                    boolean isThumbnail = !isThumbnailSet[0];
                    uploadedImages.add(CompletableFuture.supplyAsync(() -> {
                        try {
                            ProductImage image = uploadSingleImage(file, product, color, isThumbnail);
                            if (isThumbnail) {
                                isThumbnailSet[0] = true; // Đánh dấu thumbnail đã được đặt
                            }
                            return image;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }));
                }
            }
        }

        return uploadedImages.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }
    private Map<String, MultipartFile> mapFilesByName(List<MultipartFile> files) {
        return files.stream()
                .collect(Collectors.toMap(file -> {
                    String originalFilename = file.getOriginalFilename();
                    return originalFilename != null
                            ? originalFilename.substring(0, originalFilename.lastIndexOf('.'))
                            : "";
                }, file -> file));
    }
    private MultipartFile findFileByName(Map<String, MultipartFile> fileMap, String fileName) {
        String fileNameWithoutExt = fileName.contains(".")
                ? fileName.substring(0, fileName.lastIndexOf('.'))
                : fileName;
        return fileMap.get(fileNameWithoutExt);
    }
    private ProductImage uploadSingleImage(MultipartFile file, Product product, Color color , boolean isThumbnail) throws IOException {
        // Generate unique filename based on product and color
        String filename = generateUniqueFilename(product, color, file);
        // Upload to Cloudinary
        String uploadResult = cloudinaryService.uploadFile(file, filename, "PRODUCTS");
        // Create and save image entity
        ProductImage image = ProductImage.builder()
                .product(product)
                .color(color)
                .url(uploadResult)
                .isThumbnail(isThumbnail)
                .build();
        ProductImage savedImage = imageRepository.save(image);
        return savedImage;
    }

    private String generateUniqueFilename(Product product, Color color, MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename != null
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : ".jpg";

        return String.format("%s_%s_%s",
                product.getName().replaceAll("\\s+", "_"),
                color.getName().replaceAll("\\s+", "_"),
                fileExtension
        );
    }
    public List<ProductImage> getImagesByProductAndColor(UUID productId, UUID colorId) {
        return imageRepository.findByProductIdAndColorId(productId, colorId)
                .stream()
                .collect(Collectors.toList());
    }
    public ProductImageDTO updateImage(UUID imageId, MultipartFile newFile, Integer sequence) throws IOException {
        ProductImage existingImage = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundEx("Image not found"));
        // Delete old image from Cloudinary
        cloudinaryService.deleteFile(existingImage.getUrl());
        // Generate new filename
        String filename = generateUniqueFilename(
                existingImage.getProduct(),
                existingImage.getColor(),
                newFile
        );

        // Upload new image
        String uploadResult = cloudinaryService.uploadFile(newFile, filename, "products");
        // Update image
        existingImage.setUrl(uploadResult);
        ProductImage updatedImage = imageRepository.save(existingImage);
        return convertToDto(updatedImage);
    }

    public void deleteImage(UUID imageId) {
        ProductImage image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundEx("Image not found"));

        // Delete from Cloudinary
        cloudinaryService.deleteFile(image.getUrl());
        // Delete from database
        imageRepository.delete(image);
    }

    private ProductImageDTO convertToDto(ProductImage image) {
        ProductImageDTO dto = new ProductImageDTO();
        dto.setId(image.getId());
        dto.setUrl(image.getUrl());
        dto.setIsThumbnail(image.getIsThumbnail());
        dto.setProductId(image.getProduct().getId());
        dto.setColorId(image.getColor().getId());
        dto.setIsThumbnail(image.getIsThumbnail());
        return dto;
    }
}
