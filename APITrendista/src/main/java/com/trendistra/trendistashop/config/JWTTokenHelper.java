package com.trendistra.trendistashop.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

/**
 * JWTTokenHelper là một lớp hỗ trợ quản lý các thao tác liên quan đến JWT (JSON Web Token),
 * bao gồm việc cấu hình thông tin JWT, tạo, kiểm tra và phân tích token.
 * JWS with
 * Ký token: (phương thức generateToken) thông qua signWith.
 * Xác thực token: (phương thức getAllClaimsFromToken) thông qua parseClaimsJws.
 *
 * Các thành phần chính:
 * - `appName`: Tên của ứng dụng (lấy từ cấu hình).
 * - `secretKey`: Khóa bí mật dùng để ký và xác thực token.
 * - `expiresIn`: Thời gian sống của token (tính bằng giây).
 * - `header`: Tên header mặc định dùng để truyền token (mặc định là "Authorization").
 * - `startAuthHeader`: Chuỗi bắt đầu của token trong header (mặc định là "Bearer ").

 */
@Component
public class JWTTokenHelper {
    @Value("${jwt.auth.app}")
    private String appName;
    @Value("${jwt.auth.secret_key}")
    private String secretKey; // key
    @Value("${jwt.auth.expires_in}")
    private int expiresIn;// thời gian sống
    private String header = "Authorization";// Header mặc định chứa token
    private String startAuthHeader = "Bearer ";// Prefix mặc định của token trong header
    /**
     * Sinh JWT token cho người dùng.
     *
     * @param userName Tên người dùng để tạo token.
     * @return JWT token dưới dạng chuỗi.
     */
    public String generateToken(String userName) {
        return Jwts.builder()
                .issuer(appName)// tổ chức phát hành
                .subject(userName) // chủ thể đc phát hành
                .issuedAt(new Date()) // thời điểm phát hành
                .expiration(generateExpirationDate()) // thời điểm hết hạn
                .signWith(getSigningKey()) // chỉ máy chủ mới có thể ký và xác thực
                .compact(); // hoàn tất
    }

    /**
     * Lấy khóa bí mật dùng để ký JWT token.
     *
     * @return Key đối tượng khóa bí mật đã được mã hóa bằng thuật toán HMAC-SHA.
     */
    private Key getSigningKey() {
        byte[] keysBytes = Decoders.BASE64.decode(secretKey); // giải mã một chuỗi khóa từ định dạng base64
        return Keys.hmacShaKeyFor(keysBytes); // tạo một đối tượng Key được mã hóa bằng thuật toán HMAC-SHA
    }

    /**
     * Tạo ngày hết hạn cho token.
     *
     * @return Ngày hết hạn của token.
     */
    private Date generateExpirationDate() {
        return new Date(new Date().getTime() + expiresIn * 1000L); // thời điểm phát hành + với thời gian sống
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
     * Kiểm tra tính hợp lệ của token.
     *
     * @param token       Token cần kiểm tra.
     * @param userDetails Thông tin người dùng để so sánh với token.
     * @return true nếu token hợp lệ, ngược lại false.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        if (token == null || userDetails == null) {
            return false;
        }
        try {
            final String userName = getUserNameFromToken(token);
            // Kiểm tra các điều kiện: không null, khớp với userDetails và token chưa hết hạn thì true
            return userName != null
                    && userName.equals(userDetails.getUsername())
                    && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Kiểm tra xem token có hết hạn không.
     *
     * @param token Token cần kiểm tra.
     * @return true nếu token đã hết hạn, ngược lại false.
     */
    private boolean isTokenExpired(String token) {
        if (token == null || token.isEmpty()) {
            return true; // Nếu token null hoặc rỗng, coi như đã hết hạn
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
        } catch (Exception e) {
            expireDate = null;
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
        String userName;
        try {
            final Claims claims = this.getAllClaimsFromToken(authToken);
            userName = claims.getSubject();
        } catch (Exception e) {
            userName = null;
        }
        return userName;
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
}
