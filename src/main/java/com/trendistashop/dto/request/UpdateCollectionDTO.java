package com.trendistashop.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCollectionDTO {
    @NotBlank(message = "Name is required")
    private String name;
    private String description;
    private MultipartFile thumbnail;
    private List<MultipartFile> newMedia;
    private List<CreateSubCollectionDTO> subCollection;
}
