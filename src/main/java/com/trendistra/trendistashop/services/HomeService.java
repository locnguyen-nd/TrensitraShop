package com.trendistra.trendistashop.services;

import com.trendistra.trendistashop.dto.response.BannerDTO;
import com.trendistra.trendistashop.entities.Banner;
import com.trendistra.trendistashop.enums.BannerTypeEnum;
import com.trendistra.trendistashop.repositories.BannerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HomeService {
    @Autowired
    private  ModelMapper mapper;
    @Autowired
    private BannerRepository bannerRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    public BannerDTO createBanner(BannerDTO bannerDTO, MultipartFile imageFile) throws IOException {
        Banner banner = new Banner();
        // Upload ảnh
        if(imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(imageFile, null , "BANNER/" + bannerDTO.getType().name()); // upload vao tung folder rieng
            banner.setImageUrl(imageUrl);
        } else {
            banner.setImageUrl(bannerDTO.getImageUrl()); // nếu chèn link thì lấy link
        }
        banner.setTitle(bannerDTO.getTitle());
        banner.setType(bannerDTO.getType());
        banner.setEvent(bannerDTO.getEvent());
        banner.setDisplayOrder(banner.getDisplayOrder());
        banner.setLinkUrl(bannerDTO.getLinkUrl());
        banner.setIsActive(bannerDTO.getIsActive() != null ? bannerDTO.getIsActive() : true);
        banner.setDisplayOrder(bannerDTO.getDisplayOrder());
        Banner bannerSave = bannerRepository.save(banner);
        BannerDTO bannerDTORes = mapper.map(bannerSave, BannerDTO.class);
        if (bannerDTORes == null) {
            cloudinaryService.deleteFile(banner.getImageUrl());
        }
        return bannerDTORes;
    }
    public Map<String, List<BannerDTO>> getBannerWithType(String type){
        List <Banner> banners = bannerRepository.findBannerByTypeAndIsActiveTrue(BannerTypeEnum.valueOf(type));
        List<BannerDTO> bannerDTOS = banners.stream().map(
                banner -> mapper.map(banner, BannerDTO.class)
        ).collect(Collectors.toList());
        Map<String, List<BannerDTO>> groupedByEvent = bannerDTOS.stream().collect(Collectors.groupingBy(BannerDTO::getEvent));
       return groupedByEvent;
    }
}
