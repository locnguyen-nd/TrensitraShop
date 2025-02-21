package com.trendistra.trendistashop.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trendistra.trendistashop.entities.user.Address;
import com.trendistra.trendistashop.entities.user.Payment;
import com.trendistra.trendistashop.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailDTO {
    private UUID id;
    private Date orderDate;
    @JsonProperty("address")
    private AddressDTO address;
    private DiscountApply discountApply;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private String shipmentNumber;
    private Date expectedDeliveryDate;
    @JsonProperty("orderItems")
    private List<OrderItemDTO> orderItemList;
    private Payment payment;
}
