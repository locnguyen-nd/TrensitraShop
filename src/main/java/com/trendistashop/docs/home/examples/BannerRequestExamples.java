package com.trendistashop.docs.home.examples;

public class BannerRequestExamples {
    public static final String CREATE_BANNER_REQUEST = """
            {
              "title": "Khuyến mãi mùa hè",
              "event": "Summer Sale",
              "imageUrl": "https://res.cloudinary.com/yourcloud/image/upload/v1612345678/banner1.jpg",
              "linkUrl": "https://example.com/promotion", 
              "type": "HERO,CAROUSEL,PROMOTIONAL,CATEGORY,COUNTDOWN,INFO,FEATURED,DISCOUNT",
              "displayOrder": 0,
              "isActive": true
            }
            """;

    public static final String UPDATE_BANNER_REQUEST = """
             {
              "id": "1",
              "title": "Khuyến mãi mùa hè",
              "event": "Summer Sale",
              "imageUrl": "https://res.cloudinary.com/yourcloud/image/upload/v1612345678/banner1.jpg",
              "linkUrl": "https://example.com/promotion", 
              "type": "HERO,CAROUSEL,PROMOTIONAL,CATEGORY,COUNTDOWN,INFO,FEATURED,DISCOUNT",
              "displayOrder": 0,
              "isActive": true
            }
            """;
}
