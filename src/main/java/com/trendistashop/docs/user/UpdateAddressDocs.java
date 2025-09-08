package com.trendistashop.docs.user;

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
        description = "Cập nhật địa chỉ thành công",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = LoginResponse.class),
            examples = @ExampleObject(
                value = """
                    {
                        "success": true,
                        "message": "Cập nhật địa chỉ thành công",
                        "data": {
                            "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                            "name": "Màu đỏ",
                            "code": "red",
                            "value": "#FF0000"
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
        description = "Dữ liệu không được để trống",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = TypeResponse.class),
            examples = @ExampleObject(
                value = """
                    {
                        "success": false,
                        "message": "Dữ liệu không được để trống",
                        "data": null,
                        "errors": {},
                        "statusCode": 400
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
        description = "Không có quyền thay đổi địa chỉ",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = TypeResponse.class),
            examples = @ExampleObject(
                value = """
                    {
                        "success": false,
                        "message": "Không có quyền thay đổi địa chỉ",
                        "data": null,
                        "errors": {},
                        "statusCode": 403
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
                            "name": "Tên màu không được để trống",
                            "code": "Mã màu không được để trống",
                            "value": "Mã màu không được để trống"
                        },
                        "statusCode": 422
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
public @interface UpdateAddressDocs {
}
