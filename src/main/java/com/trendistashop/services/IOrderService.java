package com.trendistashop.services;

import com.trendistashop.dto.request.CheckoutRequest;
import com.trendistashop.dto.request.CreateOrder;
import com.trendistashop.dto.request.OrderRequest;
import com.trendistashop.dto.response.OrderDetailDTO;
import com.trendistashop.dto.response.OrderReview;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.enums.OrderStatus;
import com.trendistashop.exceptions.OrderCreationException;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface IOrderService {
    OrderReview previewOrderReview(CreateOrder request, Principal principal);
    OrderDetailDTO checkoutFromCart(CheckoutRequest request, Principal principal) throws OrderCreationException;
    TypeResponse<List<OrderDetailDTO>> getAllOrder(OrderStatus status, Principal principal);
    OrderDetailDTO retryPayment(UUID orderId, String paymentMethod) throws Exception;
    void cancelOrderByOrderId(UUID id, Principal principal);
    OrderDetailDTO updateOrderStatus(UUID orderId, OrderStatus status);
    void updateOrderStatusFromPayment(Long orderCode, String status, boolean cancel);
}
