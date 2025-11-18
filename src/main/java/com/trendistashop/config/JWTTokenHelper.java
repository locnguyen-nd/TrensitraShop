package com.trendistashop.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * JWTTokenHelper là một lớp hỗ trợ quản lý các thao tác liên quan đến JWT (JSON Web Token),
 * bao gồm việc cấu hình thông tin JWT, tạo, kiểm tra và phân tích token.
 * JWS with
 * Ký token: (phương thức generateToken) thông qua signWith.
 * Xác thực token: (phương thức getAllClaimsFromToken) thông qua parseClaimsJws.
 * Thêm type vào claims để phân biệt các loại token
 */
@Component
@Slf4j
public class JWTTokenHelper {
    @Value("${jwt.auth.app}")
    private String appName;
    @Value("${jwt.auth.secret_key}")
    private String secretKey;
    @Value("${jwt.auth.expires_in}")
    private int expiresIn;
    @Value("${jwt.auth.refresh_token.expires_in}")
    private int refreshTokenExpiresIn;
    @Value("${jwt.auth.verification.expires_in}")
    private int verificationTokenExpiresIn;
    private String header = "Authorization";
    private String startAuthHeader = "Bearer ";
    private Set<String> backListedToken = Collections.synchronizedSet(new HashSet<>());
    private static final String TOKEN_TYPE_CLAIM = "type";
    private static final String ACCESS_TOKEN_TYPE = "access";
    private static final String REFRESH_TOKEN_TYPE = "refresh";
    private static final String VERIFICATION_TOKEN_TYPE = "verification";
    /**
     * Sinh JWT token cho người dùng.
     *
     * @param userName Tên người dùng để tạo token.
     * @return JWT token dưới dạng chuỗi.
     */
    public String generateToken(String userName) {
        return Jwts.builder()
                .issuer(appName)
                .subject(userName)
                .issuedAt(new Date())
                .expiration(generateExpirationDate())
                .claim(TOKEN_TYPE_CLAIM, ACCESS_TOKEN_TYPE)
                .signWith(getSigningKey())
                .compact();
    }
    /**
     * Lấy khóa bí mật dùng để ký JWT token.
     *
     * @return Key đối tượng khóa bí mật đã được mã hóa bằng thuật toán HMAC-SHA.
     */
    private Key getSigningKey() {
        byte[] keysBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keysBytes);
    }

    /**
     * Tạo ngày hết hạn cho token.
     *
     * @return Ngày hết hạn của token.
     */
    private Date generateExpirationDate() {
        return new Date(new Date().getTime() + expiresIn * 1000L);
    }

    /**
     * Lấy token từ header của yêu cầu HTTP.
     *
     * @param request Đối tượng HttpServletRequest.
     * @return Token nếu có trong header, nếu không thì trả về null.
     */
    public String getToken(HttpServletRequest request) {
        String authHeader = getAuthHeaderFromHeader(request);
        if (null != authHeader && authHeader.startsWith(startAuthHeader)) {
            return authHeader.substring(7);
        }
        return authHeader;
    }
    /**
     * Kiểm tra xem token có trong blacklist không
     */
    public boolean isTokenBlacklisted(String token) {
        return backListedToken.contains(token);
    }

    /**
     * Kiểm tra tính hợp lệ của token.
     *
     * @param token       Token cần kiểm tra.
     * @param userDetails Thông tin người dùng để so sánh với token.
     * @return true nếu token hợp lệ, ngược lại false.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        if (token == null || userDetails == null || isTokenBlacklisted(token)) {
            log.warn("Token cannot validate because token null or token has logout");
            return false;
        }
        try {
            final String userName = getUserNameFromToken(token).toLowerCase();
            return userName != null
                    && userName.equals(userDetails.getUsername())
                    && !isTokenExpired(token);
        } catch (ExpiredJwtException e) {
            log.warn("Token has expired: " + e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("Token is malformed: " + e.getMessage());
        } catch (SignatureException e) {
            log.warn("Token signature is invalid: " + e.getMessage());
        } catch (JwtException e) {
            log.warn("JWT processing error: " + e.getMessage());
        } catch (Exception e) {
            log.warn("Unexpected error during token validation: " + e.getMessage());
        }
        return false;
    }

    /**
     * Kiểm tra xem token có hết hạn không.
     *
     * @param token Token cần kiểm tra.
     * @return true nếu token đã hết hạn, ngược lại false.
     */
    private boolean isTokenExpired(String token) {
        if (token == null || token.isEmpty()) {
            log.warn("Token đã hết hạn");
            return true;
        }
        Date expireDate = getExpireDateToken(token);
        return expireDate.before(new Date());
    }

    /**
     * Lấy ngày hết hạn của token.
     *
     * @param token Token cần lấy thông tin hết hạn.
     * @return Ngày hết hạn của token.
     */
    private Date getExpireDateToken(String token) {
        Date expireDate;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            expireDate = claims.getExpiration();
        } catch (ExpiredJwtException e) {
            expireDate = null;
            log.warn("Token has expired: " + e.getMessage());
        } catch (MalformedJwtException e) {
            expireDate = null;
            log.warn("Token is malformed: " + e.getMessage());
        } catch (SignatureException e) {
            expireDate = null;
            log.warn("Token signature is invalid: " + e.getMessage());
        } catch (JwtException e) {
            expireDate = null;
            log.warn("JWT processing error: " + e.getMessage());
        } catch (Exception e) {
            expireDate = null;
            log.warn("Unexpected error during token expiration retrieval: " + e.getMessage());
        }
        return expireDate;
    }

    /**
     * Lấy giá trị của header "Authorization" từ yêu cầu HTTP.
     *
     * @param request Đối tượng HttpServletRequest.
     * @return Giá trị của header "Authorization".
     */
    private String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader(header);
    }

    /**
     * Lấy tên người dùng từ token.
     *
     * @param authToken JWT token cần lấy tên người dùng.
     * @return Tên người dùng được lưu trong token.
     */
    public String getUserNameFromToken(String authToken) {
        try {
            Claims claims = getAllClaimsFromToken(authToken);
            return claims != null ? claims.getSubject() : null;
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * Lấy tất cả các claims từ token.
     *
     * @param token JWT token cần lấy thông tin claims.
     * @return Claims chứa thông tin từ token.
     */
    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * Tạo refresh token cho người dùng.
     *
     * @param userName Tên người dùng
     * @return Refresh token
     */
    public String generateRefreshToken(String userName) {
        return Jwts.builder()
                .issuer(appName)
                .subject(userName)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + refreshTokenExpiresIn * 1000L))
                .claim(TOKEN_TYPE_CLAIM, REFRESH_TOKEN_TYPE)
                .signWith(getSigningKey())
                .compact();
    }
    /**
     * Kiểm tra token có phải là refresh token không
     * CHỈ chấp nhận token mới có claim type=refresh
     */
    public boolean isRefreshToken(String token) {
        if (token == null || token.isBlank()) return false;

        try {
            Claims claims = getAllClaimsFromToken(token);
            if (claims == null) return false;

            String type = claims.get(TOKEN_TYPE_CLAIM, String.class);
            return REFRESH_TOKEN_TYPE.equals(type);

        } catch (Exception e) {
            log.error("Token is not a valid refresh token: {}", e.getMessage());
            return false;
        }
    }
    /**
     * Xử lý logout bằng cách thêm token vào blacklist
     */
    public void logout(String token) {
        if (token != null && !token.isEmpty()) {
            backListedToken.add(token);
        }
        log.info("logout successfully");
    }

    /**
     * Tạo token mới từ refresh token
     *
     * @param refreshToken Refresh token hiện tại
     * @return Token mới hoặc null nếu refresh token không hợp lệ
     */
    public String createAccessTokenFromRefreshToken(String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new IllegalArgumentException("Refresh token is required");
        }

        try {
            Claims claims = getAllClaimsFromToken(refreshToken);
            if (claims == null) {
                throw new JwtException("Invalid refresh token claims");
            }
            String username = claims.getSubject();
            if (username == null) {
                throw new JwtException("Refresh token has no subject");
            }
            if (!isRefreshToken(refreshToken)) {
                throw new JwtException("Token is not a valid refresh token");
            }
            if (isTokenExpired(refreshToken)) {
                throw new ExpiredJwtException(null, claims, "Refresh token has expired");
            }
            if (isTokenBlacklisted(refreshToken)) {
                throw new JwtException("Refresh token has been revoked");
            }

            log.info("Successfully refreshed token for user: {}", username);
            return generateToken(username);

        } catch (ExpiredJwtException e) {
            log.warn("Refresh token expired for user: {}", e.getClaims().getSubject());
            throw e;
        } catch (Exception e) {
            log.warn("Failed to refresh token: {}", e.getMessage());
            throw new JwtException("Invalid or malformed refresh token", e);
        }
    }
    // Thêm phương thức định kỳ xóa các token hết hạn khỏi blacklist
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000) // Chạy mỗi 24 giờ
    public void cleanupBlacklist() {
        backListedToken.removeIf(token -> {
            try {
                log.info("Xóa blacklist token done !");
                return isTokenExpired(token);
            } catch (ExpiredJwtException e) {
                log.warn("Token expired during blacklist cleanup: " + e.getMessage());
                return true;
            } catch (MalformedJwtException e) {
                log.warn("Malformed token during blacklist cleanup: " + e.getMessage());
                return true;
            } catch (SignatureException e) {
                log.warn("Signature invalid during blacklist cleanup: " + e.getMessage());
                return true;
            } catch (JwtException e) {
                log.warn("JWT error during blacklist cleanup: " + e.getMessage());
                return true;
            } catch (Exception e) {
                log.warn("Unexpected error during blacklist cleanup: " + e.getMessage());
                return true;
            }
        });
    }

    /**
     * Sinh verification token cho người dùng.
     *
     * @param userName Email người dùng để tạo token.
     * @return JWT verification token.
     */
    public String generateVerificationToken(String userName) {
        return Jwts.builder()
                .issuer(appName)
                .subject(userName)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + verificationTokenExpiresIn * 1000L))
                .claim(TOKEN_TYPE_CLAIM, VERIFICATION_TOKEN_TYPE)
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Kiểm tra tính hợp lệ của verification token.
     *
     * @param token Token cần kiểm tra.
     * @return true nếu token hợp lệ và chưa hết hạn.
     */
    public boolean validateVerificationToken(String token) {
        if (token == null || token.isEmpty()) {
            log.warn("Verification token is null or empty");
            return false;
        }

        try {
            Claims claims = getAllClaimsFromToken(token);
            return claims != null && VERIFICATION_TOKEN_TYPE.equals(claims.get(TOKEN_TYPE_CLAIM, String.class)) &&
                   !isTokenExpired(token);
        } catch (Exception e) {
            log.warn("Invalid verification token: " + e.getMessage());
            return false;
        }
    }
}
