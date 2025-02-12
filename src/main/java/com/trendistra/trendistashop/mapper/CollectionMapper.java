package com.trendistra.trendistashop.mapper;

import com.trendistra.trendistashop.dto.response.CollectionResponseDTO;
import com.trendistra.trendistashop.dto.response.MediaDTO;
import com.trendistra.trendistashop.dto.response.SubCollectionResponseDTO;
import com.trendistra.trendistashop.entities.collection.Collection;
import com.trendistra.trendistashop.entities.collection.Media;
import com.trendistra.trendistashop.entities.collection.SubCollection;
import com.trendistra.trendistashop.services.impl.product.ProductService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CollectionMapper {
    private ProductService productService;

    public CollectionMapper(ProductService productService) {
        this.productService = productService;
    }

    public CollectionResponseDTO mapToCollectionResponse(Collection collection) {
        List<SubCollectionResponseDTO> subCollections = collection.getSubCollections().stream()
                .map(this::mapToSubCollectionDTO)
                .collect(Collectors.toList());
        // Tính tổng số sản phẩm trong collection
        int totalProducts = subCollections.stream()
                .mapToInt(sub -> sub.getProducts().size())
                .sum();

        return CollectionResponseDTO.builder()
                .id(collection.getId())
                .name(collection.getName())
                .slug(collection.getSlug())
                .description(collection.getDescription())
                .thumbnail(collection.getThumbnail())
                .media(collection.getMedia().stream()
                        .map(this::mapToMediaDTO)
                        .collect(Collectors.toList()))
                .subCollections(subCollections)
                .totalProducts(totalProducts)
                .createdAt(collection.getCreateAt())
                .updatedAt(collection.getUpdateAt())
                .build();
    }
    public SubCollectionResponseDTO mapToSubCollectionDTO(SubCollection subCollection) {
        return SubCollectionResponseDTO.builder()
                .id(subCollection.getId())
                .name(subCollection.getName())
                .description(subCollection.getDescription())
                .media(subCollection.getMedia().stream()
                        .map(this::mapToMediaDTO)
                        .collect(Collectors.toList()))
                .products(subCollection.getProducts().stream()
                        .map(productService::mapToProductDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public MediaDTO mapToMediaDTO(Media media) {
        return MediaDTO.builder()
                .id(media.getId())
                .title(media.getTitle())
                .url(media.getUrl())
                .type(media.getType())
                .alt(media.getAlt())
                .build();
    }

}
