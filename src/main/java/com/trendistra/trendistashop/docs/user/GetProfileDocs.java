package com.trendistra.trendistashop.docs.user;

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
        description = "Lấy thông tin người dùng thành công",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = TypeResponse.class),
            examples = @ExampleObject(
                value = """
                    {
                        "success": true,
                        "message": "Lấy thông tin người dùng thành công",
                        "data": null,
                        "errors": {},
                        "statusCode": 200
                    }
                """
            )
        )
    ),
    @ApiResponse(
        responseCode = "401",
        description = "Yêu cầu đăng nhập",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = TypeResponse.class),
            examples = @ExampleObject(
                value = """
                    {
                        "success": false,
                        "message": "Yêu cầu đăng nhập",
                        "data": null,
                        "errors": {},
                        "statusCode": 401
                    }
                """
            )
        )
    ),
    @ApiResponse(
        responseCode = "403",
        description = "Không có quyền truy cập",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = TypeResponse.class),
            examples = @ExampleObject(
                value = """
                    {
                        "success": false,
                        "message": "Không có quyền truy cập",
                        "data": null,
                        "errors": {},
                        "statusCode": 403
                    }
                """
            )
        )
    ),
    @ApiResponse(
        responseCode = "404",
        description = "Không tồn tại người dùng",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = TypeResponse.class),
            examples = @ExampleObject(
                value = """
                    {
                        "success": false,
                        "message": "Không tồn tại người dùng",
                        "data": null,
                        "errors": {},
                        "statusCode": 404
                    }
                """
            )
        )
    ),
})
public @interface GetProfileDocs {
}
