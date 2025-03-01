package com.trendistra.trendistashop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusChangedEvent {
    private UUID orderId;
    private UUID userId;
    private String orderNumber;
    private String oldStatus;
    private String newStatus;
}
