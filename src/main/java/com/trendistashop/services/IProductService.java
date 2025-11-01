package com.trendistashop.services;

import com.trendistashop.dto.request.ProductRequestDTO;
import com.trendistashop.dto.response.ProductDTO;
import com.trendistashop.dto.response.SearchSuggestionDTO;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.entities.product.ProductVariant;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.swing.text.html.Option;
import java.util.UUID;

public interface IProductService {
    TypeResponse<Page<ProductDTO>> getAllProduct(Pageable pageable);
    TypeResponse<Page<ProductDTO>> searchWithName(String name,Pageable pageable);
    TypeResponse<SearchSuggestionDTO> getSuggestion (String keyword);
    @Transactional
    public TypeResponse<ProductDTO> createProduct(ProductRequestDTO productDto);
    public TypeResponse<ProductDTO> getProductById(UUID id);

    TypeResponse<Page<ProductDTO>> getProductByTag(String genderSlug , String tag, Pageable pageable);

    TypeResponse<ProductDTO> getProductBySlug(String slug);

    TypeResponse<Page<ProductDTO>> filterProduct(String keyword, String tag, String categorySlug, String genderSlug, String colorCode,
                                   String sizeValue, Double minPrice, Double maxPrice, Boolean status, PageRequest pageRequest);

    @Transactional
    public TypeResponse<ProductDTO> updateProduct(UUID productId, ProductRequestDTO productDto);
    public TypeResponse<Void> deleteProduct(UUID id);
    public TypeResponse<Void>  updateProductStatus(UUID id, boolean status);
    public TypeResponse<Void>  updateProductQuantities(UUID id, int availableQuantities);
    public ProductVariant productVariantById(UUID id);
}
