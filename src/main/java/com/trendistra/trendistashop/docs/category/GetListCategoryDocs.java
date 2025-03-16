package com.trendistra.trendistashop.docs.category;

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
                                "id": "245cb67e-d3df-11ef-95a7-c908cce416be",
                                "name": "Áo nam",
                                "slug": "ao-nam",
                                "description": "Áo nam Trendista chất lượng cao, mẫu mã đa dạng, thời trang và phong cách.",
                                "imageUrl": "https://res.cloudinary.com/dwyjwk0mf/image/upload/v1739153959/CATEGORIES/CATEGORIES_DEV%20NGUYEN.png",
                                "parentId": null,
                                "gender": {
                                "id": "245c535a-d3df-11ef-95a7-c908cce416be",
                                "name": "Nam",
                                "slug": "nam",
                                "imageUrl": null
                                },
                                "items": null,
                                "index": 0
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
    @ApiResponse(
        responseCode = "500",
        description = "Lỗi server",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = TypeResponse.class),
            examples = @ExampleObject(
                value = """
                    {
                        "success": false,
                        "message": "Lỗi server",
                        "data": null,
                        "errors": {},
                        "statusCode": 500
                    }
                """
            )
        )
    )
})
public @interface GetListCategoryDocs {
}
