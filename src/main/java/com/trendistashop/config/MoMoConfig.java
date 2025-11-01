package com.trendistashop.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author Locnd
 */
@Data
@Component
@ConfigurationProperties(prefix = "momo")
public class MoMoConfig {
    private String partnerCode;
    private String accessKey;
    private String secretKey;
    private String redirectUrl;
    private String ipnUrl;
    private String endpoint;
    private String requestType;
}
