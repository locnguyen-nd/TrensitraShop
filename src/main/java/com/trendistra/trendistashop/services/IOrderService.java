package com.trendistra.trendistashop.services;

import com.trendistra.trendistashop.dto.request.OrderRequest;
import com.trendistra.trendistashop.dto.response.OrderDetailDTO;
import com.trendistra.trendistashop.entities.user.Order;
import com.trendistra.trendistashop.exceptions.OrderCreationException;
import jakarta.transaction.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface IOrderService {

    public OrderDetailDTO saveOrder(OrderRequest orderRequest, Principal principal) throws OrderCreationException;
    public OrderDetailDTO getOrderByOrderId(UUID orderId);
    public List<OrderDetailDTO> getAllOrder();

    List<OrderDetailDTO> getAllOrder(Principal principal);

    public void cancelOrderByOrderId(UUID id, Principal principal);
    public  OrderDetailDTO updateOrderByOrder(OrderRequest orderRequest, Principal principal);

}
