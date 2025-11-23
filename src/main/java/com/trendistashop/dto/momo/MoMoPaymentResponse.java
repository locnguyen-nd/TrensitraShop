package com.trendistashop.dto.momo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Locnd
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoMoPaymentResponse {
    private Long orderCode;
    private Integer amount;
    private String status;
    private String checkoutUrl;
    private String qrCode;
}
