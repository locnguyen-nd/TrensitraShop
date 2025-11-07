package com.trendistashop.config;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import vn.payos.PayOS;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@Log4j2
public class WebConfig {
    private final PayOsConfig payOsConfig;

    public WebConfig(PayOsConfig payOsConfig) {
        this.payOsConfig = payOsConfig;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /**
     * RestTemplate cho internal services (có interceptor)
     */
    @Bean(name = "restTemplate")
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(
                MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_OCTET_STREAM
        ));
        restTemplate.getMessageConverters().add(0, converter);
        restTemplate.setInterceptors(Collections.singletonList(new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                                ClientHttpRequestExecution execution) throws IOException, java.io.IOException {
                logRequest(request, body);
                ClientHttpResponse response = execution.execute(request, body);
                logResponse(response);
                return response;
            }
        }));
        return restTemplate;
    }
    /**
     * RestTemplate cho external API (KHÔNG có interceptor)
     * Dùng riêng cho Gemini, payment gateway, etc.
     */

    @Bean(name = "externalRestTemplate")
    public RestTemplate externalRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(10000);
        factory.setReadTimeout(30000);

        RestTemplate restTemplate = new RestTemplate(factory);

        // Đảm bảo có Jackson converter
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        restTemplate.getMessageConverters().add(0, converter);

        return restTemplate;
    }
    private void logRequest(HttpRequest request, byte[] body) {
        log.debug("Request URI: {}", request.getURI());
        log.debug("Request Method: {}", request.getMethod());
        log.debug("Request Headers: {}", request.getHeaders());
        log.debug("Request Body: {}", new String(body, StandardCharsets.UTF_8));
    }

    private void logResponse(ClientHttpResponse response) throws java.io.IOException {
        log.debug("Response Status: {}", response.getStatusCode());
        log.debug("Response Headers: {}", response.getHeaders());
    }
    @Bean
    public PayOS payOS() {
        return new PayOS(payOsConfig.getClientId(), payOsConfig.getApiKey(), payOsConfig.getChecksumKey());
    }

}
