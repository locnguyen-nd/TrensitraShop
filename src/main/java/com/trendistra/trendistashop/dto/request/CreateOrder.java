package com.trendistra.trendistashop.dto.request;

import com.trendistra.trendistashop.dto.response.CartItemDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Order items cannot be null")
    @NotEmpty(message = "Order must have at least one item")
    private List<UUID> orderItems;
}
