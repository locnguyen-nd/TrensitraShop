package com.trendistashop.services;

import com.trendistashop.dto.request.CreateOrder;
import com.trendistashop.dto.request.OrderRequest;
import com.trendistashop.dto.response.OrderDetailDTO;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.enums.OrderStatus;
import com.trendistashop.exceptions.OrderCreationException;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface IOrderService {

    public OrderDetailDTO checkoutOrder(OrderRequest orderRequest, Principal principal) throws OrderCreationException;
    public OrderDetailDTO createOrder (CreateOrder createOrder, Principal principal) throws OrderCreationException;
    TypeResponse<List<OrderDetailDTO>> getAllOrder(OrderStatus orderStatus, Principal principal);

    public void cancelOrderByOrderId(UUID id, Principal principal);
    public OrderDetailDTO retryPayment(UUID orderId, String paymentMethod) throws Exception;
    public OrderDetailDTO updateOrderStatus(UUID orderId, OrderStatus orderStatus);
    public void updateOrderStatusFromPayment(Long transactionId, String newPaymentStatus) throws Exception;

}
