package com.trendistra.trendistashop.services;

import com.trendistra.trendistashop.dto.request.RegisterRequest;
import com.trendistra.trendistashop.dto.response.LoginResponse;
import com.trendistra.trendistashop.dto.response.RegisterResponse;
import com.trendistra.trendistashop.entities.user.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;

/**
 * Interface cung cấp các dịch vụ liên quan đến người dùng trong hệ thống.
 * Được sử dụng để định nghĩa các hành vi (operations) mà các lớp triển khai cần cung cấp.
 */
public interface IAuthenticationService {
    /**
     * Xác thực thông tin đăng nhập của người dùng.
     *
     * @param username tên đăng nhập của người dùng.(email)
     * @param password mật khẩu của người dùng (dưới dạng chuỗi ký tự).
     * @return LoginResponse chứa thông tin user được cấp nếu xác thực thành công.
     */
    public LoginResponse authenticateUser(String username, CharSequence password);
    /**
     * Tạo mới một người dùng trong hệ thống.
     *
     * @param registerRequest đối tượng chứa thông tin đăng ký người dùng.
     * @return RegisterResponse chứa thông tin về kết quả đăng ký.
     */
    public RegisterResponse createUser(RegisterRequest registerRequest);

    /**
     * Xác minh người dùng dựa trên tên đăng nhập.(email)
     * Sử dụng trong quy trình kích hoạt tài khoản .
     *
     * @param userName tên đăng nhập của người dùng cần xác minh.
     */
    public void verifyUser(String userName, String code) ;
    public UserEntity createUserWithGoogle(OAuth2User oAuth2User);

    Optional<UserEntity> getUser(String userName);
    public void logout (String token);
    public String refreshToken (String refreshToken);
}
