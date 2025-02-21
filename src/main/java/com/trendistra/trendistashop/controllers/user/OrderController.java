package com.trendistra.trendistashop.controllers.user;

import com.trendistra.trendistashop.dto.payos.Webhook;
import com.trendistra.trendistashop.dto.payos.WebhookData;
import com.trendistra.trendistashop.dto.request.CreateOrder;
import com.trendistra.trendistashop.dto.request.OrderRequest;
import com.trendistra.trendistashop.dto.response.OrderDetailDTO;
import com.trendistra.trendistashop.entities.user.Order;
import com.trendistra.trendistashop.enums.OrderStatus;
import com.trendistra.trendistashop.enums.PaymentMethod;
import com.trendistra.trendistashop.exceptions.OrderCreationException;
import com.trendistra.trendistashop.services.IOrderService;
import com.trendistra.trendistashop.services.impl.order.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/order")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Order")
public class OrderController {
    private final IOrderService orderService;

    @GetMapping("/user")
    public ResponseEntity<List<OrderDetailDTO>> getOrderUser (OrderStatus orderStatus, Principal principal) {
        List<OrderDetailDTO> orders = orderService.getAllOrder(orderStatus,principal);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<OrderDetailDTO> createOrder (@Valid @RequestBody CreateOrder createOrder , Principal principal) throws OrderCreationException {
        OrderDetailDTO order = orderService.createOrder(createOrder, principal);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
    @PostMapping("/checkout")
    public ResponseEntity<OrderDetailDTO> createOrder (@Valid  @RequestBody OrderRequest orderRequest , Principal principal) throws OrderCreationException {
        OrderDetailDTO order = orderService.checkoutOrder(orderRequest, principal);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PostMapping("/{orderId}/retry-payment")
    public ResponseEntity<OrderDetailDTO> retryPayment(@PathVariable UUID orderId, String paymentMethod) throws Exception {
        OrderDetailDTO order = orderService.retryPayment(orderId, paymentMethod);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
    @PutMapping("/cancel/{id}")
    public ResponseEntity<String> cancelOrder(@PathVariable UUID id, Principal principal){
        orderService.cancelOrderByOrderId(id,principal);
        return ResponseEntity.ok("Your order has been canceled successfully.");
    }
    @PutMapping("/{orderId}/update-status")
    public ResponseEntity<OrderDetailDTO> updateOrderStatus(@PathVariable UUID orderId, OrderStatus orderStatus) {
        OrderDetailDTO order = orderService.updateOrderStatus(orderId, orderStatus);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
    @GetMapping("/payment/success")
    public ResponseEntity<String> paymentSuccess(
            @RequestParam("orderCode") Long transactionId,
            @RequestParam("status") String paymentStatus) {
        return processPayment(transactionId, paymentStatus, false);
    }

    @GetMapping("/payment/cancel")
    public ResponseEntity<String> paymentCancel(
            @RequestParam("orderCode") Long transactionId) {
        return processPayment(transactionId, OrderStatus.CANCELLED.name(), true);
    }

    private ResponseEntity<String> processPayment(Long transactionId, String status, boolean isCancelled) {
        try {
            orderService.updateOrderStatusFromPayment(transactionId, status);
            return ResponseEntity.ok(isCancelled ? "Giao dịch đã bị hủy" : "Thanh toán cập nhật thành công");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi cập nhật trạng thái: " + e.getMessage());
        }
    }
}
