package com.trendistra.trendistashop.docs.product;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.trendistra.trendistashop.dto.response.TypeResponse;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Lấy danh sách sản phẩm thành công",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = TypeResponse.class),
            examples = @ExampleObject(
                value = """
                    {
                        "success": true,
                        "message": "Lấy danh sách sản phẩm thành công",
                        "data": [
                            {
                                "id": "f6f5b112-fdfc-4fb7-b3bf-fb0bcccf26c9",
                                "productId": "245f416e-d3df-11ef-95a7-c908cce416be",
                                "variantId": "24601f8a-d3df-11ef-95a7-c908cce416be",
                                "imageId": "246348e0-d3df-11ef-95a7-c908cce416be",
                                "productName": "Áo Khoác Nam Mũ Liền Lót Lông",
                                "quantity": 1,
                                "price": 849000,
                                "product": {
                                    "id": "f6f5b112-fdfc-4fb7-b3bf-fb0bcccf26c9",
                                    "productId": "245f416e-d3df-11ef-95a7-c908cce416be",
                                    "variantId": "24601f8a-d3df-11ef-95a7-c908cce416be",
                                    "imageId": "246348e0-d3df-11ef-95a7-c908cce416be",
                                    "productName": "Áo Khoác Nam Mũ Liền Lót Lông",
                                    "quantity": 1,
                                    "price": 849000,
                                    "product": {
                                        "id": "245f416e-d3df-11ef-95a7-c908cce416be",
                                        "name": "Áo Khoác Nam Mũ Liền Lót Lông",
                                        "code": "AKM7007",
                                        "slug": "ao-khoac-nam-mu-lien-lot-long",
                                        "urlImage": "https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617699/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp",
                                        "status": true,
                                        "originPrice": 849000,
                                        "price": 849000,
                                        "tag": "BEST_SELLERS",
                                        "availableQuantities": 100,
                                        "views": 40,
                                        "ratingAverage": 0,
                                        "ratingTotal": 0,
                                        "unitsSold": 0,
                                        "categoryId": "245e3ad0-d3df-11ef-95a7-c908cce416be",
                                        "categoryName": "Áo gió nam",
                                        "isFreeShip": null,
                                        "summary": null,
                                        "description": "Áo khoác nam đa năng mũ liền tiện lợi, lót lông ấm áp. Công nghệ màng PU siêu mỏng nhẹ, chống thấm nước hiệu quả. Thiết kế hiện đại với phối khoá trẻ trung, dây rút chắc chắn, khoá túi trong an toàn để đựng đồ, cúc bấm cổ tay tránh gió lùa. Vải polyester cao cấp, bền bỉ, trượt nước 3k phù hợp mọi thời tiết.",
                                        "discountValue": 0,
                                        "productImages": [
                                            {
                                            "id": "2463462e-d3df-11ef-95a7-c908cce416be",
                                            "url": "https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736945684/ao-khoac-nam-AKM7007-DEN-1_dlw5o5.webp",
                                            "isThumbnail": true,
                                            "productId": "245f416e-d3df-11ef-95a7-c908cce416be",
                                            "colorId": "24588edc-d3df-11ef-95a7-c908cce416be"
                                            }
                                        ],
                                        "productVariants": [
                                            {
                                            "id": "24601ddc-d3df-11ef-95a7-c908cce416be",
                                            "colorId": "24588c2a-d3df-11ef-95a7-c908cce416be",
                                            "sizeId": "245a3606-d3df-11ef-95a7-c908cce416be",
                                            "colorName": "ghi",
                                            "colorCode": "ash",
                                            "colorValue": "#AE9B91",
                                            "sizeName": "M",
                                            "stockQuantity": 10,
                                            "codeVariant": "AKM7007-GHI-M"
                                            }
                                        ]
                                    }
                                }
                            }
                        ],
                        "errors": {},
                        "statusCode": 200
                    }
                """
            )
        )
    ),
    @ApiResponse(
        responseCode = "404",
        description = "Không tìm thấy danh sách sản phẩm",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = TypeResponse.class),
            examples = @ExampleObject(
                value = """
                    {
                        "success": false,
                        "message": "Không tìm thấy danh sách sản phẩm",
                        "data": null,
                        "errors": {},
                        "statusCode": 404
                    }
                """
            )
        )
    ),
})
public @interface GetAllProductDocs {
}
