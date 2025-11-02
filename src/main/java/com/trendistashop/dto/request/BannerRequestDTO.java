package com.trendistashop.dto.request;

import com.trendistashop.enums.BannerTypeEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BannerRequestDTO {

    @NotBlank(message = "Event is required")
    private String event;

    @NotNull(message = "Type is required")
    private BannerTypeEnum type;
    private Boolean isActive = false; // Default to false
    @NotNull(message = "At least one image required")
    @Size(min = 1, message = "At least one image required")
    private List<@Valid BannerImageRequest> bannerImages;
}
