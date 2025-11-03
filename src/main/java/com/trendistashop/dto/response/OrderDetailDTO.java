package com.trendistashop.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trendistashop.entities.user.Payment;
import com.trendistashop.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailDTO {
    private UUID id;
    private LocalDateTime orderDate;
    @JsonProperty("address")
    private AddressDTO address;
    private DiscountApply discountApply;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private String shipmentNumber;
    private LocalDateTime expectedDeliveryDate;
    @JsonProperty("orderItems")
    private List<OrderItemDTO> orderItemList;
    private Payment payment;
}
