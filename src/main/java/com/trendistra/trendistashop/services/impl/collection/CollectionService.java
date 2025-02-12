package com.trendistra.trendistashop.services.impl.collection;

import com.trendistra.trendistashop.dto.request.CreateCollectionDTO;
import com.trendistra.trendistashop.dto.request.CreateSubCollectionDTO;
import com.trendistra.trendistashop.dto.request.UpdateCollectionDTO;
import com.trendistra.trendistashop.dto.response.CollectionResponseDTO;
import com.trendistra.trendistashop.entities.collection.Collection;
import com.trendistra.trendistashop.entities.collection.Media;
import com.trendistra.trendistashop.entities.collection.SubCollection;
import com.trendistra.trendistashop.entities.product.Product;
import com.trendistra.trendistashop.enums.MediaOwnerType;
import com.trendistra.trendistashop.enums.MediaType;
import com.trendistra.trendistashop.exceptions.InvalidParameterException;
import com.trendistra.trendistashop.exceptions.ResourceNotFoundEx;
import com.trendistra.trendistashop.helper.GenerateSlug;
import com.trendistra.trendistashop.mapper.CollectionMapper;
import com.trendistra.trendistashop.repositories.collection.CollectionRepository;
import com.trendistra.trendistashop.repositories.collection.MediaRepository;
import com.trendistra.trendistashop.repositories.collection.SubCollectionRepository;
import com.trendistra.trendistashop.repositories.product.ProductRepository;
import com.trendistra.trendistashop.services.CloudinaryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CollectionService {
    private final CollectionRepository collectionRepository;
    private final MediaRepository mediaRepository;
    private final CloudinaryService cloudinaryService;
    private  GenerateSlug generateSlug;
    private final CollectionMapper collectionMapper;
    private final ProductRepository productRepository;
    private final SubCollectionRepository subCollectionRepository;
    @PersistenceContext
    private EntityManager entityManager;

    /*
    Create collection
    * */
    @Transactional
    public CollectionResponseDTO createCollection(CreateCollectionDTO dto) throws IOException {
        String slug = generateSlug.generateSlug(dto.getName());
        //Create collection
        Collection collection = Collection.builder()
                .name(dto.getName())
                .slug(slug)
                .description(dto.getDescription())
                .build();

        if (collection.getMedia() == null) {
            collection.setMedia(new ArrayList<>());
        }
        //Upload and set thumbnail
        if (dto.getThumbnail() != null  && !dto.getThumbnail().isEmpty()) {
            try {
                String originalFilename = dto.getThumbnail().getOriginalFilename();
                String extension = originalFilename != null && originalFilename.contains(".")
                        ? originalFilename.substring(originalFilename.lastIndexOf("."))
                        : ".jpg";
                String thumbnailUrl = cloudinaryService.uploadFile(
                        dto.getThumbnail(),
                        "collection_thumb_" +  slug + extension,
                        "collections/thumbnails"
                );
                collection.setThumbnail(thumbnailUrl);
            } catch (IOException e) {
                log.error("Error uploading thumbnail", e);
                throw new RuntimeException("Failed to upload thumbnail");
            }
        }
        collection = collectionRepository.save(collection);
        // Handle media uploads
        if (dto.getMedia() != null && !dto.getMedia().isEmpty()) {
            List<Media> mediaList = new ArrayList<>();
            for (int i = 0; i < dto.getMedia().size(); i++) {
                try {
                    MultipartFile mediaFile = dto.getMedia().get(i);
                    String originalFilename = mediaFile.getOriginalFilename();
                    String extension = originalFilename != null && originalFilename.contains(".")
                            ? originalFilename.substring(originalFilename.lastIndexOf("."))
                            : ".jpg";

                    String mediaUrl = cloudinaryService.uploadFile(
                            dto.getMedia().get(i),
                            "collection_media_" + slug + "_" + i + extension,
                            "collections/"+slug
                    );

                    Media media = new Media();
                    media.setUrl(mediaUrl);
                    media.setType(MediaType.IMAGE);
                    media.setCollection(collection);
                    media.setSortOrder(i);
                    media.setOwnerType(MediaOwnerType.COLLECTION);
                    mediaList.add(media);
                } catch (IOException e) {
                    log.error("Error uploading media", e);
                    // Continue with other uploads
                }
            }
            mediaRepository.saveAll(mediaList);
        }
        // Gọi method xử lý lưu SubCollections
        processSubCollections(collection, dto.getSubCollections(), slug);
        if (collection.getSubCollections() == null) {
            collection.setSubCollections(new ArrayList<>());
        }
        collection = collectionRepository.save(collection);
        return collectionMapper.mapToCollectionResponse(collection);
    }
    private void processSubCollections(Collection collection, List<CreateSubCollectionDTO> subCollectionDTOs, String slug) {
        if (subCollectionDTOs == null || subCollectionDTOs.isEmpty()) return;
        if (collection.getSubCollections() == null) {
            collection.setSubCollections(new ArrayList<>());
        }
        for (CreateSubCollectionDTO subDto : subCollectionDTOs) {
            if (subDto.getName() == null || subDto.getName().isBlank()) {
                log.warn("Skipping subCollection with empty name");
                continue;
            }

            // Tạo đối tượng SubCollection
            SubCollection subCollection = SubCollection.builder()
                    .name(subDto.getName())
                    .description(subDto.getDescription())
                    .collection(collection)
                    .build();
            subCollection = subCollectionRepository.save(subCollection);
            // Upload media cho SubCollection
            List<Media> subMediaList = new ArrayList<>();
            if (subDto.getMedia() != null && !subDto.getMedia().isEmpty()) {
                for (int j = 0; j < subDto.getMedia().size(); j++) {
                    MultipartFile subFile = subDto.getMedia().get(j);
                    if (subFile != null && !subFile.isEmpty()) {
                        try {
                            String originalFilename = subFile.getOriginalFilename();
                            String extension = (originalFilename != null && originalFilename.contains("."))
                                    ? originalFilename.substring(originalFilename.lastIndexOf("."))
                                    : ".jpg";

                            String subMediaUrl = cloudinaryService.uploadFile(
                                    subFile,
                                    "sub_collection_media_" + slug + "_" + subCollection.getName() + "_" + extension + j,
                                    "collections/" + slug
                            );
                            subMediaList.add(Media.builder()
                                    .url(subMediaUrl)
                                    .type(MediaType.IMAGE)
                                    .subCollection(subCollection)
                                    .sortOrder(j)
                                    .ownerType(MediaOwnerType.SUB_COLLECTION)
                                    .build());
                        } catch (IOException e) {
                            log.error("Error uploading subCollection media file {}: {}", j, e.getMessage());
                        }
                    }
                    if (!subMediaList.isEmpty()) {
                        mediaRepository.saveAll(subMediaList);  // Save media before attaching to subCollection
                    }
                    subCollection.setMedia(subMediaList);
                }
                // Gán sản phẩm vào SubCollection (nếu có)
                if (subDto.getProductIds() != null && !subDto.getProductIds().isEmpty()) {
                    try {
                        List<UUID> uuids = subDto.getProductIds().stream()
                                .map(UUID::fromString)
                                .collect(Collectors.toList());

                        List<Product> products = productRepository.findAllById(uuids);

                        // Kiểm tra sản phẩm hợp lệ
                        if (products.size() != uuids.size()) {
                            Set<UUID> foundIds = products.stream()
                                    .map(Product::getId)
                                    .collect(Collectors.toSet());

                            List<UUID> missingIds = uuids.stream()
                                    .filter(id -> !foundIds.contains(id))
                                    .collect(Collectors.toList());

                            log.warn("Some products not found: {}", missingIds);
                        }
                        subCollection.setProducts(products);
                    } catch (Exception e) {
                        log.error("Error processing product IDs for subCollection '{}': {}", subDto.getName(), e.getMessage());
                    }
                }
                subCollection = subCollectionRepository.save(subCollection);
                collection.getSubCollections().add(subCollection);
            }
        }
    }

    @Transactional
    public CollectionResponseDTO updateCollection(UUID id, UpdateCollectionDTO dto) throws IOException {
        Collection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEx("Collection not found"));

        String slug = generateSlug.generateSlug(dto.getName());

        //  Update basic details
        collection.setName(dto.getName());
        collection.setDescription(dto.getDescription());

        //  Update thumbnail if a new one is provided
        if (dto.getThumbnail() != null && !dto.getThumbnail().isEmpty()) {
            try {
                // Delete old thumbnail
                if (collection.getThumbnail() != null) {
                    cloudinaryService.deleteFile(collection.getThumbnail());
                }
                String originalFilename = dto.getThumbnail().getOriginalFilename();
                String extension = originalFilename != null && originalFilename.contains(".")
                        ? originalFilename.substring(originalFilename.lastIndexOf("."))
                        : ".jpg";

                String thumbnailUrl = cloudinaryService.uploadFile(
                        dto.getThumbnail(),
                        "collection_thumb_" + slug + extension,
                        "collections/thumbnails"
                );
                collection.setThumbnail(thumbnailUrl);
            } catch (IOException e) {
                log.error("Error updating thumbnail", e);
                throw new RuntimeException("Failed to update thumbnail");
            }
        }

        //  Update media: Delete old media and upload new ones
        if (dto.getNewMedia() != null && !dto.getNewMedia().isEmpty()) {
            // Delete old media
            if (collection.getMedia() != null) {
                for (Media media : collection.getMedia()) {
                    cloudinaryService.deleteFile(media.getUrl());
                }
                mediaRepository.deleteAll(collection.getMedia());
                collection.getMedia().clear();
                entityManager.flush();
            }

            // Upload new media
            List<Media> mediaList = new ArrayList<>();
            for (int i = 0; i < dto.getNewMedia().size(); i++) {
                try {
                    MultipartFile mediaFile = dto.getNewMedia().get(i);
                    String originalFilename = mediaFile.getOriginalFilename();
                    String extension = (originalFilename != null && originalFilename.contains("."))
                            ? originalFilename.substring(originalFilename.lastIndexOf("."))
                            : ".jpg";

                    String mediaUrl = cloudinaryService.uploadFile(
                            dto.getNewMedia().get(i),
                            "collection_media_" + slug + "_" + i + extension,
                            "collections/" + slug
                    );

                    Media media = Media.builder()
                            .url(mediaUrl)
                            .type(MediaType.IMAGE)
                            .collection(collection)
                            .sortOrder(i)
                            .ownerType(MediaOwnerType.COLLECTION)
                            .build();
                    mediaList.add(media);
                } catch (IOException e) {
                    log.error("Error uploading media", e);
                }
            }
            mediaRepository.saveAll(mediaList);
            collection.setMedia(mediaList);
        }
        processUpdatedSubCollections(collection, dto.getSubCollection(), slug);
        //  Save updated collection
        collection = collectionRepository.save(collection);

        return collectionMapper.mapToCollectionResponse(collection);
    }
    private void processUpdatedSubCollections(Collection collection, List<CreateSubCollectionDTO> subCollectionDTOs, String slug) {
        if (subCollectionDTOs == null) return;

        // Get existing subCollections
        Map<UUID, SubCollection> existingSubCollections = collection.getSubCollections()
                .stream().collect(Collectors.toMap(SubCollection::getId, sub -> sub));

        List<SubCollection> updatedSubCollections = new ArrayList<>();

        for (CreateSubCollectionDTO subDto : subCollectionDTOs) {
            SubCollection subCollection;

            if (subDto.getId() != null && existingSubCollections.containsKey(subDto.getId())) {
                //  Update existing subCollection
                subCollection = existingSubCollections.get(subDto.getId());
                subCollection.setName(subDto.getName());
                subCollection.setDescription(subDto.getDescription());
            } else {
                //  Create new subCollection
                subCollection = SubCollection.builder()
                        .name(subDto.getName())
                        .description(subDto.getDescription())
                        .collection(collection)
                        .build();
            }

            //  Update media for subCollection
            if (subDto.getMedia() != null && !subDto.getMedia().isEmpty()) {
                // Delete old media
                for (Media media : subCollection.getMedia()) {
                    cloudinaryService.deleteFile(media.getUrl());
                }
                mediaRepository.deleteAll(subCollection.getMedia());
                subCollection.getMedia().clear();
                entityManager.flush();

                // Upload new media
                List<Media> subMediaList = new ArrayList<>();
                    for (int j = 0; j < subDto.getMedia().size(); j++) {
                        MultipartFile subFile = subDto.getMedia().get(j);
                        if (subFile != null && !subFile.isEmpty()) {
                            try {
                                String originalFilename = subFile.getOriginalFilename();
                                String extension = (originalFilename != null && originalFilename.contains("."))
                                        ? originalFilename.substring(originalFilename.lastIndexOf("."))
                                        : ".jpg";
                                String subMediaUrl = cloudinaryService.uploadFile(
                                        subFile,
                                        "sub_collection_media_" + slug + "_" + subCollection.getName() + "_" + extension + j,
                                        "collections/" + slug
                                );

                                subMediaList.add(Media.builder()
                                        .url(subMediaUrl)
                                        .type(MediaType.IMAGE)
                                        .subCollection(subCollection)
                                        .sortOrder(j)
                                        .ownerType(MediaOwnerType.SUB_COLLECTION)
                                        .build());
                            } catch (IOException e) {
                                log.error("Error uploading subCollection media file {}: {}", j, e.getMessage());
                            }
                        }
                    }
                if (!subMediaList.isEmpty()) {
                    mediaRepository.saveAll(subMediaList);  // Save media before attaching to subCollection
                }
                    subCollection.setMedia(subMediaList);
                }


            //  Update associated products
            if (subDto.getProductIds() != null && !subDto.getProductIds().isEmpty()) {
                try {
                    List<UUID> uuids = subDto.getProductIds().stream()
                            .map(UUID::fromString)
                            .collect(Collectors.toList());
                    List<Product> products = productRepository.findAllById(uuids);
                    if (subCollection.getProducts() != null) {
                        subCollection.getProducts().clear(); // Ensure Hibernate recognizes the update
                        entityManager.flush();
                    }
                    subCollection.setProducts(products);
                } catch (Exception e) {
                    log.error("Error updating products for subCollection '{}': {}", subDto.getName(), e.getMessage());
                }
            }
            updatedSubCollections.add(subCollection);
        }

        // Remove subCollections that were deleted
        List<SubCollection> subCollectionsToRemove = collection.getSubCollections().stream()
                .filter(sub -> !updatedSubCollections.contains(sub))
                .collect(Collectors.toList());

        for (SubCollection sub : subCollectionsToRemove) {
            // Delete media from Cloudinary
            for (Media media : sub.getMedia()) {
                cloudinaryService.deleteFile(media.getUrl());
            }
            mediaRepository.deleteAll(sub.getMedia());

            subCollectionRepository.delete(sub);
        }
        collection.getSubCollections().clear();
        entityManager.flush();
        collection.setSubCollections(updatedSubCollections);
    }

    public void deleteCollection(UUID id) {
        Collection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEx("Collection not found"));
        String folderPath = "collections/" + collection.getSlug();
        try{
            // Delete thumbnail
            if (collection.getThumbnail() != null) {
                cloudinaryService.deleteFile(collection.getThumbnail());
            }
            // Delete all media
            cloudinaryService.deleteFilesInFolder(folderPath);
            cloudinaryService.deleteFolder(folderPath);
            log.info("Deleted Cloudinary folder: {}", folderPath);
        } catch (Exception e) {
            log.error("Error deleting collection media", e);
        }

        collectionRepository.delete(collection);
    }

    public CollectionResponseDTO getCollection(String slug) {
        Collection collection = collectionRepository.findBySlug(slug);
        if (collection == null) {
            throw new ResourceNotFoundEx("Collection not found");
        }
        return collectionMapper.mapToCollectionResponse(collection);
    }

    public List<CollectionResponseDTO> getAllCollections(Sort sort) {
        return collectionRepository.findAll(sort)
                .stream()
                .map(collectionMapper::mapToCollectionResponse)
                .collect(Collectors.toList());
    }


}
