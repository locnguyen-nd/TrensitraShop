package com.trendistra.trendistashop.services;

import com.trendistra.trendistashop.dto.response.BannerDTO;
import com.trendistra.trendistashop.dto.response.TypeResponse;

import java.util.List;
import java.util.Map;

public interface IHomeService {
    TypeResponse<BannerDTO> createBanner(BannerDTO bannerDTO);
    TypeResponse<BannerDTO> updateBanner(Long id, BannerDTO bannerDTO) ;
    TypeResponse<Map<String, List<BannerDTO>>> getBannerWithType(String type);
    TypeResponse<Void> deleteBanner(Long id);


}
