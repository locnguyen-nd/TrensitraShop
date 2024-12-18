package com.trendistra.trendistashop.dto.response;

import com.trendistra.trendistashop.enums.BannerTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BannerDTO {
    private Long id;
    private String title;
    private String imageUrl;
    private String linkUrl;
    private BannerTypeEnum type; // MAIN , DISCOUNT
    private Integer displayOrder;
    private Boolean isActive;
}
