package com.trendistashop.services;

import com.trendistashop.dto.request.BannerRequestDTO;
import com.trendistashop.dto.response.BannerResponseDTO;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.enums.BannerTypeEnum;

import java.util.List;
import java.util.Map;

public interface IHomeService {
    TypeResponse<BannerResponseDTO> getBannerById(Long id);
    TypeResponse<BannerResponseDTO> createBanner(BannerRequestDTO bannerDTO);
    TypeResponse<BannerResponseDTO> updateBanner(Long id, BannerRequestDTO bannerDTO) ;
    TypeResponse<List<BannerResponseDTO>> getBannerWithTypeAndFilter(String type, String event, Boolean isActive);
    TypeResponse<Void> deleteBanner(Long id);
}
