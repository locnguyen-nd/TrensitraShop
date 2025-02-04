package com.trendistra.trendistashop.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class VietQRConfig {
    @Value("${vietqr.client-id}")
    private String clientId;

    @Value("${vietqr.api-key}")
    private String apiKey;

    @Value("${vietqr.account-no}")
    private String accountNo;

    @Value("${vietqr.account-name}")
    private String accountName;

    @Value("${vietqr.bank-id}")
    private String bankId;

    public static final String VIETQR_API_URL = "https://api.vietqr.io/v2/generate";
    public static final int ORDER_TIMEOUT_MINUTES = 10;
}
