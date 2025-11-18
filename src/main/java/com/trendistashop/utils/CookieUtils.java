package com.trendistashop.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Locnd
 * Lấy refreshToken từ cookie
 */
@Component
public class CookieUtils {
    @Value("${jwt.auth.refresh_token.expires_in}")
    private int refreshTokenExpiresIn;
    public void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);             // Nếu production thì set là true
        cookie.setPath("/");                // Gửi kèm mọi request
        cookie.setMaxAge(refreshTokenExpiresIn);
        cookie.setAttribute("SameSite", "Strict"); // Chống CSRF
        response.addCookie(cookie);
    }
    public void clearRefreshTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
