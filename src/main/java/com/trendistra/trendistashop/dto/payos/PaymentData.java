package com.trendistra.trendistashop.dto.payos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaymentData {
    private long orderCode;
    private int amount;
    private String description;
    private List<ItemData> items;
    private String cancelUrl;
    private String successUrl;

}
