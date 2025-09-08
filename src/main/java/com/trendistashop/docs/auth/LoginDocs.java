package com.trendistashop.docs.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.trendistashop.dto.response.LoginResponse;
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
        description = "Đăng nhập thành công",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = LoginResponse.class),
            examples = @ExampleObject(
                value = """
                    {
                        "success": true,
                        "message": "Đăng nhập thành công",
                        "data": {
                            "id": "b13c6ab2-882d-4b29-9136-d7d977078d30",
                            "firstName": "trendista",
                            "lastName": "trendista",
                            "phoneNumber": "+0123456789",
                            "email": "trendista@example.com",
                            "authorityList": ["ROLE_USER"],
                            "token": "eyJhbGciOiJIUzI1NiJ9....",
                            "expiresIn": 86400,
                            "isEnabled": true,
                            "isLocked": false
                        },
                        "errors": {},
                        "statusCode": 200
                    }
                """
            )
        )
    ),
    @ApiResponse(
        responseCode = "400",
        description = "Thông tin đăng nhập không chính xác",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = TypeResponse.class),
            examples = @ExampleObject(
                value = """
                    {
                        "success": false,
                        "message": "Email hoặc mật khẩu không chính xác",
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
                            "email": "Email không đúng định dạng",
                            "password": "Mật khẩu không được để trống"
                        },
                        "statusCode": 422
                    }
                """
            )
        )
    )
})
public @interface LoginDocs {
}
