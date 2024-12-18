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
        List<ProductImage> uploadedImages = new ArrayList<>();
        boolean isThumbnailSet = false;
        for (Map.Entry<UUID, List<String>> entry : colorImageMapping.entrySet()) {
            UUID colorId = entry.getKey();
            Color color = colorRepository.findById(colorId)
                    .orElseThrow(() -> new ResourceNotFoundEx("Color not found"));
            List<String> imageFileNames = entry.getValue();
            for (String fileName : imageFileNames) {
                MultipartFile file = findFileByName(files, fileName);
                if (file != null && !file.isEmpty()) {
                    boolean isThumbnail = !isThumbnailSet;
                    try{
                        ProductImage image = uploadSingleImage(file, product, color, isThumbnail);
                        uploadedImages.add(image);
                        if (isThumbnail) {
                            isThumbnailSet = true; // Đánh dấu thumbnail đã được đặt
                        }
                    } catch (Exception e){
                        System.out.println(e);
                    }
                }
            }
        }
        return  uploadedImages;
    }
    private MultipartFile findFileByName(List<MultipartFile> files, String fileName) {
        return files.stream()
                .filter(file -> {
                    String originalFilename = file.getOriginalFilename();
                    if (originalFilename == null) return false;
                    String fileNameWithoutExt = fileName.contains(".")
                            ? fileName.substring(0, fileName.lastIndexOf('.'))
                            : fileName;

                    return originalFilename.contains(fileNameWithoutExt);
                })
                .findFirst()
                .orElse(null);
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
