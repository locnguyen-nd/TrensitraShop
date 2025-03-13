package com.trendistra.trendistashop.docs.auth;

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
        description = "Làm mới token thành công",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = TypeResponse.class),
            examples = @ExampleObject(
                value = """
                    {
                        "success": true,
                        "message": "Làm mới token thành công",
                        "data": {
                            "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
                            "refreshToken": "eyJhbGciOiJIUzI1NiJ9..."
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
        description = "Refresh token không hợp lệ hoặc đã hết hạn",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = TypeResponse.class),
            examples = @ExampleObject(
                value = """
                    {
                        "success": false,
                        "message": "Refresh token không hợp lệ hoặc đã hết hạn",
                        "data": null,
                        "errors": {},
                        "statusCode": 400
                    }
                """
            )
        )
    )
})
public @interface RefreshTokenDocs {
}
