package com.trendistra.trendistashop.controllers.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendistra.trendistashop.dto.response.BannerDTO;
import com.trendistra.trendistashop.services.HomeService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/home")
@CrossOrigin
@Tag(name = "HomePage")
public class HomeController {
    @Autowired
    private HomeService homeService;
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BannerDTO> createBanner(
            @RequestPart("bannerDto") String bannerDTOJson,
            @RequestParam(required = false) MultipartFile imageFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        BannerDTO bannerDTO = objectMapper.readValue(bannerDTOJson, BannerDTO.class);
        bannerDTO = homeService.createBanner(bannerDTO, imageFile);
        return ResponseEntity.ok(bannerDTO);
    }
    @GetMapping("/banner/{type}")
    public ResponseEntity<Map<String, List<BannerDTO>>> getBannerWithType(
            @PathVariable String type) {
        Map<String, List<BannerDTO>> banners = homeService.getBannerWithType(type);
        return ResponseEntity.ok(banners);
    }

}
