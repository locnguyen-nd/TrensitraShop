package com.trendistashop.dto.request;

import com.trendistashop.enums.PaymentMethod;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrder {
    @NotEmpty(message = "Order must have at least one item")
    private List<UUID> orderItems;
    @Size(max = 2, message = "Chỉ được áp dụng tối đa 2 mã giảm giá")
    private List<UUID> discountId;
    @NotNull(message = "Address is required")
    private UUID addressId;
}
