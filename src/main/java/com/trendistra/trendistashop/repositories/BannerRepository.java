package com.trendistra.trendistashop.repositories;

import com.trendistra.trendistashop.entities.Banner;
import com.trendistra.trendistashop.enums.BannerTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner,Long> {
    List<Banner> findBannerByTypeAndIsActiveTrue(BannerTypeEnum bannerTypeEnum);
}
