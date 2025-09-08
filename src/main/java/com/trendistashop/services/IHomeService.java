package com.trendistashop.services;

import com.trendistashop.dto.response.BannerDTO;
import com.trendistashop.dto.response.TypeResponse;

import java.util.List;
import java.util.Map;

public interface IHomeService {
    TypeResponse<BannerDTO> createBanner(BannerDTO bannerDTO);
    TypeResponse<BannerDTO> updateBanner(Long id, BannerDTO bannerDTO) ;
    TypeResponse<Map<String, List<BannerDTO>>> getBannerWithType(String type);
    TypeResponse<Void> deleteBanner(Long id);


}
