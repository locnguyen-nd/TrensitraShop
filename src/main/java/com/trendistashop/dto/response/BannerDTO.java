package com.trendistashop.dto.response;

import com.trendistashop.enums.BannerTypeEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BannerDTO {
    private Long id;
    private String title;
    @NotBlank(message = "Event is required")
    private String event;
    @URL(message = "Image URL must be a valid URL")
    private String imageUrl;
    private String linkUrl;
    @NotBlank(message = "Type is required")
    private BannerTypeEnum type; // MAIN , DISCOUNT
    private Integer displayOrder;
    private Boolean isActive;
}
