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
                                "gender": {
                                "id": "245c54fe-d3df-11ef-95a7-c908cce416be",
                                "name": "Nữ",
                                "slug": "nu",
                                "imageUrl": null
                                },
                                "categories": [
                                    {
                                        "id": "245cb00c-d3df-11ef-95a7-c908cce416be",
                                        "name": "Áo nữ",
                                        "slug": "ao-nu",
                                        "description": "Khi nhắc đến tủ đồ của các chị em, bạn sẽ hoàn toàn bị thu hút bởi những chiễc áo nữ đang được treo ngay ngắn. Thời trang áo nữ kiểu luôn đa dạng và phát triển không ngừng với đa dạng thiết kế. Vậy những kiểu áo nữ đẹp nào đang được đông đảo phái nữ quan tâm? Hãy cùng TRENDISTA tìm hiểu ngay nhé!",
                                        "imageUrl": null,
                                        "parentId": null,
                                        "gender": null,
                                        "items": [
                                            {
                                                "id": "245e1e7e-d3df-11ef-95a7-c908cce416be",
                                                "name": "Áo vest nữ",
                                                "slug": "ao-vest-nu",
                                                "description": null,
                                                "imageUrl": null,
                                                "parentId": null,
                                                "gender": null,
                                                "items": [],
                                                "index": 0
                                            }
                                        ],
                                        "index": 0
                                    }
                                ]
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
public @interface GetAllCategoryDocs {
}
