
package com.trendistashop.controllers.user;
import com.trendistashop.config.MoMoConfig;
import com.trendistashop.dto.request.CheckoutRequest;
import com.trendistashop.dto.request.CreateOrder;
import com.trendistashop.dto.response.OrderDetailDTO;
import com.trendistashop.dto.response.OrderReview;
import com.trendistashop.dto.response.PageDTO;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.enums.OrderStatus;
import com.trendistashop.exceptions.OrderCreationException;
import com.trendistashop.services.IOrderService;
import com.trendistashop.utils.ResponseHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/order")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
@Tag(name = "Order API", description = "Quản lý đơn hàng & thanh toán")
public class OrderController {

    private final IOrderService orderService;
    @Autowired
    private MoMoConfig moMoConfig;
    @PostMapping("/preview")
    @Operation(summary = "Xem trước đơn hàng", description = "Tính tiền  khi áp 1 mã giảm giá")
    public ResponseEntity<TypeResponse<OrderReview>> previewOrder(
            @Valid @RequestBody CreateOrder request,
            Principal principal) {
        TypeResponse<OrderReview> response = orderService.previewOrderReview(request, principal);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/checkout")
    @Operation(summary = "Thanh toán từ giỏ hàng")
    public ResponseEntity<TypeResponse<OrderDetailDTO>> checkout(
            @Valid @RequestBody CheckoutRequest request,
            Principal principal)  {
        TypeResponse<OrderDetailDTO> response = orderService.checkoutFromCart(request, principal);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping
    @Operation(summary = "Lấy danh sách đơn hàng của người dùng")
    public ResponseEntity<TypeResponse<PageDTO<OrderDetailDTO>>> getUserOrders(
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Principal principal) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        TypeResponse<PageDTO<OrderDetailDTO>> response = orderService.getAllOrder(status, principal, pageable);
        return ResponseEntity.ok(response);
    }
    @GetMapping(value = "/{orderCode}")
    @Operation(summary = "Lấy chi tiết đơn hàng theo mã đơn hàng")
    public ResponseEntity<TypeResponse<OrderDetailDTO>> getOrderByOrderCode(
            @PathVariable Long orderCode,
            Principal principal) {
        TypeResponse<OrderDetailDTO> response = orderService.getOrderByOrderCode(orderCode, principal);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @PostMapping("/payment/retry/{paymentMethod}/{orderId}/")
    @Operation(summary = "Tạo lại link thanh toán lại với  các đơn hàng đã hủy hoặc thanh toán thất bại")
    public ResponseEntity<TypeResponse<OrderDetailDTO>> retryPayment(
            @PathVariable UUID orderId,
            @PathVariable(required = false) String paymentMethod) {
        TypeResponse<OrderDetailDTO> response = orderService.retryPayment(orderId, paymentMethod);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @PostMapping("/cancel/{orderId}")
    @Operation(summary = "Hủy đơn hàng")
    public ResponseEntity<TypeResponse<Void>> cancelOrder(@PathVariable UUID orderId, Principal principal) {
        TypeResponse<Void> response = orderService.cancelOrderByOrderId(orderId, principal);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/admin/{orderId}/status")
    @Operation(summary = "Admin cập nhật trạng thái")
    public ResponseEntity<TypeResponse<OrderDetailDTO>> updateStatus(
            @PathVariable UUID orderId,
            @RequestParam OrderStatus status) {
        TypeResponse<OrderDetailDTO> response = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/payment/callback")
    public ResponseEntity<TypeResponse<Void>> paymentCallback(
            @RequestParam("orderCode") Long orderCode,
            @RequestParam("status") String status,
            @RequestParam(name = "cancel", defaultValue = "false") boolean cancel) {
            TypeResponse<Void> response = orderService.updateOrderStatusFromPayment(orderCode, status, cancel);
           return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    /**
     * IPN - Server to Server callback từ MoMo
     * POST /api/v1/payment/callback/momo/ipn
     */
    @PostMapping("/payment/callback/momo/ipn")
    public ResponseEntity<Map<String, Object>> momoIpn(@RequestBody Map<String, String> params) {
        try {
            log.info("MoMo IPN received (POST): {}", params);

//            String rawHash =
//                    "partnerCode=" + params.get("partnerCode") +
//                            "&accessKey=" + moMoConfig.getAccessKey() +
//                            "&requestId=" + params.get("requestId") +
//                            "&amount=" + params.get("amount") +
//                            "&orderId=" + params.get("orderId") +
//                            "&transId=" + params.get("transId") +
//                            "&orderInfo=" + params.get("orderInfo") +
//                            "&orderType=" + params.get("orderType") +
//                            "&payType=" + params.getOrDefault("payType", "") +
//                            "&responseTime=" + params.get("responseTime") +
//                            "&message=" + params.get("message") +
//                            "&resultCode=" + params.get("resultCode") +
//                            "&extraData=" + params.getOrDefault("extraData", "");
//
//            String calculatedSignature = hmacSHA256(moMoConfig.getSecretKey(), rawHash);
//            String receivedSignature = params.get("signature");
//
//            if (!calculatedSignature.equals(receivedSignature)) {
//                log.warn("MoMo IPN: Invalid signature! Calculated: {}, Received: {}",
//                        calculatedSignature, receivedSignature);
//                return ResponseEntity.badRequest().body(Map.of(
//                        "partnerCode", moMoConfig.getPartnerCode(),
//                        "requestId", params.get("requestId"),
//                        "orderId", params.get("orderId"),
//                        "resultCode", 97,
//                        "message", "Invalid signature",
//                        "responseTime", System.currentTimeMillis()
//                ));
//            }

            String resultCode = params.get("resultCode");
            Long orderId = Long.parseLong(params.get("orderId"));
            boolean success = "0".equals(resultCode);

            orderService.updateOrderStatusFromPayment(
                    orderId,
                    success ? "PAID" : "CANCELLED",
                    !success
            );

            return ResponseEntity.ok(Map.of(
                    "partnerCode", moMoConfig.getPartnerCode(),
                    "requestId", params.get("requestId"),
                    "orderId", params.get("orderId"),
                    "resultCode", 0,
                    "message", "Success",
                    "responseTime", System.currentTimeMillis()
            ));

        } catch (Exception e) {
            log.error("MoMo IPN error", e);
            return ResponseEntity.status(500).body(Map.of(
                    "partnerCode", moMoConfig.getPartnerCode(),
                    "requestId", params.getOrDefault("requestId", ""),
                    "orderId", params.getOrDefault("orderId", ""),
                    "resultCode", 99,
                    "message", "Internal error: " + e.getMessage(),
                    "responseTime", System.currentTimeMillis()
            ));
        }
    }
    @GetMapping("/payment/callback/momo/ipn")
    public ResponseEntity<Map<String, Object>> momoIpnReturn(@RequestParam Map<String, String> params) {
        try {
            log.info("MoMo IPN received (GET): {}", params);

            // === 1. Tính chữ ký - ĐÚNG THỨ TỰ ===
//            String rawHash =
//                    "partnerCode=" + params.get("partnerCode") +
//                            "&accessKey=" + moMoConfig.getAccessKey() +
//                            "&requestId=" + params.get("requestId") +
//                            "&amount=" + params.get("amount") +
//                            "&orderId=" + params.get("orderId") +
//                            "&transId=" + params.get("transId") +
//                            "&orderInfo=" + params.get("orderInfo") +
//                            "&orderType=" + params.get("orderType") +
//                            "&payType=" + params.getOrDefault("payType", "") +
//                            "&responseTime=" + params.get("responseTime") +
//                            "&message=" + params.get("message") +
//                            "&resultCode=" + params.get("resultCode") +
//                            "&extraData=" + params.getOrDefault("extraData", "");
//
//            String calculatedSignature = hmacSHA256(moMoConfig.getSecretKey(), rawHash);
//            String receivedSignature = params.get("signature");
//
//            log.info("Calculated signature: {}", calculatedSignature);
//            log.info("Received signature: {}", receivedSignature);
//
//            if (!calculatedSignature.equals(receivedSignature)) {
//                log.warn("MoMo IPN: Invalid signature! Calculated: {}, Received: {}",
//                        calculatedSignature, receivedSignature);
//                return ResponseEntity.badRequest().body(Map.of(
//                        "resultCode", "97",
//                        "message", "Invalid signature"
//                ));
//            }

            // === 2. Xử lý thanh toán ===
            String resultCode = params.get("resultCode");
            String orderIdStr = params.get("orderId");
            boolean success = "0".equals(resultCode);

            if (orderIdStr != null && !orderIdStr.isEmpty()) {
                Long orderId = Long.parseLong(orderIdStr);
                orderService.updateOrderStatusFromPayment(orderId, success ? "PAID" : "CANCELLED", !success);
            }

            return ResponseEntity.ok(Map.of("resultCode", "0", "message", "Success"));

        } catch (Exception e) {
            log.error("MoMo IPN error", e);
            return ResponseEntity.status(500).body(Map.of("resultCode", "99", "message", "System error"));
        }
    }
    private String hmacSHA256(String key, String data) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec spec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(spec);
        byte[] bytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(bytes);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}