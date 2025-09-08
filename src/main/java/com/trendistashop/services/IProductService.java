package com.trendistashop.services;

import com.trendistashop.dto.request.ProductRequestDTO;
import com.trendistashop.dto.response.ProductDTO;
import com.trendistashop.dto.response.SearchSuggestionDTO;
import com.trendistashop.dto.response.TypeResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IProductService {
    TypeResponse<Page<ProductDTO>> getAllProduct(Pageable pageable);
    TypeResponse<Page<ProductDTO>> searchWithName(String name,Pageable pageable);
    TypeResponse<SearchSuggestionDTO> getSuggestion (String keyword);
    @Transactional
    public ProductDTO createProduct(ProductRequestDTO productDto);
    public TypeResponse<ProductDTO> getProductById(UUID id);

    TypeResponse<Page<ProductDTO>> getProductByTag(String genderSlug , String tag, Pageable pageable);

    TypeResponse<ProductDTO> getProductBySlug(String slug);

    TypeResponse<Page<ProductDTO>> filterProduct(String categorySlug, String genderSlug, String colorCode,
                                   String sizeValue, Double minPrice, Double maxPrice, PageRequest pageRequest);

    @Transactional
    public ProductDTO updateProduct(UUID productId, ProductRequestDTO productDto);
    public void deleteProduct(UUID id);
    public void updateProductStatus(UUID id, boolean status);
    public void updateProductQuantities(UUID id, int availableQuantities);
}
