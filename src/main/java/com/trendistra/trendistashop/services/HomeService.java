package com.trendistra.trendistashop.services;

import com.trendistra.trendistashop.dto.response.BannerDTO;
import com.trendistra.trendistashop.entities.Banner;
import com.trendistra.trendistashop.enums.BannerTypeEnum;
import com.trendistra.trendistashop.exceptions.ResourceNotFoundEx;
import com.trendistra.trendistashop.repositories.BannerRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
    @Transactional
    public BannerDTO updateBanner(Long id, BannerDTO bannerDTO, MultipartFile imageFile) throws IOException {
        Banner existingBanner = bannerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEx("Banner not found"));

        // Xóa ảnh cũ nếu có ảnh mới
        if (imageFile != null && !imageFile.isEmpty()) {
            if (existingBanner.getImageUrl() != null) {
                cloudinaryService.deleteFile(extractPublicIdFromUrl(existingBanner.getImageUrl()));
            }
            String imageUrl = cloudinaryService.uploadFile(imageFile, null, "BANNER/" + bannerDTO.getType().name());
            existingBanner.setImageUrl(imageUrl);
        } else {
            existingBanner.setImageUrl(bannerDTO.getImageUrl());
        }

        // Cập nhật các trường
        existingBanner.setTitle(bannerDTO.getTitle());
        existingBanner.setType(bannerDTO.getType());
        existingBanner.setEvent(bannerDTO.getEvent());
        existingBanner.setLinkUrl(bannerDTO.getLinkUrl());
        existingBanner.setIsActive(bannerDTO.getIsActive() != null ? bannerDTO.getIsActive() : true);
        existingBanner.setDisplayOrder(bannerDTO.getDisplayOrder());

        // Lưu cập nhật
        Banner updatedBanner = bannerRepository.save(existingBanner);
        return mapper.map(updatedBanner, BannerDTO.class);
    }
    @Transactional
    public void deleteBanner(Long id) throws IOException {
        Banner existingBanner = bannerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEx("Banner not found"));

        // Xóa ảnh trên Cloudinary nếu có
        if (existingBanner.getImageUrl() != null) {
            cloudinaryService.deleteFile(extractPublicIdFromUrl(existingBanner.getImageUrl()));
        }

        // Xóa Banner khỏi database
        bannerRepository.delete(existingBanner);
    }
    private String extractPublicIdFromUrl(String url) {
        String[] parts = url.split("/");
        String filename = parts[parts.length - 1];
        return "BANNER/" + filename.split("\\.")[0];
    }

}
