package com.trendistra.trendistashop.controllers.user;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendistra.trendistashop.dto.request.CreateCollectionDTO;
import com.trendistra.trendistashop.dto.request.CreateSubCollectionDTO;
import com.trendistra.trendistashop.dto.request.UpdateCollectionDTO;
import com.trendistra.trendistashop.dto.response.CollectionResponseDTO;
import com.trendistra.trendistashop.dto.response.ErrorResponse;
import com.trendistra.trendistashop.exceptions.ResourceNotFoundEx;
import com.trendistra.trendistashop.services.impl.collection.CollectionService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/collections")
@RequiredArgsConstructor
@Tag(name = "Collections")
@Slf4j
public class CollectionController {
    private final CollectionService collectionService;
    private final ObjectMapper objectMapper;
    /**
     * API tạo collection mới (hỗ trợ upload media)
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createCollection(
            @Parameter(description = "Collection name", required = true)
            @RequestParam("name") String name,

            @Parameter(description = "Collection description")
            @RequestParam(value = "description", required = false) String description,

            @Parameter(description = "Collection thumbnail image")
            @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail,

            @Parameter(description = "Collection media files (images/videos)")
            @RequestParam(value = "media", required = false) List<MultipartFile> media,

            @Parameter(
                    description = "Subcollections data in JSON format",
                    example = """
                    [
                      {
                        "name": "Beach Wear",
                        "description": "Stylish beach outfits",
                        "productIds": [
                          "245f3f34-d3df-11ef-95a7-c908cce416be",
                          "245f3f34-d3df-11ef-95a7-c908cce416be",
                          "245f427c-d3df-11ef-95a7-c908cce416be"
                        ]
                      }
                    ]
                    """
            )
            @RequestParam(value = "subCollections", required = false) String subCollectionsJson,

            @Parameter(description = "Subcollections media files, one file per subCollection (order matters)")
            @RequestParam(value = "subCollectionsMedia", required = false) List<MultipartFile> subCollectionsMedia
    ) throws JsonProcessingException {

        // Convert the JSON string for subCollections into a List of DTOs
        List<CreateSubCollectionDTO> subCollections = null;
        if (subCollectionsJson != null && !subCollectionsJson.isEmpty()) {
            subCollections = objectMapper.readValue(
                    subCollectionsJson,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, CreateSubCollectionDTO.class)
            );
        }

        // If subCollectionsMedia is provided, assign each file to the corresponding subCollection.
        // (This example assumes one media file per subCollection.)
        if (subCollections != null && subCollectionsMedia != null) {
            if (subCollections.size() == subCollectionsMedia.size()) {
                for (int i = 0; i < subCollections.size(); i++) {
                    // Create a new list and add the corresponding media file
                    List<MultipartFile> mediaForSub = new ArrayList<>();
                    mediaForSub.add(subCollectionsMedia.get(i));
                    subCollections.get(i).setMedia(mediaForSub);
                }
            } else {
                log.warn("The number of subCollectionsMedia files ({}) does not match the number of subCollections ({}).",
                        subCollectionsMedia.size(), subCollections.size());
            }
        }

        // Build the CreateCollectionDTO
        CreateCollectionDTO dto = new CreateCollectionDTO(name, description, thumbnail, media, subCollections);
        log.info("Creating collection: {}", dto.getName());
        try {
            CollectionResponseDTO response = collectionService.createCollection(dto);
            log.info("Collection created successfully: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            log.error("Invalid data: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ErrorResponse(400, "Invalid data: " + e.getMessage()));
        } catch (IOException e) {
            log.error("Media upload error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Failed to upload media: " + e.getMessage()));
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal Server Error: " + e.getMessage()));
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateCollection(
            @PathVariable UUID id,

            @Parameter(description = "Collection name", required = true)
            @RequestParam("name") String name,

            @Parameter(description = "Collection description")
            @RequestParam(value = "description", required = false) String description,

            @Parameter(description = "Collection thumbnail image")
            @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail,

            @Parameter(description = "Collection media files (images/videos)")
            @RequestParam(value = "media", required = false) List<MultipartFile> media,

            @Parameter(
                    description = "Subcollections data in JSON format",
                    example = """
                [
                  {
                    "id": "existing-sub-collection-id",
                    "name": "Updated Beach Wear",
                    "description": "Stylish beach outfits",
                    "productIds": [
                      "245f3f34-d3df-11ef-95a7-c908cce416be",
                      "245f427c-d3df-11ef-95a7-c908cce416be"
                    ]
                  }
                ]
                """
            )
            @RequestParam(value = "subCollections", required = false) String subCollectionsJson,

            @Parameter(description = "Subcollections media files, one file per subCollection (order matters)")
            @RequestParam(value = "subCollectionsMedia", required = false) List<MultipartFile> subCollectionsMedia
    ) throws JsonProcessingException {

        // Convert JSON string for subCollections into a List of DTOs
        List<CreateSubCollectionDTO> subCollections = null;
        if (subCollectionsJson != null && !subCollectionsJson.isEmpty()) {
            subCollections = objectMapper.readValue(
                    subCollectionsJson,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, CreateSubCollectionDTO.class)
            );
        }

        // Assign media files to the corresponding subCollection
        if (subCollections != null && subCollectionsMedia != null) {
            if (subCollections.size() == subCollectionsMedia.size()) {
                for (int i = 0; i < subCollections.size(); i++) {
                    List<MultipartFile> mediaForSub = new ArrayList<>();
                    mediaForSub.add(subCollectionsMedia.get(i));
                    subCollections.get(i).setMedia(mediaForSub);
                }
            } else {
                log.warn("Mismatch between subCollections and subCollectionsMedia: {} vs {}", subCollections.size(), subCollectionsMedia.size());
            }
        }

        // Build DTO and call service
        UpdateCollectionDTO dto = new UpdateCollectionDTO(name, description, thumbnail, media, subCollections);
        try {
            CollectionResponseDTO updatedCollection = collectionService.updateCollection(id, dto);
            return ResponseEntity.ok(updatedCollection);
        } catch (ResourceNotFoundEx e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "Collection not found."));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Failed to update media."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal Server Error."));
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ErrorResponse> deleteCollection(@PathVariable UUID id) {
        ErrorResponse response = new ErrorResponse();
        try{
            log.info("Collection delete successfully: {}", id);
            collectionService.deleteCollection(id);
             response = new ErrorResponse(200, "Collection deleted successfully");
        } catch (EntityNotFoundException e) {
        log.error("Collection not found: {}", id);
          response = new ErrorResponse(404, "Collection not found");
        } catch (Exception e) {
            log.error("Error deleting collection: {}", e.getMessage());
            response = new ErrorResponse(500, "Internal Server Error");
        }
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{slug}")
    public ResponseEntity<CollectionResponseDTO> getCollection(@PathVariable String slug) {
        return ResponseEntity.ok(collectionService.getCollection(slug));
    }

    @GetMapping
    public ResponseEntity<List<CollectionResponseDTO>> getAllCollections(
            @RequestParam(defaultValue = "createAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);

        List<CollectionResponseDTO> collections = collectionService.getAllCollections(sort);

        return ResponseEntity.ok(collections);
    }
}
