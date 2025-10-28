package com.trendistashop.services.impl.collection;

import com.trendistashop.constants.ResponseMessage;
import com.trendistashop.dto.request.CollectionRequestDTO;
import com.trendistashop.dto.request.SubThemeRequest;
import com.trendistashop.dto.response.CollectionResponseDTO;
import com.trendistashop.dto.response.ProductDTO;
import com.trendistashop.dto.response.SubThemeResponse;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.entities.collection.Collection;
import com.trendistashop.entities.collection.SubTheme;
import com.trendistashop.entities.product.Product;
import com.trendistashop.entities.product.ProductImage;
import com.trendistashop.helper.GenerateSlug;
import com.trendistashop.repositories.collection.CollectionRepository;
import com.trendistashop.repositories.collection.SubThemeRepository;
import com.trendistashop.repositories.product.ProductRepository;
import com.trendistashop.services.CloudinaryService;
import com.trendistashop.services.ICollectionService;
import com.trendistashop.services.impl.product.ProductService;
import com.trendistashop.specifications.CollectionSpecification;
import com.trendistashop.utils.ResponseHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CollectionServiceImpl implements ICollectionService {

    private final CollectionRepository collectionRepository;
    private final ModelMapper mapper;
    @Autowired
    private CloudinaryService cloudinaryService;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final SubThemeRepository subThemeRepository;

    @Override
    @Transactional
    public TypeResponse<CollectionResponseDTO> createCollection(CollectionRequestDTO requestDTO) {
        try {
            // Check name uniqueness first
            if (collectionRepository.findByName(requestDTO.getName()) != null) {
                return ResponseHelper.badRequest(ResponseMessage.COLLECTION_NAME_EXIST);
            }

            // Map collection
            Collection collection = mapper.map(requestDTO, Collection.class);
            collection.setSlug(GenerateSlug.generateSlug(requestDTO.getName()));
            collection.setStatus(requestDTO.getStatus() != null ? requestDTO.getStatus() : true);

            // Map subThemes
            List<SubTheme> subThemes = new ArrayList<>();
            if (requestDTO.getSubThemes() != null && !requestDTO.getSubThemes().isEmpty()) {
                subThemes = requestDTO.getSubThemes().stream()
                        .map(subReq -> {
                            SubTheme subTheme = mapper.map(subReq, SubTheme.class);
                            subTheme.setCollection(collection);
                            return subTheme;
                        })
                        .collect(Collectors.toList());
            }
            collection.setSubThemes(subThemes);

            // Handle productIds for collection level BEFORE save (check validity)
            if (requestDTO.getProductIds() != null && !requestDTO.getProductIds().isEmpty()) {
                List<Product> products = productRepository.findAllById(requestDTO.getProductIds());
                if (products.size() != requestDTO.getProductIds().size()) {
                    return ResponseHelper.badRequest(ResponseMessage.PRODUCT_NOT_FOUND);
                }
                // Assign links (no save yet)
                Collection tempCollection = collection;  // Temp for lambda
                products.forEach(product -> {
                    product.setCollection(tempCollection);
                    tempCollection.getProducts().add(product);
                });
            }

            // Handle productIds for each subTheme BEFORE save
            for (int i = 0; i < subThemes.size(); i++) {
                SubTheme subTheme = subThemes.get(i);
                SubThemeRequest subReq = requestDTO.getSubThemes().get(i);
                if (subReq.getProductIds() != null && !subReq.getProductIds().isEmpty()) {
                    List<Product> subProducts = productRepository.findAllById(subReq.getProductIds());
                    if (subProducts.size() != subReq.getProductIds().size()) {
                        return ResponseHelper.badRequest(ResponseMessage.PRODUCT_NOT_FOUND);
                    }
                    subProducts.forEach(product -> product.addSubTheme(subTheme));
                }
            }

            // Now save after all validations
            Collection savedCollection = collectionRepository.save(collection);
            CollectionResponseDTO response = mapToCollectionResponse(savedCollection);
            log.info("Collection created successfully");
            return ResponseHelper.created(response, ResponseMessage.CREATE_SUCCESS);
        } catch (Exception e) {
            log.error("Error creating collection: {}", e.getMessage(), e);
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }
    @Override
    public TypeResponse<CollectionResponseDTO> getCollectionBySlug(String slug) {
        try {
            Collection collection = collectionRepository.findBySlug(slug);
            if (collection == null) {
                ResponseHelper.notFound(ResponseMessage.COLLECTION_NOT_FOUND);
            }
            CollectionResponseDTO response = mapToCollectionResponse(collection);
            log.info("Collection found successfully");
            return ResponseHelper.ok(response, ResponseMessage.FETCH_SUCCESS);
        }  catch (Exception e) {
            log.error("Error getting collection by slug: {}", e.getMessage(), e);
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }

    @Override
    public TypeResponse<Page<CollectionResponseDTO>> getCollectionsWithFilter(Boolean status, String keyword, PageRequest pageRequest) {
        try{
            Specification <Collection> spec = CollectionSpecification.withFilters(status, keyword);
            Page<Collection> collections = collectionRepository.findAll(spec, pageRequest);
            Page<CollectionResponseDTO> responses = collections.map(this::mapToCollectionResponse);
            log.info("Collections found successfully");
            return ResponseHelper.ok(responses, ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            log.error("Error getting collections with filter: {}", e.getMessage(), e);
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }

    @Override
    @Transactional
    public TypeResponse<CollectionResponseDTO> updateCollection(UUID id, CollectionRequestDTO requestDTO) {
        try {
            Optional<Collection> optionalCollection = collectionRepository.findById(id);
            if (optionalCollection.isEmpty()) {
                return ResponseHelper.notFound(ResponseMessage.COLLECTION_NOT_FOUND);
            }
            Collection existingCollection = optionalCollection.get();
            if (requestDTO.getName() != null) {
                if (!existingCollection.getName().equals(requestDTO.getName())) {
                    if (collectionRepository.findByName(requestDTO.getName()) != null) {
                        return ResponseHelper.badRequest(ResponseMessage.COLLECTION_NAME_EXIST);
                    }
                }
                existingCollection.setName(requestDTO.getName());
                existingCollection.setSlug(GenerateSlug.generateSlug(requestDTO.getName()));
            }
            if (requestDTO.getDescription() != null) {
                existingCollection.setDescription(requestDTO.getDescription());
            }
            if (requestDTO.getStatus() != null) {
                existingCollection.setStatus(requestDTO.getStatus());
            }
            if (requestDTO.getOrderIndex() != null) {
                existingCollection.setOrderIndex(requestDTO.getOrderIndex());
            }
            if (requestDTO.getThumbnail() != null && !requestDTO.getThumbnail().equals(existingCollection.getThumbnail())) {
                if (existingCollection.getThumbnail() != null) {
                    cloudinaryService.deleteFile(existingCollection.getThumbnail());
                }
                existingCollection.setThumbnail(requestDTO.getThumbnail());
            }
            if (requestDTO.getBannerUrl() != null && !requestDTO.getBannerUrl().equals(existingCollection.getBannerUrl())) {
                if (existingCollection.getBannerUrl() != null) {
                    cloudinaryService.deleteFile(existingCollection.getBannerUrl());
                }
                existingCollection.setBannerUrl(requestDTO.getBannerUrl());
            }
            if (requestDTO.getSubThemes() != null) {
                List<SubTheme> currentSubThemes = existingCollection.getSubThemes();
                for (SubTheme oldSubTheme : new ArrayList<>(currentSubThemes)) {
                    if (oldSubTheme.getImageUrl() != null) {
                        cloudinaryService.deleteFile(oldSubTheme.getImageUrl());
                    }
                    List<Product> currentSubProducts = new ArrayList<>(
                            Optional.ofNullable(oldSubTheme.getProducts()).orElse(Collections.emptyList())
                    );
                    for (Product oldProduct : currentSubProducts) {
                        oldProduct.removeSubTheme(oldSubTheme);
                    }
                    oldSubTheme.setCollection(null);
                    currentSubThemes.remove(oldSubTheme);
                }
                List<SubTheme> newSubThemes = requestDTO.getSubThemes().stream()
                        .map(subReq -> {
                            SubTheme subTheme = SubTheme.builder()
                                    .name(subReq.getName())
                                    .description(subReq.getDescription())
                                    .imageUrl(subReq.getImageUrl())
                                    .priority(subReq.getPriority() != null ? subReq.getPriority() : 0)
                                    .collection(existingCollection)
                                    .build();
                            return subTheme;
                        })
                        .collect(Collectors.toList());
                currentSubThemes.addAll(newSubThemes);
            }
            if (requestDTO.getProductIds() != null) {
                Set<UUID> newProductIds = new HashSet<>(requestDTO.getProductIds());
                List<Product> currentProducts = new ArrayList<>(
                        Optional.ofNullable(existingCollection.getProducts()).orElse(Collections.emptyList())
                );
                for (Product oldProduct : currentProducts) {
                    if (!newProductIds.contains(oldProduct.getId())) {
                        oldProduct.setCollection(null);
                        existingCollection.getProducts().remove(oldProduct);
                    }
                }
                List<UUID> toAddIds = newProductIds.stream()
                        .filter(newId ->
                                Optional.ofNullable(existingCollection.getProducts())
                                        .orElse(Collections.emptyList())
                                        .stream().noneMatch(p -> p.getId().equals(newId))
                        )
                        .collect(Collectors.toList());
                if (!toAddIds.isEmpty()) {
                    List<Product> newProducts = productRepository.findAllById(toAddIds);
                    if (newProducts.size() != toAddIds.size()) {
                        return ResponseHelper.badRequest(ResponseMessage.PRODUCT_NOT_FOUND);
                    }
                    newProducts.forEach(product -> {
                        product.setCollection(existingCollection);
                        existingCollection.getProducts().add(product);
                    });
                }
            } else {
                List<Product> currentProducts = new ArrayList<>(
                        Optional.ofNullable(existingCollection.getProducts()).orElse(Collections.emptyList())
                );
                for (Product oldProduct : currentProducts) {
                    oldProduct.setCollection(null);
                    existingCollection.getProducts().remove(oldProduct);
                }
            }
            if (requestDTO.getSubThemes() != null) {
                for (int i = 0; i < requestDTO.getSubThemes().size(); i++) {
                    SubTheme subTheme = existingCollection.getSubThemes().get(i);
                    SubThemeRequest subReq = requestDTO.getSubThemes().get(i);
                    if (subReq.getProductIds() != null) {
                        Set<UUID> newSubProductIds = new HashSet<>(subReq.getProductIds());
                        List<Product> currentSubProducts = new ArrayList<>(
                                Optional.ofNullable(subTheme.getProducts()).orElse(Collections.emptyList())
                        );
                        for (Product oldSubProduct : currentSubProducts) {
                            if (!newSubProductIds.contains(oldSubProduct.getId())) {
                                oldSubProduct.removeSubTheme(subTheme);
                            }
                        }
                        List<UUID> toAddSubIds = newSubProductIds.stream()
                                .filter(newId ->
                                        Optional.ofNullable(subTheme.getProducts())
                                                .orElse(Collections.emptyList())
                                                .stream().noneMatch(p -> p.getId().equals(newId))
                                )
                                .collect(Collectors.toList());
                        if (!toAddSubIds.isEmpty()) {
                            List<Product> newSubProducts = productRepository.findAllById(toAddSubIds);
                            if (newSubProducts.size() != toAddSubIds.size()) {
                                return ResponseHelper.badRequest(ResponseMessage.PRODUCT_NOT_FOUND);
                            }
                            newSubProducts.forEach(product -> product.addSubTheme(subTheme));
                        }
                        subThemeRepository.save(subTheme);
                    } else {
                        List<Product> currentSubProducts = new ArrayList<>(
                                Optional.ofNullable(subTheme.getProducts()).orElse(Collections.emptyList())
                        );
                        for (Product oldSubProduct : currentSubProducts) {
                            oldSubProduct.removeSubTheme(subTheme);
                        }
                        subThemeRepository.save(subTheme);
                    }
                }
            }

            Collection updatedCollection = collectionRepository.save(existingCollection);
            CollectionResponseDTO response = mapToCollectionResponse(updatedCollection);
            log.info("Collection updated successfully");
            return ResponseHelper.ok(response, ResponseMessage.UPDATE_SUCCESS);
        } catch (Exception e) {
            log.error("Error updating collection {}: {}", id, e.getMessage(), e);
            return ResponseHelper.serverError(ResponseMessage.UPDATE_FAILED);
        }
    }
    @Override
    @Transactional
    public TypeResponse<Void> deleteCollection(UUID id) {
        try {
            Optional<Collection> optionalCollection = collectionRepository.findById(id);
            if (optionalCollection.isEmpty()) {
                return ResponseHelper.notFound(ResponseMessage.COLLECTION_NOT_FOUND);
            }
            Collection existingCollection = optionalCollection.get();

            if (existingCollection.getThumbnail() != null) {
                cloudinaryService.deleteFile(existingCollection.getThumbnail());
            }
            if (existingCollection.getBannerUrl() != null) {
                cloudinaryService.deleteFile(existingCollection.getBannerUrl());
            }

            List<SubTheme> currentSubThemes = new ArrayList<>(
                    Optional.ofNullable(existingCollection.getSubThemes()).orElse(Collections.emptyList())
            );
            for (SubTheme subTheme : currentSubThemes) {
                if (subTheme.getImageUrl() != null) {
                    cloudinaryService.deleteFile(subTheme.getImageUrl());
                }
                List<Product> currentSubProducts = new ArrayList<>(
                        Optional.ofNullable(subTheme.getProducts()).orElse(Collections.emptyList())
                );
                for (Product oldProduct : currentSubProducts) {
                    oldProduct.removeSubTheme(subTheme);
                }
            }

            List<Product> currentProducts = new ArrayList<>(
                    Optional.ofNullable(existingCollection.getProducts()).orElse(Collections.emptyList())
            );
            for (Product product : currentProducts) {
                product.setCollection(null);
            }

            existingCollection.getProducts().clear();
            collectionRepository.delete(existingCollection);
            log.info("Collection deleted successfully");
            return ResponseHelper.ok(null, ResponseMessage.DELETE_SUCCESS);
        } catch (Exception e) {
            log.error("Error deleting collection {}: {}", id, e.getMessage(), e);
            return ResponseHelper.serverError(ResponseMessage.DELETE_FAILED);
        }
    }
    private CollectionResponseDTO mapToCollectionResponse(Collection collection) {
        CollectionResponseDTO response = mapper.map(collection, CollectionResponseDTO.class);
        if (collection.getProducts() != null && !collection.getProducts().isEmpty()) {
            List<ProductDTO> productDtos = collection.getProducts().stream()
                    .map(productService::mapToProductDto)
                    .collect(Collectors.toList());
            response.setProducts(productDtos);
        }
        if (collection.getSubThemes() != null) {
            List<SubThemeResponse> subThemeResponses = collection.getSubThemes().stream()
                    .map(sub -> {
                        SubThemeResponse subResponse = mapper.map(sub, SubThemeResponse.class);
                        if (sub.getProducts() != null && !sub.getProducts().isEmpty()) {
                            List<ProductDTO> subProductDtos = sub.getProducts().stream()
                                    .map(productService::mapToProductDto)
                                    .collect(Collectors.toList());
                            subResponse.setProducts(subProductDtos);
                        }
                        return subResponse;
                    })
                    .collect(Collectors.toList());
            response.setSubThemes(subThemeResponses);
        }
        return response;
    }
}