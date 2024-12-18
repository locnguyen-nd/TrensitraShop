package com.trendistra.trendistashop.services;

import com.trendistra.trendistashop.dto.request.ProductRequestDTO;
import com.trendistra.trendistashop.dto.response.ProductDTO;
import com.trendistra.trendistashop.enums.ProductTagEnum;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface IProductService {
    public List<ProductDTO> getAllProduct();
    @Transactional
    public ProductDTO createProductWithImages(ProductRequestDTO productDto, List<MultipartFile> files) throws IOException;
    public ProductDTO getProductById(UUID id);
    public List<ProductDTO> getProductByTag(String tag);
    Page<ProductDTO> filterProduct(UUID categoryId, UUID genderId, UUID colorId ,  UUID sizeId, Double minPrice, Double maxPrice, PageRequest pageRequest);

    @Transactional
    public ProductDTO updateProduct(UUID id, ProductDTO productDto);
    public void deleteProduct(UUID id);
    public void updateProductStatus(UUID id, boolean status);
    public void updateProductQuantities(UUID id, int availableQuantities);
}
