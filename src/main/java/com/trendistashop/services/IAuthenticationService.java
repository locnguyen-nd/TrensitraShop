package com.trendistashop.services;

import com.trendistashop.dto.request.RegisterRequest;
import com.trendistashop.dto.request.ResetPassword;
import com.trendistashop.dto.request.VerifyResetPassword;
import com.trendistashop.dto.response.ErrorResponse;
import com.trendistashop.dto.response.LoginResponse;
import com.trendistashop.dto.response.RegisterResponse;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.entities.user.UserEntity;
import com.trendistashop.enums.GuardType;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;
import java.util.Optional;

/**
 * Interface cung cấp các dịch vụ liên quan đến người dùng trong hệ thống.
 * Được sử dụng để định nghĩa các hành vi (operations) mà các lớp triển khai cần
 * cung cấp.
 */
public interface IAuthenticationService {
    /**
     * Xác thực thông tin đăng nhập của người dùng.
     *
     * @param username tên đăng nhập của người dùng.(email)
     * @param password mật khẩu của người dùng (dưới dạng chuỗi ký tự).
     * @return LoginResponse chứa thông tin user được cấp nếu xác thực thành công.
     */
    TypeResponse<LoginResponse> authenticateUser(String username, CharSequence password, GuardType guard, HttpServletResponse response);

    /**
     * Tạo mới một người dùng trong hệ thống.
     *
     * @param registerRequest đối tượng chứa thông tin đăng ký người dùng.
     * @return RegisterResponse chứa thông tin về kết quả đăng ký.
     */
    TypeResponse<RegisterResponse> createUser(RegisterRequest registerRequest);

    /**
     * Xác minh người dùng dựa trên tên đăng nhập.(email)
     * Sử dụng trong quy trình kích hoạt tài khoản .
     *
     * @param userName tên đăng nhập của người dùng cần xác minh.
     */
    TypeResponse<Void> verifyUser(String userName, String token);

    TypeResponse<Void> resendTokenVerify(String userName);

    UserEntity createUserWithGoogle(OAuth2User oAuth2User);

    Optional<UserEntity> getUser(String userName);

    TypeResponse<Object> logout(String token);

    TypeResponse<LoginResponse> refreshToken(String refreshToken);

    TypeResponse<ErrorResponse> sendCodeResetPassword(String email);

    TypeResponse<ErrorResponse> resendCodeResetPassword(String email);

    TypeResponse<ErrorResponse> verifyResetPassword(VerifyResetPassword request);

    TypeResponse<ErrorResponse> resetPassword(ResetPassword resetPassword);
}
