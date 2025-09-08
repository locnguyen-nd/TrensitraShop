package com.trendistashop.dto.payos;

import lombok.Data;

@Data
public class Webhook {
    private Long transactionId;
    private String paymentStatus;
}
