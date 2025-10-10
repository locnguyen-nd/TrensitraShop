package com.trendistashop.services;

import com.trendistashop.dto.request.CollectionRequestDTO;
import com.trendistashop.dto.response.CollectionResponseDTO;
import com.trendistashop.dto.response.TypeResponse;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ICollectionService {
    TypeResponse<CollectionResponseDTO> createCollection(CollectionRequestDTO requestDTO);
    TypeResponse<CollectionResponseDTO> getCollectionBySlug(String slug);
    TypeResponse<List<CollectionResponseDTO>> getCollectionsWithFilter(Boolean status, String keyword);
    TypeResponse<CollectionResponseDTO> updateCollection(UUID id, CollectionRequestDTO requestDTO);
    TypeResponse<Void> deleteCollection(UUID id);
}
