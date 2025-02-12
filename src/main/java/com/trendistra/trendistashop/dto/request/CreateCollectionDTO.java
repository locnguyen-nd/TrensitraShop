package com.trendistra.trendistashop.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCollectionDTO {
    @NotBlank(message = "Name is required")
    private String name;
    private String description;
    private MultipartFile thumbnail;
    private List<MultipartFile> media;
    @JsonProperty("subCollections")
    private List<CreateSubCollectionDTO> subCollections;
}
