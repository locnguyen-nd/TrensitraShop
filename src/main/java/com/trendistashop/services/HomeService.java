package com.trendistashop.services;

import com.trendistashop.constants.ResponseMessage;
import com.trendistashop.dto.request.BannerImageRequest;
import com.trendistashop.dto.request.BannerRequestDTO;
import com.trendistashop.dto.response.BannerImageResponse;
import com.trendistashop.dto.response.BannerResponseDTO;
import com.trendistashop.entities.Banner;
import com.trendistashop.entities.BannerImage;
import com.trendistashop.specifications.BannerSpecification;
import com.trendistashop.utils.ResponseHelper;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.enums.BannerTypeEnum;
import com.trendistashop.repositories.BannerRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HomeService implements IHomeService {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private BannerRepository bannerRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    @Transactional
    public TypeResponse<BannerResponseDTO> getBannerById(Long id) {
        try {
            Optional<Banner> bannerOpt = bannerRepository.findById(id);
            if (bannerOpt.isEmpty()) {
                return ResponseHelper.notFound(ResponseMessage.NOT_FOUND);
            }
            BannerResponseDTO bannerResponse = mapToBannerResponse(bannerOpt.get());
            log.info("Fetched banner with ID: {}", id);
            return ResponseHelper.ok(bannerResponse, ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            log.error("Error fetching banner: {}", e.getMessage());
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }
    @Override
    @Transactional
    public TypeResponse<BannerResponseDTO> createBanner(BannerRequestDTO bannerDTO) {
        try {
            Banner banner = mapper.map(bannerDTO, Banner.class);
            if (bannerRepository.existsByEvent(banner.getEvent())) {
                log.warn("Banner with event '{}' already exists.", bannerDTO.getEvent());
                return ResponseHelper.badRequest(ResponseMessage.BANNER_EVENT_EXISTS);
            }
            banner.setEvent(bannerDTO.getEvent());
            banner.setType(bannerDTO.getType());
            banner.setIsActive(bannerDTO.getIsActive() != null ? bannerDTO.getIsActive() : false);
            if (bannerDTO.getBannerImages() != null && !bannerDTO.getBannerImages().isEmpty()) {
                List<BannerImage> bannerImages = bannerDTO.getBannerImages().stream()
                        .map(imageReq -> {
                            BannerImage image = new BannerImage();
                            image.setImageUrl(imageReq.getImageUrl());
                            image.setLinkUrl(imageReq.getLinkUrl());
                            image.setContent(imageReq.getContent());
                            image.setDisplayOrder(imageReq.getDisplayOrder());
                            image.setBanner(banner);
                            return image;
                        })
                        .collect(Collectors.toList());
                banner.setBannerImages(bannerImages);
            }
            Banner bannerSave = bannerRepository.save(banner);
            BannerResponseDTO bannerResponse = mapToBannerResponse(bannerSave);
            log.info("Created new banner with ID: {}", bannerResponse.getId());
            return ResponseHelper.created(bannerResponse, ResponseMessage.CREATE_SUCCESS);
        } catch (Exception e) {
            log.error("Error creating banner: {}", e.getMessage());
            return ResponseHelper.serverError(ResponseMessage.CREATE_FAILED);
        }
    }

    @Override
    public TypeResponse<List<BannerResponseDTO>> getBannerWithTypeAndFilter(String type, String event, Boolean isActive) {
        try {
            BannerTypeEnum bannerType = null;
            if(type != null && !type.isEmpty()) {
                bannerType = BannerTypeEnum.valueOf(type.toUpperCase());
            }
            Specification<Banner> spec = BannerSpecification.withFilters(bannerType, event, isActive);
            List<Banner> banners = bannerRepository.findAll(spec);
            List<BannerResponseDTO> bannerResponses = banners.stream()
                    .map(this::mapToBannerResponse)
                    .collect(Collectors.toList());

            log.info("Found {} banners", banners.size());
            return ResponseHelper.ok(bannerResponses, ResponseMessage.FETCH_SUCCESS);
        } catch (IllegalArgumentException e) {
            log.error("Err: {}", e.getMessage());
            return ResponseHelper.badRequest(ResponseMessage.VALIDATION_ERROR);
        } catch (Exception e) {
            log.error("Error fetching banners: {}", e.getMessage());
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }

    @Override
    @Transactional
    public TypeResponse<BannerResponseDTO> updateBanner(Long id, BannerRequestDTO bannerDTO) {
        try {
            Optional<Banner> existingBanner = bannerRepository.findById(id);
            if (existingBanner.isEmpty()) {
                return ResponseHelper.notFound(ResponseMessage.NOT_FOUND);
            }
            Banner banner = existingBanner.get();
            banner.setEvent(bannerDTO.getEvent());
            banner.setType(bannerDTO.getType());
            banner.setIsActive(bannerDTO.getIsActive() != null ? bannerDTO.getIsActive() : true);
            if (bannerDTO.getBannerImages() != null) {
                List<BannerImage> oldImages = banner.getBannerImages();
                List<String> newImageUrls = bannerDTO.getBannerImages()
                        .stream()
                        .map(BannerImageRequest::getImageUrl)
                        .filter(Objects::nonNull)
                        .toList();

                List<BannerImage> imagesToRemove = oldImages.stream()
                        .filter(img -> img.getImageUrl() != null && !newImageUrls.contains(img.getImageUrl()))
                        .toList();

                imagesToRemove.forEach(img -> {
                    cloudinaryService.deleteFile(img.getImageUrl());
                    oldImages.remove(img);
                });

                for (BannerImageRequest imageReq : bannerDTO.getBannerImages()) {
                    boolean exists = oldImages.stream()
                            .anyMatch(img -> Objects.equals(img.getImageUrl(), imageReq.getImageUrl()));
                    if (!exists) {
                        BannerImage newImg = new BannerImage();
                        newImg.setImageUrl(imageReq.getImageUrl());
                        newImg.setLinkUrl(imageReq.getLinkUrl());
                        newImg.setContent(imageReq.getContent());
                        newImg.setDisplayOrder(imageReq.getDisplayOrder());
                        newImg.setBanner(banner);
                        oldImages.add(newImg);
                    }
                }
            }

            Banner updatedBanner = bannerRepository.save(banner);
            BannerResponseDTO bannerResponse = mapToBannerResponse(updatedBanner);
            log.info("Updated banner with ID: {}", bannerResponse.getId());
            return ResponseHelper.ok(bannerResponse, ResponseMessage.UPDATE_SUCCESS);
        } catch (Exception e) {
            log.error("Error fetching banner: {}", e.getMessage());
            return ResponseHelper.serverError(ResponseMessage.UPDATE_FAILED);
        }
    }

    @Override
    @Transactional
    public TypeResponse<Void> deleteBanner(Long id) {
        try {
            Optional<Banner> existingBanner = bannerRepository.findById(id);
            if (existingBanner.isEmpty()) {
                return ResponseHelper.notFound(ResponseMessage.NOT_FOUND);
            }
            Banner banner = existingBanner.get();
            banner.getBannerImages().forEach(image -> {
                if (image.getImageUrl() != null) {
                    cloudinaryService.deleteFile(image.getImageUrl());
                }
            });
            bannerRepository.delete(banner);
            log.info("Deleted banner with ID: {}", id);
            return ResponseHelper.ok(null, ResponseMessage.DELETE_SUCCESS);
        } catch (Exception e) {
            log.error("Error deleting banner: {}", e.getMessage());
            return ResponseHelper.serverError(ResponseMessage.DELETE_FAILED);
        }
    }

    @Override
    public TypeResponse<BannerResponseDTO> setDisplay(Long id, Boolean isActive, BannerTypeEnum bannerTypeEnum) {
        try {
            Optional<Banner> existingBannerOpt = bannerRepository.findById(id);
            if (existingBannerOpt.isEmpty()) {
                return ResponseHelper.notFound(ResponseMessage.NOT_FOUND);
            }
            Banner banner = existingBannerOpt.get();
            if (Boolean.TRUE.equals(isActive)) {
                int maxCount = bannerTypeEnum.getMaxCount();

                long currentActiveCount = bannerRepository.countByTypeAndIsActiveTrue(bannerTypeEnum);
                if (!banner.getIsActive() && currentActiveCount >= maxCount) {
                    return ResponseHelper.badRequest(
                            String.format(
                                    "Đã đạt giới hạn hiển thị cho loại %s (tối đa %d). Hiện tại: %d",
                                    bannerTypeEnum.getDisplayName(), maxCount, currentActiveCount
                            )
                    );
                }
            }

            banner.setIsActive(isActive);
            banner.setType(bannerTypeEnum);
            bannerRepository.save(banner);
            BannerResponseDTO bannerResponse = mapToBannerResponse(banner);
            log.info("Cập nhật hiển thị banner ID: {}, isActive: {}, type: {}", id, isActive, bannerTypeEnum);
            return ResponseHelper.ok(bannerResponse, ResponseMessage.UPDATE_SUCCESS);
        } catch (Exception e) {
            log.error("Lỗi cập nhật hiển thị banner ID: {}. Chi tiết: {}", id, e.getMessage(), e);
            return ResponseHelper.serverError(ResponseMessage.UPDATE_FAILED);
        }
    }

    private BannerResponseDTO mapToBannerResponse(Banner banner) {
        BannerResponseDTO response = new BannerResponseDTO();
        response.setId(banner.getId());
        response.setEvent(banner.getEvent());
        response.setType(banner.getType());
        response.setIsActive(banner.getIsActive());

        if (banner.getBannerImages() != null) {
            List<BannerImageResponse> imageResponses = banner.getBannerImages().stream()
                    .map(image -> {
                        BannerImageResponse imgResp = new BannerImageResponse();
                        imgResp.setImageUrl(image.getImageUrl());
                        imgResp.setLinkUrl(image.getLinkUrl());
                        imgResp.setContent(image.getContent() != null ? image.getContent() : "");
                        imgResp.setDisplayOrder(image.getDisplayOrder());
                        return imgResp;
                    })
                    .collect(Collectors.toList());
            response.setBannerImages(imageResponses);
        }
        return response;
    }

}
