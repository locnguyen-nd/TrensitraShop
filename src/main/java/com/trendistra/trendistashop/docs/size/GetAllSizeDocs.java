package com.trendistra.trendistashop.docs.size;

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
        description = "Lấy danh sách size thành công",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = TypeResponse.class),
            examples = @ExampleObject(
                value = """
                    {
                        "success": true,
                        "message": "Lấy danh sách size thành công",
                        "data": [
                            {
                                "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                                "value": "string"
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
        description = "Không tìm thấy danh sách size",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = TypeResponse.class),
            examples = @ExampleObject(
                value = """
                    {
                        "success": false,
                        "message": "Không tìm thấy danh sách size",
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
    ),
})
public @interface GetAllSizeDocs {
}
