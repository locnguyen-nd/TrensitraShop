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
                responseCode = "201",
                description = "Tạo danh mục thành công",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = TypeResponse.class),
                        examples = @ExampleObject(
                                value = """
                    {
                      "success": true,
                      "message": "Tạo danh mục thành công",
                      "data": {
                        "id": "245cb00c-d3df-11ef-95a7-c908cce416be",
                        "name": "Áo nữ",
                        "slug": "ao-nu",
                        "description": "Mô tả danh mục...",
                        "imageUrl": "https://res.cloudinary.com/yourcloud/image/upload/v1612345678/ao-nu.png",
                        "parent": null,
                        "gender": "245c54fe-d3df-11ef-95a7-c908cce416be"
                      },
                      "errors": {},
                      "statusCode": 201
                    }
                    """
                        )
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Dữ liệu đầu vào không hợp lệ",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = TypeResponse.class),
                        examples = @ExampleObject(
                                value = """
                    {
                      "success": false,
                      "message": "Dữ liệu đầu vào không hợp lệ",
                      "data": null,
                      "errors": {
                        "name": "Tên không được để trống",
                        "gender": "Gender là bắt buộc"
                      },
                      "statusCode": 400
                    }
                    """
                        )
                )
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Lỗi server khi tạo danh mục",
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
public @interface CreateCategoryDocs {
}
