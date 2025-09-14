package com.trendistashop.docs.example;
public class ProductRequestExamples {
    // Example for creating a product with free shipping, single variant, and NEW_ARRIVALS tag
    public static final String CREATE_PRODUCT_REQUEST = """
                {
                  "name": "Classic T-Shirt",
                  "summary": "Comfortable cotton T-shirt for everyday wear",
                  "description": "A high-quality T-shirt made from 100% organic cotton, available in multiple colors.",
                  "status": true,
                  "originPrice": 25.99,
                  "price": 20.99,
                  "isFreeShip": true,
                  "tag": "NEW_ARRIVALS,BEST_SELLERS,TRENDING,HOT_DEALS,RECOMMENDED",
                  "categoryId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                  "discountIds": [
                    "3fa85f64-5717-4562-b3fc-2c963f66afa6"
                  ],
                  "variants": [
                    {
                      "colorId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                      "sizeId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                      "stockQuantity": 100,
                      "price": 20.99,
                      "order": 1,
                      "images": [
                        {
                          "url": "https://example.com/images/tshirt-blue.jpg",
                          "isThumbnail": true,
                          "order": 1
                        }
                      ]
                    }
                  ]
                }
            """;

    // Example for updating a product with multiple variants and BEST_SELLERS tag
    public static final String UPDATE_PRODUCT_REQUEST = """
        {
          "name": "Premium Jacket Updated",
          "summary": "Updated stylish jacket for winter",
          "description": "Updated warm and durable jacket for cold weather.",
          "status": true,
          "originPrice": 99.99,
          "price": 99.99,
          "isFreeShip": true,
          "tag": "NEW_ARRIVALS,BEST_SELLERS,TRENDING,HOT_DEALS,RECOMMENDED",
          "categoryId": "4fb96g75-6828-5673-c4fd-3d074g77bgb7",
          "discountIds": [],
          "variants": [
            {
              "colorId": "4fb96g75-6828-5673-c4fd-3d074g77bgb7",
              "sizeId": "4fb96g75-6828-5673-c4fd-3d074g77bgb7",
              "stockQuantity": 40,
              "price": 99.99,
              "order": 1,
              "images": [
                {
                  "url": "https://example.com/images/jacket-black-updated.jpg",
                  "isThumbnail": true,
                  "order": 1
                }
              ]
            },
            {
              "colorId": "5gc07h86-7939-6784-d5ge-4e185h88chc8",
              "sizeId": "5gc07h86-7939-6784-d5ge-4e185h88chc8",
              "stockQuantity": 20,
              "price": 99.99,
              "order": 1
              "images": [
                {
                  "url": "https://example.com/images/jacket-blue-updated.jpg",
                  "isThumbnail": true,
                   "order": 1
                }
              ]
            }
          ]
        }
    """;
}