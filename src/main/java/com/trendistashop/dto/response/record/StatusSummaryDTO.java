package com.trendistashop.dto.response.record;

import com.trendistashop.enums.OrderStatus;

import java.math.BigDecimal;

/**
 *
 * @author Locnd
 */
public record StatusSummaryDTO(
        OrderStatus status,
        Long count,
        BigDecimal totalAmount
) {}