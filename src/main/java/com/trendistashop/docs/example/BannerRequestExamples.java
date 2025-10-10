package com.trendistashop.docs.example;
public class BannerRequestExamples {
    public static final String CREATE_BANNER_REQUEST = """
            {
              "event": "Black Friday Sale",
              "type": "HERO,CAROUSEL,PROMOTIONAL,CATEGORY,COUNTDOWN,INFO,FEATURED,DISCOUNT",
              "isActive": true,
              "bannerImages": [
                {
                  "imageUrl": "https://res.cloudinary.com/trendistashop/image/upload/v1234567890/banners/black-friday-1.jpg",
                  "linkUrl": "https://trendistashop.com/black-friday",
                  "content": "Discount up to 50% off!",
                  "displayOrder": 1
                },
                {
                  "imageUrl": "https://res.cloudinary.com/trendistashop/image/upload/v1234567890/banners/black-friday-2.jpg",
                  "linkUrl": "https://trendistashop.com/black-friday/deals",
                  "content": "Limited time offer",
                  "displayOrder": 2
                }
              ]
            }
            """;

    public static final String UPDATE_BANNER_REQUEST = """
             {
              "event": "Black Friday Sale Updated",
              "type": "HERO,CAROUSEL,PROMOTIONAL,CATEGORY,COUNTDOWN,INFO,FEATURED,DISCOUNT",
              "isActive": true,
              "bannerImages": [
                {
                  "imageUrl": "https://res.cloudinary.com/trendistashop/image/upload/v1234567890/banners/black-friday-1.jpg",
                  "linkUrl": "https://trendistashop.com/black-friday",
                  "content": "Discount up to 50% off!",
                  "displayOrder": 1
                }
              ]
            }
            """;
}
