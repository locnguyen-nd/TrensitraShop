package com.trendistashop.docs.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.trendistashop.dto.response.TypeResponse;

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
        description = "Đặt lại mật khẩu thành công",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = TypeResponse.class),
            examples = @ExampleObject(
                value = """
                    {
                        "success": true,
                        "message": "Đặt lại mật khẩu thành công",
                        "data": null,
                        "errors": {},
                        "statusCode": 200
                    }
                """
            )
        )
    ),
    @ApiResponse(
        responseCode = "400",
        description = "Token không hợp lệ hoặc đã hết hạn",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = TypeResponse.class),
            examples = @ExampleObject(
                value = """
                    {
                        "success": false,
                        "message": "Token không hợp lệ hoặc đã hết hạn",
                        "data": null,
                        "errors": {},
                        "statusCode": 400
                    }
                """
            )
        )
    ),
    @ApiResponse(
        responseCode = "422",
        description = "Dữ liệu đầu vào không hợp lệ",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = TypeResponse.class),
            examples = @ExampleObject(
                value = """
                    {
                        "success": false,
                        "message": "Dữ liệu không hợp lệ",
                        "data": null,
                        "errors": {
                            "password": "Mật khẩu phải có ít nhất 6 ký tự",
                            "confirmPassword": "Mật khẩu xác nhận không khớp"
                        },
                        "statusCode": 422
                    }
                """
            )
        )
    )
})
public @interface ResetPasswordDocs {
}
