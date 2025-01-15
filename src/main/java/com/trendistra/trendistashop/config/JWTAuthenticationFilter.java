package com.trendistra.trendistashop.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendistra.trendistashop.dto.response.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Bộ lọc xác thực JWT, kiểm tra tính hợp lệ của JWT trong yêu cầu và thiết lập thông tin xác thực cho người dùng.
 */
@Slf4j

public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JWTTokenHelper jwtTokenHelper;
    private ObjectMapper objectMapper = new ObjectMapper();
    /**
     * Khởi tạo bộ lọc xác thực JWT với JWTTokenHelper và UserDetailsService.
     *
     * @param jwtTokenHelper     Giúp tạo và kiểm tra JWT token.
     * @param userDetailsService Dịch vụ lấy thông tin người dùng từ cơ sở dữ liệu.
     */
    public JWTAuthenticationFilter(JWTTokenHelper jwtTokenHelper, UserDetailsService userDetailsService) {
        this.jwtTokenHelper = jwtTokenHelper;
        this.userDetailsService = userDetailsService;
    }
    /**
     * Kiểm tra JWT token trong yêu cầu HTTP, xác thực người dùng nếu token hợp lệ.
     *
     * @param request Yêu cầu HTTP.
     * @param response Phản hồi HTTP.
     * @param filterChain Chuỗi bộ lọc để tiếp tục xử lý yêu cầu.
     * @throws ServletException Nếu có lỗi trong quá trình xử lý yêu cầu.
     * @throws IOException Nếu có lỗi nhập/xuất trong quá trình xử lý yêu cầu.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (null == authHeader || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.clearContext();
        try {
            String authToken = jwtTokenHelper.getToken(request); // Lấy token
            if (null != authToken) {
                if (jwtTokenHelper.isTokenBlacklisted(authToken)) {
                    ErrorResponse errorResponse = ErrorResponse.builder()
                            .code(HttpStatus.UNAUTHORIZED.value())
                            .message("Token has been invalidated")
                            .build();
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType("application/json");
                    response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
                    return;
                }
                String userName = jwtTokenHelper.getUserNameFromToken(authToken); // Lấy tên người dùng từ token
                if (null != userName) {
                    try {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(userName); // Lấy thông tin người dùng
                        if (jwtTokenHelper.validateToken(authToken, userDetails)) { // Kiểm tra tính hợp lệ của token
                            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            authenticationToken.setDetails(new WebAuthenticationDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        }
                    } catch (UsernameNotFoundException e) {
                        log.warn("User not found: " + e.getMessage());
                        ErrorResponse errorResponse = ErrorResponse.builder()
                                .code(HttpStatus.UNAUTHORIZED.value())
                                .message("User not found")
                                .build();
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.setContentType("application/json");
                        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
                        return;
                    } catch (BadCredentialsException e) {
                        log.warn("Invalid credentials: " + e.getMessage());
                        ErrorResponse errorResponse = ErrorResponse.builder()
                                .code(HttpStatus.UNAUTHORIZED.value())
                                .message("Invalid credentials")
                                .build();
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.setContentType("application/json");
                        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
                        return;
                    } catch (Exception e) {
                        log.warn("Unexpected error during user authentication: " + e.getMessage());
                        ErrorResponse errorResponse = ErrorResponse.builder()
                                .code(HttpStatus.UNAUTHORIZED.value())
                                .message("Authentication failed")
                                .build();
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.setContentType("application/json");
                        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
                        return;
                    }
                }
            }
        } catch (ExpiredJwtException e) {
            log.warn("Token has expired: " + e.getMessage());
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .message("Token has expired")
                    .build();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            return;
        } catch (MalformedJwtException e) {
            log.warn("Token is malformed: " + e.getMessage());
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .message("Token is malformed")
                    .build();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            return;
        } catch (SignatureException e) {
            log.warn("Token signature is invalid: " + e.getMessage());
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .message("Token signature is invalid")
                    .build();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            return;
        } catch (JwtException e) {
            log.warn("JWT processing error: " + e.getMessage());
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .message("JWT processing error")
                    .build();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            return;
        } catch (Exception e) {
            log.warn("Unexpected error during authentication: " + e.getMessage());
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .message("Authentication failed")
                    .build();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            return;
        }
        filterChain.doFilter(request, response);
    }
}
