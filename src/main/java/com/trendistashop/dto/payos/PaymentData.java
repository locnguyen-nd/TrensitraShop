package com.trendistashop.dto.payos;

import lombok.Builder;
import lombok.Data;
import vn.payos.model.v2.paymentRequests.PaymentLinkItem;

import java.util.List;

@Data
@Builder
public class PaymentData {
    private long orderCode;
    private int amount;
    private String buyerEmail;
    private String description;
    private List<PaymentLinkItem> items;
    private String returnUrl;
    private String cancelUrl;
}
