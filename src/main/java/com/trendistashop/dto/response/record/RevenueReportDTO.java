package com.trendistashop.dto.response.record;

import java.math.BigDecimal;

/**
 *
 * @author Locnd
 */
public record RevenueReportDTO(
        BigDecimal totalRevenue,
        BigDecimal totalRefund,
        BigDecimal netRevenue,
        Long totalOrders,
        Long completedOrders,
        Long cancelledOrders,
        String currency
) {}
