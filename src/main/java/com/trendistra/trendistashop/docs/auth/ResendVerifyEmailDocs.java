package com.trendistra.trendistashop.docs.auth;

import com.trendistra.trendistashop.dto.response.TypeResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME) 
@ApiResponses({
@ApiResponse(
        responseCode = "200", 
        description = "Gửi lại email xác thực thành công",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = TypeResponse.class),
            examples = @ExampleObject(
                value = """
                    {
                        "success": true,
                        "message": "Đã gửi lại email xác thực. Vui lòng kiểm tra hộp thư của bạn",
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
        description = "Tài khoản đã được kích hoạt hoặc không hợp lệ",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = TypeResponse.class),
            examples = @ExampleObject(
                value = """
                    {
                        "success": false,
                        "message": "Tài khoản đã được kích hoạt hoặc không hợp lệ",
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
        description = "Không tìm thấy người dùng",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = TypeResponse.class),
            examples = @ExampleObject(
                value = """
                    {
                        "success": false,
                        "message": "Lỗi dữ liệu đầu vào",
                        "data": null,
                        "errors": {
                            "email": "Không tìm thấy người dùng"
                        },
                        "statusCode": 422
                    }
                """
            )
        )
    )
})
public @interface ResendVerifyEmailDocs {
}
