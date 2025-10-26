package com.trendistashop.services;

import com.trendistashop.dto.request.CollectionRequestDTO;
import com.trendistashop.dto.response.CollectionResponseDTO;
import com.trendistashop.dto.response.TypeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ICollectionService {
    TypeResponse<CollectionResponseDTO> createCollection(CollectionRequestDTO requestDTO);
    TypeResponse<CollectionResponseDTO> getCollectionBySlug(String slug);
    TypeResponse<Page<CollectionResponseDTO>> getCollectionsWithFilter(Boolean status, String keyword, PageRequest pageRequest);
    TypeResponse<CollectionResponseDTO> updateCollection(UUID id, CollectionRequestDTO requestDTO);
    TypeResponse<Void> deleteCollection(UUID id);
}
