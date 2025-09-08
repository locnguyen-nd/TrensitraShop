package com.trendistashop.enums;

public enum CloudinaryEnum {
    PRODUCTS("products"),
    CATEGORIES("categories"),
    PROMOTIONS("promotions"),
    BANNERS("banners"),
    AVATARS("avatars"),
    CHATS("chats"),
    COLLECTIONS("collections"),
    GENDERS("genders"),
    DISCOUNTS("discounts");
    private final String folderPath;

    CloudinaryEnum(String folderPath) {
        this.folderPath = folderPath;
    }

    public String getFolderPath() {
        return folderPath;
    }
}
