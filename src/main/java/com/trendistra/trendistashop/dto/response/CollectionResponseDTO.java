package com.trendistra.trendistashop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollectionResponseDTO {
    private UUID id;
    private String name;
    private String slug;
    private String description;
    private String thumbnail;
    private List<MediaDTO> media;
    private List<SubCollectionResponseDTO> subCollections;
    private Date createdAt;
    private Date updatedAt;
    private Integer totalProducts;

}
