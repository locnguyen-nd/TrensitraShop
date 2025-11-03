package com.trendistashop.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 *
 * @author Locnd
 */
@Data
@Component
@ConfigurationProperties(prefix = "app.shipping")
public class ShippingConfig {
    private String apiUrl;
    private String token;
    private Integer shopId;
}

