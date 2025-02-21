package com.trendistra.trendistashop.dto.payos;

import lombok.Data;
import vn.payos.type.PaymentData;
import vn.payos.type.Transaction;

import java.util.List;

@Data
public class PaymentLinkData {
    private String id;
    private long orderCode;
    private int amount;
    private int amountPaid;
    private int amountRemaining;
    private String status;
    private String createdAt;
    private List<Transaction> transactions;
    private String cancellationReason;
    private String canceledAt;
}
