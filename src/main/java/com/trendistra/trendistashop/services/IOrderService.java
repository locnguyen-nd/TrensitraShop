package com.trendistra.trendistashop.services;

import com.trendistra.trendistashop.dto.request.CreateOrder;
import com.trendistra.trendistashop.dto.request.OrderRequest;
import com.trendistra.trendistashop.dto.response.OrderDetailDTO;
import com.trendistra.trendistashop.entities.user.Order;
import com.trendistra.trendistashop.enums.OrderStatus;
import com.trendistra.trendistashop.enums.PaymentMethod;
import com.trendistra.trendistashop.exceptions.OrderCreationException;
import jakarta.transaction.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface IOrderService {

    public OrderDetailDTO checkoutOrder(OrderRequest orderRequest, Principal principal) throws OrderCreationException;
    public OrderDetailDTO createOrder (CreateOrder createOrder, Principal principal) throws OrderCreationException;
    List<OrderDetailDTO> getAllOrder(OrderStatus orderStatus, Principal principal);

    public void cancelOrderByOrderId(UUID id, Principal principal);
    public OrderDetailDTO retryPayment(UUID orderId, String paymentMethod) throws Exception;
    public  OrderDetailDTO updateOrderStatus(UUID orderId, OrderStatus orderStatus);
    public void updateOrderStatusFromPayment(Long transactionId, String newPaymentStatus) throws Exception;

}
