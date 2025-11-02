package com.trendistashop.enums;

public enum BannerTypeEnum {
    HERO("Banner Chính", 1),
    CAROUSEL("Slider", 5),
    PROMOTIONAL("Khuyến Mãi", 3),
    CATEGORY("Danh Mục", 4),
    COUNTDOWN("Đếm Ngược", 1),
    INFO("Thông Tin", 2),
    FEATURED("Nổi Bật", 6),
    DISCOUNT("Giảm Giá", 3);

    private final String displayName;
    private final int maxCount;

    BannerTypeEnum(String displayName, int maxCount) {
        this.displayName = displayName;
        this.maxCount = maxCount;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getMaxCount() {
        return maxCount;
    }
}