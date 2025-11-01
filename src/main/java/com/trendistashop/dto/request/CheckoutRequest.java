package com.trendistashop.dto.request;

import com.trendistashop.enums.PaymentMethod;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author Locnd
 */
@Data
public class CheckoutRequest {
    @NotEmpty
    private List<UUID> cartItemIds;
    @NotNull
    private UUID addressId;
    @NotNull
    private PaymentMethod paymentMethod;
    private String note;
    private List<UUID> discountId;
}
