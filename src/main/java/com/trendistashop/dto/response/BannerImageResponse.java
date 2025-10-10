package com.trendistashop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BannerImageResponse {
    private String imageUrl;
    private String linkUrl;
    private String content;
    private Integer order;
}
