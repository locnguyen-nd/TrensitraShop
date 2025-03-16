package com.trendistra.trendistashop.docs.size;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.trendistra.trendistashop.dto.response.LoginResponse;
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
        description = "Tạo size thành công",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = LoginResponse.class),
            examples = @ExampleObject(
                value = """
                    {
                        "success": true,
                        "message": "Tạo size thành công",
                        "data": {
                            "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                            "name": "M"
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
                            "value": "Tên size không được để trống"
                        },
                        "statusCode": 422
                    }
                """
            )
        )
    )
})
public @interface CreateSizeDocs {
}
