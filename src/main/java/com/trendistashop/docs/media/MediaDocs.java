package com.trendistashop.docs.media;
import com.trendistashop.dto.response.TypeResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation dùng để mô tả các response của API Media (upload, update, delete)
 * theo định dạng Swagger tương tự như LoginDocs.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses({
        @ApiResponse(
                responseCode = "201",
                description = "Tải file media lên thành công",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = TypeResponse.class),
                        examples = @ExampleObject(
                                value = """
                    {
                      "success": true,
                      "message": "File uploaded successfully.",
                      "data": [
                          "https://res.cloudinary.com/yourcloud/image/upload/v1612345678/folder_123e4567-e89b-12d3-a456-426614174000.mp4"
                      ],
                      "errors": {},
                      "statusCode": 201
                    }
                    """
                        )
                )
        ),
        @ApiResponse(
                responseCode = "200",
                description = "Thao tác thành công (update hoặc delete)",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = TypeResponse.class),
                        examples = @ExampleObject(
                                value = """
                    {
                      "success": true,
                      "message": "Media updated successfully.",
                      "data": "https://res.cloudinary.com/yourcloud/image/upload/v1612345678/folder_123e4567-e89b-12d3-a456-426614174999.png",
                      "errors": {},
                      "statusCode": 200
                    }
                    """
                        )
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Yêu cầu không hợp lệ (thiếu folder, file, imageUrl, ...)",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = TypeResponse.class),
                        examples = @ExampleObject(
                                value = """
                    {
                      "success": false,
                      "message": "Folder parameter is required",
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
                description = "File không hợp lệ (không đúng định dạng hoặc vượt quá kích thước cho phép)",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = TypeResponse.class),
                        examples = @ExampleObject(
                                value = """
                    {
                      "success": false,
                      "message": "Invalid file. Allowed types: images (jpeg, png, gif, webp up to 10MB) and videos (mp4, mpeg, quicktime, avi up to 50MB).",
                      "data": null,
                      "errors": {
                          "file": "File exceeds maximum allowed size"
                      },
                      "statusCode": 422
                    }
                    """
                        )
                )
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Lỗi hệ thống khi thao tác với Cloudinary",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = TypeResponse.class),
                        examples = @ExampleObject(
                                value = """
                    {
                      "success": false,
                      "message": "Failed to upload file: filename.mp4",
                      "data": null,
                      "errors": {},
                      "statusCode": 500
                    }
                    """
                        )
                )
        )
})
public @interface MediaDocs {
}
