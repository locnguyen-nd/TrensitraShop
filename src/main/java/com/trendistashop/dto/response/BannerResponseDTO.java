package com.trendistashop.dto.response;

import com.trendistashop.enums.BannerTypeEnum;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BannerResponseDTO {
    private Long id;
    private String event;
    private BannerTypeEnum type;
    private Boolean isActive;
    private List<BannerImageResponse> bannerImages;
}
