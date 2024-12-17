package com.trendistra.trendistashop.config;

import com.trendistra.trendistashop.entities.user.PermissionEntity;
import com.trendistra.trendistashop.repositories.auth.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private JWTTokenHelper jwtTokenHelper;
    @Value("${api.prefix}")
    private String prefix ;

    /**
     * Danh sach các URL không cần phân quyền
     */
    private final String[] publicApis = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/api/v1/auth/**",
            "/api/v1/categories/**",
            "/api/v1/colors/**",
            "/api/v1/discounts/**",
            "/api/v1/products/**",
            "/api/v1/sizes/**",
            "/api/v1/role/**",
            "/api/v1/permissions/**",
    };
    /**
     * Cấu hình bảo mật cho ứng dụng, xác định cách thức xử lý các yêu cầu HTTP.
     * - Tắt CSRF.
     * - Cấu hình quyền truy cập cho từng URL.
     * - Cấu hình chính sách session.
     * - Thêm bộ lọc JWT để xác thực người dùng.
     *
     * @param http Đối tượng HttpSecurity để cấu hình bảo mật.
     * @return SecurityFilterChain đối tượng cấu hình bảo mật hoàn chỉnh.
     * @throws Exception nếu có lỗi khi cấu hình bảo mật.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        List<PermissionEntity> permissionEntities = permissionRepository.findAll();
        http
                .csrf(AbstractHttpConfigurer :: disable)
                .authenticationManager(authenticationManager())
                // Ánh xạ quyền dựa trên permissions từ cơ sở dữ liệu
                .authorizeHttpRequests(auth -> {
                    permissionEntities.forEach(permissionEntity -> {
                        try {
                            HttpMethod httpMethod = HttpMethod.valueOf(permissionEntity.getMethod());
                            System.out.println(httpMethod);
                            String fullUrl = prefix + permissionEntity.getEndPoint();
                            System.out.println(fullUrl);
                            System.out.println(permissionEntity.getName());
                            auth.requestMatchers(httpMethod, fullUrl)
                                    .hasAuthority("USER");
                            System.out.println("Role" + getRoleInPermission(permissionEntity.getName()));
                        } catch (IllegalArgumentException e) {
                            // Ghi log nếu method không hợp lệ
                            System.err.println("Invalid HTTP method: " + permissionEntity.getMethod());
                        }
                    });
                    // Các request khác cần xác thực
                    auth.anyRequest().authenticated();
                })
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .addFilterBefore(new JWTAuthenticationFilter(jwtTokenHelper, userDetailsService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    private String getRoleInPermission(String name) {
        int spaceIndex = name.indexOf(" ");
        if (spaceIndex != -1) {
            return name.substring(0, spaceIndex);
        }
        return name;
    }

    /**
     * Cấu hình WebSecurity để bỏ qua bảo mật cho các API công khai.
     *
     * @return WebSecurityCustomizer cho phép bỏ qua bảo mật cho các API công khai.
     */
    @Bean
    public WebSecurityCustomizer webSecurityConfigurer() {
        return (web) -> web.ignoring().requestMatchers(publicApis);
    }

    /**
     * Cấu hình AuthenticationManager, sử dụng DaoAuthenticationProvider để xác thực người dùng.
     *
     * @return AuthenticationManager để xác thực người dùng.
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(daoAuthenticationProvider);
    }
    /**
     * Tạo một PasswordEncoder để mã hóa mật khẩu người dùng.
     *
     * @return PasswordEncoder sử dụng mã hóa mật khẩu.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
