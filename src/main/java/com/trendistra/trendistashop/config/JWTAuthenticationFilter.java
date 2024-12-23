package com.trendistra.trendistashop.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Bộ lọc xác thực JWT, kiểm tra tính hợp lệ của JWT trong yêu cầu và thiết lập thông tin xác thực cho người dùng.
 */
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JWTTokenHelper jwtTokenHelper;
    /**
     * Khởi tạo bộ lọc xác thực JWT với JWTTokenHelper và UserDetailsService.
     *
     * @param jwtTokenHelper Giúp tạo và kiểm tra JWT token.
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
        try {
            String authToken = jwtTokenHelper.getToken(request); // lấy token
            if ( null != authToken) {
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
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
