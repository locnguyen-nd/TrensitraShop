package com.trendistashop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BannerImageRequest {
    @NotBlank(message = "Image URL is required")
    private String imageUrl;
    private String linkUrl;
    private String content;
    @NotNull(message = "Display order is required")
    private Integer displayOrder;
}
