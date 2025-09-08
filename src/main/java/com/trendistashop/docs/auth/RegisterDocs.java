package com.trendistashop.docs.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.trendistashop.dto.response.RegisterResponse;
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
        responseCode = "201",
        description = "Đăng ký thành công.",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = RegisterResponse.class),
            examples = @ExampleObject(
                value = """
                    {
                        "success": true,
                        "message": "Đăng ký thành công. vui lòng kiểm tra email kích hoạt tài khoản",
                        "data": {
                            "firstName": "John",
                            "lastName": "Doe",
                            "email": "john.doe@example.com",
                            "phoneNumber": "0987654321",
                            "enabled": false
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
        description = "Dữ liệu đăng ký không hợp lệ.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = TypeResponse.class),
            examples = @ExampleObject(
                value = """
                    {
                        "success": false,
                        "message": "Tài khoản đã tồn tại",
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
        description = "Lỗi validate dữ liệu",
        content = @Content(
            examples = @ExampleObject(
                value = """
                    {
                        "success": false,
                        "message": "Dữ liệu không hợp lệ",
                        "data": null,
                        "errors": {
                            "email": "Email không đúng định dạng",
                            "password": "Mật khẩu phải có ít nhất 6 ký tự",
                            "phoneNumber": "Số điện thoại không hợp lệ"
                        },
                        "statusCode": 422
                    }
                """
            )
        )
    )
})
public @interface RegisterDocs {
}
