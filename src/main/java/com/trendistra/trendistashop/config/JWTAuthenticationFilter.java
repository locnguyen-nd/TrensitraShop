package com.trendistra.trendistashop.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendistra.trendistashop.dto.response.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JWTTokenHelper jwtTokenHelper;
    private  ObjectMapper objectMapper;
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
            String authToken = jwtTokenHelper.getToken(request); // lấy token
            if ( null != authToken) {
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
                String userName = jwtTokenHelper.getUserNameFromToken(authToken); // get userName with token
                if (null != userName) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(userName);// get userDetail
//                    System.out.println(userDetails.getUsername());
                    if (jwtTokenHelper.validateToken(authToken, userDetails)) { // check token validate ==> pass
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .message("Authentication  failed")
                    .build();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            return;
        }
        filterChain.doFilter(request, response);
    }
}
