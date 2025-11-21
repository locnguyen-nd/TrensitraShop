package com.trendistashop.config;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


/**
 * Class nay duoc danh dau la Configuration cau hinh swagger (them phu thuoc trong pom.xml)
 */
@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI openAPI() {
        OpenAPI openAPI = new OpenAPI()
                .info(new Info()
                        .title("TRENDISTA API")
                        .description("Trendista API Description")
                        .version("1.0")
                        .contact(new Contact().name("Lộc Nguyễn").email("locnguyen4.0@gmail.com")))
                .addServersItem(new Server().url("http://localhost:8080").description("Local Server"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));

        // Thêm auto refresh token
        openAPI.addExtension("x-springdoc-interceptor",
                Map.of("responseInterceptor",
                        Map.of("apply", """
                                function(response) {
                                  if (response.status === 401 && !response.request.url.includes('/refresh-token')) {
                                    return fetch('/api/v1/auth/refresh-token', {
                                      method: 'POST',
                                      credentials: 'include'
                                    }).then(res => res.ok 
                                      ? fetch(response.request.url, response.request)
                                      : (alert('Hết phiên đăng nhập!'), response)
                                    );
                                  }
                                  return response;
                                }
                                """)));
        return openAPI;
    }
}