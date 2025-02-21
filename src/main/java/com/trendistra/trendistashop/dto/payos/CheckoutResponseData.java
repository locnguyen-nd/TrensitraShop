package com.trendistra.trendistashop.dto.payos;

import lombok.Data;

@Data
public class CheckoutResponseData {
    private String bin;
    private String accountNumber;
    private String accountName;
    private int amount;
    private String description;
    private long orderCode;
    private String currency;
    private String paymentLinkId;
    private String status;
    private String checkoutUrl;
    private String qrCode;
}
