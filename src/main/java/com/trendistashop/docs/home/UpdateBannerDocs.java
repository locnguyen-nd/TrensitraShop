package com.trendistashop.docs.home;

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
                @ApiResponse(responseCode = "200", description = "Cập nhật banner thành công", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeResponse.class), examples = @ExampleObject(value = """
                                {
                                  "success": true,
                                  "message": "Cập nhật banner thành công",
                                  "data": {
                                      "id": 1,
                                      "title": "Khuyến mãi mùa hè cập nhật",
                                      "event": "Summer Sale",
                                      "imageUrl": "https://res.cloudinary.com/yourcloud/image/upload/v1612345678/banner_updated.jpg",
                                      "linkUrl": "https://example.com/promotion-updated",
                                      "type": "MAIN",
                                      "displayOrder": 1,
                                      "isActive": true
                                  },
                                  "errors": {},
                                  "statusCode": 200
                                }
                                """))),
                @ApiResponse(responseCode = "400", description = "Dữ liệu đầu vào không hợp lệ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeResponse.class), examples = @ExampleObject(value = """
                                {
                                  "success": false,
                                  "message": "Dữ liệu đầu vào không hợp lệ",
                                  "data": null,
                                  "errors": {
                                      "title": "Tiêu đề không được để trống",
                                      "event": "Event không được để trống"
                                  },
                                  "statusCode": 400
                                }
                                """))),
                @ApiResponse(responseCode = "404", description = "Banner không tồn tại", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeResponse.class), examples = @ExampleObject(value = """
                                {
                                  "success": false,
                                  "message": "Không tìm thấy banner cần cập nhật",
                                  "data": null,
                                  "errors": {},
                                  "statusCode": 404
                                }
                                """))),
                @ApiResponse(responseCode = "500", description = "Lỗi hệ thống khi cập nhật banner", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeResponse.class), examples = @ExampleObject(value = """
                                {
                                  "success": false,
                                  "message": "Lỗi hệ thống, vui lòng thử lại sau",
                                  "data": null,
                                  "errors": {},
                                  "statusCode": 500
                                }
                                """)))
})
public @interface UpdateBannerDocs {
}
