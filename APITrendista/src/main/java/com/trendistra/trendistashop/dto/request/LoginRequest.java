package com.trendistra.trendistashop.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * DTO (Data Transfer Object) đại diện cho yêu cầu đăng ký từ phía người dùng.
 * Class này được sử dụng để truyền dữ liệu từ client đến server.
 *
 * - @Data: Sinh tự động các phương thức getter, setter, toString, equals và hashCode.
 * - @Builder: Hỗ trợ xây dựng đối tượng bằng Builder Pattern, giúp khởi tạo đối tượng một cách linh hoạt.
 * - @NoArgsConstructor: Tạo constructor không tham số, dùng để khởi tạo đối tượng mặc định.
 * - @AllArgsConstructor: Tạo constructor với đầy đủ các tham số, dùng khi cần khởi tạo với giá trị cụ thể.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotEmpty(message = "Email không được để trống")
    private String email;
    @NotEmpty(message = "Password không được để trống")
    private CharSequence password;
}
