package com.trendistra.trendistashop.services;

import com.trendistra.trendistashop.Util.ResponseHelper;
import com.trendistra.trendistashop.dto.response.BannerDTO;
import com.trendistra.trendistashop.dto.response.TypeResponse;
import com.trendistra.trendistashop.entities.Banner;
import com.trendistra.trendistashop.enums.BannerTypeEnum;
import com.trendistra.trendistashop.repositories.BannerRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HomeService implements IHomeService {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private BannerRepository bannerRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    @Transactional
    public TypeResponse<BannerDTO> createBanner(BannerDTO bannerDTO) {
        try {
            Banner banner = new Banner();
            banner.setImageUrl(bannerDTO.getImageUrl());
            banner.setTitle(bannerDTO.getTitle());
            banner.setType(bannerDTO.getType());
            banner.setEvent(bannerDTO.getEvent());
            banner.setDisplayOrder(banner.getDisplayOrder());
            banner.setLinkUrl(bannerDTO.getLinkUrl());
            banner.setIsActive(bannerDTO.getIsActive() != null ? bannerDTO.getIsActive() : true);
            banner.setDisplayOrder(bannerDTO.getDisplayOrder());
            Banner bannerSave = bannerRepository.save(banner);
            BannerDTO bannerResponse = mapper.map(bannerSave, BannerDTO.class);
            return ResponseHelper.created(bannerResponse, "Tạo banner thành công !");
        } catch (Exception e) {
            return ResponseHelper.serverError("Lỗi server");
        }

    }

    @Override
    public TypeResponse<Map<String, List<BannerDTO>>> getBannerWithType(String type) {
        try {
            List<Banner> banners = bannerRepository.findBannerByTypeAndIsActiveTrue(BannerTypeEnum.valueOf(type));
            List<BannerDTO> bannerDTOS = banners.stream().map(
                    banner -> mapper.map(banner, BannerDTO.class)).collect(Collectors.toList());
            Map<String, List<BannerDTO>> groupedByEvent = bannerDTOS.stream()
                    .collect(Collectors.groupingBy(BannerDTO::getEvent));
            return ResponseHelper.ok(groupedByEvent, "Lấy danh sách banner thành công !");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.badRequest("Loại banner không hợp lệ");
        } catch (Exception e) {
            return ResponseHelper.serverError("Lỗi server");
        }

    }

    @Transactional
    @Override
    public TypeResponse<BannerDTO> updateBanner(Long id, BannerDTO bannerDTO) {
        try {
            Banner existingBanner = bannerRepository.findById(id).get();
            if (existingBanner == null) {
                return ResponseHelper.notFound("Không tìm thấy banner");
            }
            existingBanner.setImageUrl(bannerDTO.getImageUrl());
            existingBanner.setTitle(bannerDTO.getTitle());
            existingBanner.setType(bannerDTO.getType());
            existingBanner.setEvent(bannerDTO.getEvent());
            existingBanner.setLinkUrl(bannerDTO.getLinkUrl());
            existingBanner.setIsActive(bannerDTO.getIsActive() != null ? bannerDTO.getIsActive() : true);
            existingBanner.setDisplayOrder(bannerDTO.getDisplayOrder());
            BannerDTO bannerResponse = mapper.map(existingBanner, BannerDTO.class);
            return ResponseHelper.ok(bannerResponse, "Update banner thành công !");
        } catch (Exception e) {
            return ResponseHelper.serverError("Lỗi server");
        }

    }

    @Transactional
    @Override
    public TypeResponse<Void> deleteBanner(Long id) {
        try {
            Banner existingBanner = bannerRepository.findById(id).get();
            if (existingBanner == null) {
                return ResponseHelper.notFound("Không tìm thấy banner");
            }
            // Xóa ảnh trên Cloudinary nếu có
            if (existingBanner.getImageUrl() != null) {
                cloudinaryService.deleteFile(existingBanner.getImageUrl());
                existingBanner.setImageUrl(null);
            }
            existingBanner.preDestroy();
            // Xóa Banner khỏi database
            bannerRepository.save(existingBanner);
            return ResponseHelper.ok(null, "Xóa banner thành công !");
        } catch (Exception e) {
            return ResponseHelper.serverError("Lỗi server");
        }

    }

}
