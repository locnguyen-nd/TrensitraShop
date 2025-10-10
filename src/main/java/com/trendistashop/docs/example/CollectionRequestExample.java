package com.trendistashop.docs.example;

public class CollectionRequestExample {
    public static final String CREATE_COLLECTION = """
            {
              "name": "Bộ sưu tập Xuân Hè 2025",
              "description": "Bộ sưu tập thời trang xuân hè với thiết kế tươi trẻ, kết hợp giữa phong cách đường phố và thanh lịch, phù hợp cho mọi lứa tuổi.",
              "thumbnail": "https://res.cloudinary.com/trendistashop/image/upload/v1718034567/thumbnails/xuanhe-thumbnail.jpg",
              "bannerUrl": "https://res.cloudinary.com/trendistashop/image/upload/v1718034567/banners/xuanhe-banner.jpg",
              "status": true,
              "orderIndex": 1,
              "productIds": [
                "e4a25a99-c19a-4072-bc2f-b0d2988005af"
              ],
              "subThemes": [
                {
                  "name": "Casual",
                  "description": "Phong cách thoải mái, phù hợp dạo phố và cuối tuần với áo thun, quần jeans và sneaker.",
                  "imageUrl": "https://res.cloudinary.com/trendistashop/image/upload/v1718034567/subthemes/casual-icon.jpg",
                  "priority": 1,
                  "productIds": [
                    "e4a25a99-c19a-4072-bc2f-b0d2988005af"
                  ]
                },
                {
                  "name": "Formal",
                  "description": "Thiết kế lịch sự cho văn phòng và sự kiện, với vest, sơ mi và giày da cao cấp.",
                  "imageUrl": "https://res.cloudinary.com/trendistashop/image/upload/v1718034567/subthemes/formal-icon.jpg",
                  "priority": 2,
                  "productIds": [
                    "e4a25a99-c19a-4072-bc2f-b0d2988005af"
                  ]
                }
              ]
            }
            """;
    public static final String UPDATE_COLLECTION = """
            {
              "name": "Bộ sưu tập Xuân Hè 2025 Update",
              "description": "Bộ sưu tập thời trang xuân hè với thiết kế tươi trẻ, kết hợp giữa phong cách đường phố và thanh lịch, phù hợp cho mọi lứa tuổi.",
              "thumbnail": "https://res.cloudinary.com/trendistashop/image/upload/v1718034567/thumbnails/xuanhe-thumbnail.jpg",
              "bannerUrl": "https://res.cloudinary.com/trendistashop/image/upload/v1718034567/banners/xuanhe-banner.jpg",
              "status": true,
              "orderIndex": 1,
              "productIds": [
                "e4a25a99-c19a-4072-bc2f-b0d2988005af"
              ],
              "subThemes": [
                {
                  "name": "Casual",
                  "description": "Phong cách thoải mái, phù hợp dạo phố và cuối tuần với áo thun, quần jeans và sneaker.",
                  "imageUrl": "https://res.cloudinary.com/trendistashop/image/upload/v1718034567/subthemes/casual-icon.jpg",
                  "priority": 1,
                  "productIds": [
                    "e4a25a99-c19a-4072-bc2f-b0d2988005af"
                  ]
                }
              ]
            }
            """;
}
