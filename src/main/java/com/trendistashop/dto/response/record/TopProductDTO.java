package com.trendistashop.dto.response.record;

import java.math.BigDecimal;
import java.util.UUID;

/**
 *
 * @author Locnd
 */
public record TopProductDTO(
        UUID productId,
        String productName,
        String thumbnail,
        Long quantitySold,
        BigDecimal revenue
) {}