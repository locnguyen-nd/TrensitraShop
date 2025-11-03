package com.trendistashop.services;

import com.trendistashop.dto.request.CheckoutRequest;
import com.trendistashop.dto.request.CreateOrder;
import com.trendistashop.dto.request.OrderRequest;
import com.trendistashop.dto.response.OrderDetailDTO;
import com.trendistashop.dto.response.OrderReview;
import com.trendistashop.dto.response.PageDTO;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.dto.response.record.RevenueReportDTO;
import com.trendistashop.dto.response.record.StatusSummaryDTO;
import com.trendistashop.dto.response.record.TopProductDTO;
import com.trendistashop.enums.OrderStatus;
import com.trendistashop.exceptions.OrderCreationException;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IOrderService {
    TypeResponse<OrderReview> previewOrderReview(CreateOrder request, Principal principal);
    TypeResponse<OrderDetailDTO> checkoutFromCart(CheckoutRequest request, Principal principal) ;
    TypeResponse<PageDTO<OrderDetailDTO>> getAllOrder(OrderStatus status, Principal principal, Pageable pageable) ;
    TypeResponse<Void> cancelOrderByOrderId(UUID id, Principal principal);
    TypeResponse<OrderDetailDTO> updateOrderStatus(UUID orderId, OrderStatus status);
    TypeResponse<Void> updateOrderStatusFromPayment(Long orderCode, String status, boolean cancel);
    TypeResponse<OrderDetailDTO> getOrderByOrderCode(Long orderCode, Principal principal);
    TypeResponse<OrderDetailDTO> retryPayment(UUID orderId, String paymentMethod) ;
    // Báo cáo
    TypeResponse<RevenueReportDTO> getRevenueReport(LocalDate from, LocalDate to);
    TypeResponse<List<StatusSummaryDTO>> getOrderStatusSummary(LocalDate from, LocalDate to);
    TypeResponse<List<TopProductDTO>> getTopSellingProducts(int limit, LocalDate from, LocalDate to);
    // Xuất file hóa đơn, đơn hàng
    TypeResponse<byte[]> exportInvoicePdf(UUID orderId);
    TypeResponse<byte[]> exportOrdersToExcel(OrderStatus status, LocalDate from, LocalDate to);
}
