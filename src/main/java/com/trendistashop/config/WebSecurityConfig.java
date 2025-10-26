package com.trendistashop.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendistashop.entities.user.UserEntity;
import com.trendistashop.services.impl.auth.AuthenticationService;
import com.trendistashop.services.impl.auth.PermissionService;
import com.trendistashop.utils.ResponseHelper;
import com.trendistashop.constants.ResponseMessage;
import com.trendistashop.dto.response.TypeResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private JWTTokenHelper jwtTokenHelper;
    @Autowired
    @Lazy
    private AuthenticationService authenticationService;
    @Value("${api.prefix}")
    private String prefix;
    @Value("${frontend.dev.url}")
    private String frontendDevUrl;
    @Value("${frontend.prod.url}")
    private String frontendProdUrl;
    @Value("${admin.dev.url}")
    private String adminDevUrl;
    @Value("${admin.prod.url}")
    private String adminProdUrl;

    @Value("${frontend.prod.url:http://localhost:4000}")
    private String oauth2RedirectUrl;

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
            "/api/v1/banner/**",
            "/api/v1/collections/**",
            "/api/v1/notifications/**",
            "/api/v1/test/**",
            "/api/v1/oauth2/**",
            "/api/v1/media/**",
            "/actuator/health/**",
            "/favicon.ico",
            "/*.html",
            "/error",
            "/.well-known/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        Map<String, Map<String, List<String>>> permissionMappings = permissionService.loadPermissions();

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint((request, response, authException) -> {
                            log.error("Authentication error for request {}: {}", request.getRequestURI(), authException.getMessage());
                            response.setStatus(401);
                            response.setContentType("application/json;charset=UTF-8");
                            TypeResponse<?> errorResponse = ResponseHelper.unauthorized(ResponseMessage.UNAUTHORIZED);
                            response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            log.error("Access denied for request {}: {}", request.getRequestURI(), accessDeniedException.getMessage());
                            response.setStatus(403);
                            response.setContentType("application/json;charset=UTF-8");
                            TypeResponse<?> errorResponse = ResponseHelper.forbidden(ResponseMessage.FORBIDDEN);
                            response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
                        })
                )

                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(publicApis).permitAll();
                    auth.requestMatchers("/oauth2/**", "/login/oauth2/code/**").permitAll();

                    permissionMappings.forEach((endPoint, methodMap) -> {
                        methodMap.forEach((httpMethod, permissions) -> {
                            String fullUrl = prefix + endPoint;
                            auth.requestMatchers(HttpMethod.valueOf(httpMethod), fullUrl)
                                    .hasAnyAuthority(permissions.toArray(new String[0]));
                        });
                    });

                    auth.anyRequest().authenticated();
                })

                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler((request, response, authentication) -> {
                            String token = jwtTokenHelper.getToken(request);
                            jwtTokenHelper.logout(token);
                        })
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.getWriter().write("Logged out successfully");
                        })
                )

                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(authorization ->
                                authorization.baseUri("/oauth2/authorization")
                        )
                        .redirectionEndpoint(redirection ->
                                redirection.baseUri("/login/oauth2/code/*")
                        )
                        .userInfoEndpoint(userInfo ->
                                userInfo.userService(oAuth2UserService())
                        )
                        .successHandler((request, response, authentication) -> {
                            log.info(" OAuth2 login successful");

                            try {
                                OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
                                String email = oauth2User.getAttribute("email");

                                if (email == null) {
                                    log.error("Email is null in OAuth2User");
                                    redirectToFrontendWithError(response, "Email not found");
                                    return;
                                }

                                log.info("Processing OAuth2 user: {}", email);

                                // Get or create user
                                UserEntity user = authenticationService.getUser(email)
                                        .orElseGet(() -> {
                                            log.info("Creating new Google user: {}", email);
                                            return authenticationService.createUserWithGoogle(oauth2User);
                                        });

                                // Generate JWT token
                                String token = jwtTokenHelper.generateToken(user.getUsername());
                                log.info("JWT token generated for user: {}", email);

                                // Redirect to frontend with token
                                String redirectUrl = String.format("%s?token=%s&email=%s",
                                        oauth2RedirectUrl,
                                        URLEncoder.encode(token, StandardCharsets.UTF_8),
                                        URLEncoder.encode(email, StandardCharsets.UTF_8)
                                );

                                log.info("Redirecting to: {}", redirectUrl);
                                response.sendRedirect(redirectUrl);

                            } catch (Exception e) {
                                log.error("Error in OAuth2 success handler", e);
                                redirectToFrontendWithError(response, "Authentication failed");
                            }
                        })
                        .failureHandler((request, response, exception) -> {
                            log.error(" OAuth2 login failed: {}", exception.getMessage(), exception);
                            redirectToFrontendWithError(response, exception.getMessage());
                        })
                )

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .addFilterBefore(
                        new JWTAuthenticationFilter(jwtTokenHelper, userDetailsService),
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    /**
     * Helper method to redirect to frontend with error
     */
    private void redirectToFrontendWithError(HttpServletResponse response, String errorMessage) {
        try {
            String errorUrl = String.format("%s?error=true&message=%s",
                    oauth2RedirectUrl,
                    URLEncoder.encode(errorMessage, StandardCharsets.UTF_8)
            );
            response.sendRedirect(errorUrl);
        } catch (Exception e) {
            log.error("Failed to redirect to error page", e);
        }
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        return request -> {
            log.info("Processing OAuth2 request for client: {}", request.getClientRegistration().getRegistrationId());
            try {
                OAuth2User oauth2User = delegate.loadUser(request);
                if (oauth2User == null) {
                    log.error("OAuth2User is null after loadUser");
                    throw new IllegalStateException("OAuth2User is null");
                }

                log.info("Loaded OAuth2User attributes: {}", oauth2User.getAttributes());
                String email = oauth2User.getAttribute("email");

                if (email == null) {
                    log.error("Email attribute is null in OAuth2User");
                    throw new IllegalArgumentException("Email not found in OAuth2User");
                }

                log.info("OAuth2 user loaded successfully: {}", email);
                return oauth2User;

            } catch (Exception e) {
                log.error("Error in oAuth2UserService: {}", e.getMessage(), e);
                throw e;
            }
        };
    }

    @Bean
    public WebSecurityCustomizer webSecurityConfigurer() {
        return (web) -> web.ignoring().requestMatchers(publicApis);
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                frontendDevUrl,
                frontendProdUrl,
                adminDevUrl,
                adminProdUrl
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}