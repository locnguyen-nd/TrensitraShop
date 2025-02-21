package com.trendistra.trendistashop.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class PayOsConfig {
    @Value("${PAYOS_CLIENT_ID}")
    private String clientId;

    @Value("${PAYOS_API_KEY}")
    private String apiKey;

    @Value("${PAYOS_CHECKSUM_KEY}")
    private String checksumKey;
    public static final int ORDER_TIMEOUT_MINUTES = 10;
    @Value("${PAYOS_RETURN_URL}")
    private String returnUrl;
    @Value("${PAYOS_CANCEL_URL}")
    private String cancelUrl;
}
