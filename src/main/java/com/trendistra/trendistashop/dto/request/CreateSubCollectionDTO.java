package com.trendistra.trendistashop.dto.request;

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
public class CreateSubCollectionDTO {
    private UUID id;
    @NotBlank(message = "SubCollection name is required")
    private String name;

    private String description;

    // Danh sách file media (ảnh, video) cho subcollection
    private List<MultipartFile> media;

    // (Tùy chọn) Danh sách ID sản phẩm để gán cho subcollection
    private List<String> productIds;
}
