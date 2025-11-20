package com.trendistashop.docs.example;

public class DiscountRequestExamples {
    public static final String CREATE_DISCOUNT_ORDER_REQUEST = """
        {
          "code": "ORDER10",
          "description": "10% off on total order value",
          "discountType": "PERCENT/AMOUNT",
          "discountApplyFor": "ORDER",
          "discountValue": 10,
          "maxDiscountValue": 50,
          "minOrderValue": 100,
          "frame": "url image for discount",
          "startDate": "2025-09-07T00:00:00",
          "endDate": "2025-12-31T23:59:59",
          "isActive": true
        }
    """;

    // Example for creating a discount applied to PRODUCT
    public static final String CREATE_DISCOUNT_PRODUCT_REQUEST = """
        {
          "code": "PRODUCT20",
          "description": "20% off on specific products",
          "discountType": "PERCENT/AMOUNT",
          "discountApplyFor": "PRODUCT",
          "discountValue": 20,
          "maxDiscountValue": 20,
          "minOrderValue": 0,
          "frame": "url image for discount",
          "startDate": "2025-09-07T00:00:00",
          "endDate": "2025-12-31T23:59:59",
          "isActive": true
        }
    """;
    public static final String CREATE_DISCOUNT_SHIPPING_REQUEST = """
        {
          "code": "FREESHIP15",
          "description": "Free 15K fee shipping for order",
          "discountType": "AMOUNT",
          "discountApplyFor": "SHIPPING",
          "discountValue": 15000,
          "maxDiscountValue": 0,
          "minOrderValue": 0,
          "usageLimit": 100,
          "maxUsagePerCustomer": 3,
          "frame": "url image for discount",
          "startDate": "2025-09-07T00:00:00",
          "endDate": "2025-12-31T23:59:59",
          "isActive": true
        }
    """;
    // Example for updating a discount applied to ORDER
    public static final String UPDATE_DISCOUNT_ORDER_REQUEST = """
        {
          "code": "ORDER15",
          "description": "Updated 15% off on total order value",
         "discountType": "PERCENT/AMOUNT",
          "discountApplyFor": "ORDER",
          "discountValue": 15,
          "maxDiscountValue": 75,
          "minOrderValue": 120,
          "frame": "url image for discount",
          "startDate": "2025-09-07T00:00:00",
          "endDate": "2026-01-31T23:59:59",
          "isActive": true
        }
    """;

    // Example for updating a discount applied to PRODUCT
    public static final String UPDATE_DISCOUNT_PRODUCT_REQUEST = """
        {
          "code": "PRODUCT25",
          "description": "Updated 25% off on specific products",
         "discountType": "PERCENT/AMOUNT",
          "discountApplyFor": "PRODUCT",
          "discountValue": 25,
          "maxDiscountValue": 150,
          "minOrderValue": 60,
          "frame": "url image for discount",
          "startDate": "2025-09-07T00:00:00",
          "endDate": "2026-01-31T23:59:59",
          "isActive": true
        }
    """;
}