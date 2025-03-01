package com.trendistra.trendistashop.config;


import com.trendistra.trendistashop.services.impl.auth.PermissionService;
import jakarta.servlet.http.HttpServletResponse;
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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private JWTTokenHelper jwtTokenHelper;
    @Value("${api.prefix}")
    private String prefix ;
    @Value("${frontend.url}")
    private String frontendUrl;

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
            "/api/v1/home/**",
            "/api/v1/collections/**",
            "/api/v1/notifications/**",
            "/oauth2/**"
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
        Map<String, Map<String, List<String>>> permissionMappings = permissionService.loadPermissions();
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(
                        corsConfigurationSource()
                ))
                .authenticationManager(authenticationManager())
                // Ánh xạ quyền dựa trên permissions từ cơ sở dữ liệu
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(publicApis);
                    permissionMappings.forEach((endPoint , methodMap) -> {
                       methodMap.forEach((httpMethod, permissions) -> {
                           String fullUrl = prefix + endPoint;
//                         System.out.println("Configuring access for URL: " + fullUrl + " with method: " + httpMethod + " and permissions: " + permissions);
                           auth.requestMatchers(HttpMethod.valueOf(httpMethod), fullUrl)
                                   .hasAnyAuthority(permissions.toArray(new  String[0]));
                       });
                    });
                    auth.requestMatchers(
                            "/",
                            "/login/**",
                            "/oauth2/**",
                            "/api/v1/chat/**",
                            "/oauth2/callback**",
                            "/error").permitAll();
                    auth.anyRequest().authenticated();
                })
                .logout(logout -> {
                    logout
                            .logoutUrl("/api/v1/auth/logout")
                            .addLogoutHandler((request, response, authentication) -> {
                                // Lấy token từ request và thêm vào blacklist
                                String token = jwtTokenHelper.getToken(request);
                                jwtTokenHelper.logout(token);
                            })
                            .clearAuthentication(true)
                            .invalidateHttpSession(true)
                            .deleteCookies("JSESSIONID")
                            .logoutSuccessHandler((request, response, authentication) -> {
                                response.setStatus(HttpServletResponse.SC_OK);
                                response.getWriter().write("Logged out successfully");
                            });
                })
//                .oauth2Login(oauth2 -> oauth2
//                        .defaultSuccessUrl("/oauth2/success", true)
//                        .userInfoEndpoint(userInfo -> userInfo
//                                .userService(oAuth2UserService())
//                        )
//                )
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .addFilterBefore(new JWTAuthenticationFilter(jwtTokenHelper, userDetailsService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

        return request -> {
            OAuth2User user = delegate.loadUser(request);
            // Có thể thêm xử lý custom cho user ở đây
            return user;
        };
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
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(frontendUrl));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
