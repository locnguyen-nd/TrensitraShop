package com.trendistra.trendistashop.dto.request;

import com.trendistra.trendistashop.dto.response.SubCollectionResponseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

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
