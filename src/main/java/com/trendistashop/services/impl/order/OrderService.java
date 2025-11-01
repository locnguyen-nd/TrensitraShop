// src/main/java/com/trendistashop/services/impl/order/OrderService.java
package com.trendistashop.services.impl.order;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendistashop.config.MoMoConfig;
import com.trendistashop.config.PayOsConfig;
import com.trendistashop.config.VietQRConfig;
import com.trendistashop.dto.payos.MoMoPaymentResponse;
import com.trendistashop.dto.request.CheckoutRequest;
import com.trendistashop.dto.request.CreateOrder;
import com.trendistashop.dto.response.*;
import com.trendistashop.entities.product.Discount;
import com.trendistashop.entities.product.ProductVariant;
import com.trendistashop.entities.user.*;
import com.trendistashop.enums.*;
import com.trendistashop.exceptions.OrderCreationException;
import com.trendistashop.exceptions.ResourceNotFoundEx;
import com.trendistashop.repositories.order.*;
import com.trendistashop.repositories.product.DiscountRepository;
import com.trendistashop.repositories.product.ProductVariantRepository;
import com.trendistashop.services.IOrderService;
import com.trendistashop.services.impl.auth.EmailService;
import com.trendistashop.services.impl.notification.OrderNotificationService;
import com.trendistashop.services.impl.product.DiscountService;
import com.trendistashop.utils.ResponseHelper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class OrderService implements IOrderService {

    @Autowired private UserDetailsService userDetailsService;
    @Autowired private OrderRepository orderRepository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private ProductVariantRepository productVariantRepository;
    @Autowired private CartRepository cartRepository;
    @Autowired private PaymentRepository paymentRepository;
    @Autowired private OrderNotificationService orderNotificationService;
    @Autowired private PayOsConfig payOsConfig;
    @Autowired private DiscountRepository discountRepository;
    @Autowired private DiscountService discountService;
    @Autowired private EmailService emailService;
    @Autowired private MoMoConfig moMoConfig;
    private final PayOS payOS;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OrderService(PayOS payOS) {
        this.payOS = payOS;
    }

    @Override
    public OrderReview previewOrderReview(CreateOrder request, Principal principal) {
        UserEntity user = getUser(principal);
        List<CartItem> items = getSelectedCartItems(user, request.getOrderItems());
        BigDecimal subtotal = calculateSubtotal(items);

        DiscountResult result = applyDiscounts(subtotal, request.getDiscountId(), items);

        BigDecimal totalAfterDiscount = subtotal.subtract(result.getDiscountAmount());
        BigDecimal finalTotal = totalAfterDiscount.add(result.getShippingFee());

        List<VariantDTO> variantList = items.stream().map(this::toVariantDTO).toList();
        int totalQuantity = items.stream().mapToInt(CartItem::getCartItemQuantity).sum();

        return OrderReview.builder()
                .variantList(variantList)
                .quantity(totalQuantity)
                .shippingFee(result.getShippingFee())
                .total(finalTotal)
                .discount(result.getDiscountAmount())
                .appliedDiscounts(result.getAppliedDiscounts())
                .build();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public OrderDetailDTO checkoutFromCart(CheckoutRequest request, Principal principal) throws OrderCreationException {
        UserEntity user = getUser(principal);
        List<CartItem> items = getSelectedCartItems(user, request.getCartItemIds());
        BigDecimal subtotal = calculateSubtotal(items);

        DiscountResult result = applyDiscounts(subtotal, request.getDiscountId(), items);

        BigDecimal totalAfterDiscount = subtotal.subtract(result.getDiscountAmount());
        BigDecimal finalTotal = totalAfterDiscount.add(result.getShippingFee());

        // Kiểm tra tồn kho
        for (CartItem item : items) {
            ProductVariant v = productVariantRepository.findById(item.getProductVariantId())
                    .orElseThrow(() -> new OrderCreationException("Sản phẩm không tồn tại"));
            if (v.getStockQuantity() < item.getCartItemQuantity()) {
                throw new OrderCreationException("Hết hàng: " + item.getCartProduct().getName());
            }
        }

        // Tạo Order
        Order order = Order.builder()
                .user(user)
                .totalAmount(finalTotal)
                .orderStatus(OrderStatus.PENDING)
                .paymentMethod(request.getPaymentMethod())
                .orderCode(generateOrderCode())
                .orderDate(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(VietQRConfig.ORDER_TIMEOUT_MINUTES))
                .note(request.getNote())
                .build();

        // Gán discount
        List<Discount> discountEntities = result.getAppliedDiscounts().stream()
                .map(da -> discountRepository.findById(da.getId()).orElse(null))
                .filter(Objects::nonNull)
                .toList();
        order.setDiscounts(discountEntities);

        // Tạo OrderItem + trừ kho
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem ci : items) {
            ProductVariant v = productVariantRepository.findById(ci.getProductVariantId()).get();
            v.setStockQuantity(v.getStockQuantity() - ci.getCartItemQuantity());
            productVariantRepository.save(v);

            OrderItem oi = OrderItem.builder()
                    .order(order)
                    .product(ci.getCartProduct())
                    .productVariantId(ci.getProductVariantId())
                    .quantity(ci.getCartItemQuantity())
                    .itemPrice(ci.getCartProduct().getPrice())
                    .build();
            orderItems.add(oi);
        }
        order.setOrderItems(orderItems);

        // Gán địa chỉ
        Address address = user.getAddressList().stream()
                .filter(a -> a.getId().equals(request.getAddressId()))
                .findFirst()
                .orElseThrow(() -> new OrderCreationException("Địa chỉ không hợp lệ"));
        order.setAddress(address);

        // Tạo payment
        try {
            Payment payment = createPayment(order);
            order.setPayment(payment);
        } catch (Exception e) {
            throw new OrderCreationException("Lỗi tạo thanh toán: " + e.getMessage());
        }

        // Lưu + xóa giỏ
        order = orderRepository.save(order);
        removeItemsFromCart(user.getUserCart(), items);

        return convertToOrderDetailDTO(order);
    }

    private DiscountResult applyDiscounts(BigDecimal subtotal, List<UUID> discountIds, List<CartItem> items) {
        BigDecimal discountAmount = BigDecimal.ZERO;
        BigDecimal shippingFee = BigDecimal.valueOf(0);
        List<DiscountApply> appliedDiscounts = new ArrayList<>();

        if (discountIds != null && !discountIds.isEmpty()) {
            for (UUID discountId : discountIds) {
                DiscountApply apply = discountService.previewDiscountForOrder(discountId, subtotal, items);
                if (apply != null) {
                    if (apply.getApplyType() == DiscountApplyFor.ORDER) {
                        discountAmount = discountAmount.add(apply.getValueApply());
                    } else if (apply.getApplyType() == DiscountApplyFor.SHIPPING) {
                        shippingFee = shippingFee.subtract(apply.getValueApply());
                        if (shippingFee.compareTo(BigDecimal.ZERO) < 0) {
                            shippingFee = BigDecimal.ZERO;
                        }
                    }
                    appliedDiscounts.add(apply);
                }
            }
        }

        return new DiscountResult(discountAmount, shippingFee, appliedDiscounts);
    }

    private UserEntity getUser(Principal principal) {
        return (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
    }

    private List<CartItem> getSelectedCartItems(UserEntity user, List<UUID> cartItemIds) {
        return user.getUserCart().getCartItems().stream()
                .filter(i -> cartItemIds.contains(i.getId()))
                .toList();
    }

    private BigDecimal calculateSubtotal(List<CartItem> items) {
        return items.stream()
                .map(i -> i.getCartProduct().getPrice().multiply(BigDecimal.valueOf(i.getCartItemQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Long generateOrderCode() {
        return System.currentTimeMillis() % 1000000;
    }
    private Long generateNewOrderCode(Order order) {
        long base = order.getOrderCode();
        Random random = new Random();
        Long newCode;
        do {
            newCode = base * 10000 + random.nextInt(1000, 9999);
        } while (orderRepository.findByOrderCode(newCode).isPresent());
        return newCode;
    }
    private void removeItemsFromCart(Cart cart, List<CartItem> items) {
        BigDecimal totalRemove = items.stream()
                .map(i -> i.getCartProduct().getPrice().multiply(BigDecimal.valueOf(i.getCartItemQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setCartTotal(cart.getCartTotal().subtract(totalRemove));
        cart.getCartItems().removeAll(items);
        cartRepository.save(cart);
    }

    private VariantDTO toVariantDTO(CartItem ci) {
        ProductVariant v = productVariantRepository.findById(ci.getProductVariantId()).orElse(null);
        if (v == null) return null;

        List<ProductImageDTO> images = v.getProduct().getImages().stream()
                .map(img -> ProductImageDTO.builder()
                        .id(img.getId())
                        .url(img.getUrl())
                        .isThumbnail(img.getIsThumbnail())
                        .order(img.getOrder())
                        .build())
                .toList();

        return VariantDTO.builder()
                .id(v.getId())
                .colorId(v.getColor() != null ? v.getColor().getId() : null)
                .sizeId(v.getSize() != null ? v.getSize().getId() : null)
                .colorName(v.getColor() != null ? v.getColor().getName() : null)
                .colorCode(v.getColor() != null ? v.getColor().getCode() : null)
                .colorValue(v.getColor() != null ? v.getColor().getValue() : null)
                .sizeName(v.getSize() != null ? v.getSize().getValue() : null)
                .stockQuantity(v.getStockQuantity())
                .codeVariant(v.getCodeVariant())
                .order(v.getOrder())
                .price(ci.getCartProduct().getPrice())
                .productImages(images)
                .build();
    }

    private Payment createPayment(Order order) throws Exception {
        BigDecimal subtotal = order.getOrderItems().stream()
                .map(oi -> oi.getItemPrice().multiply(BigDecimal.valueOf(oi.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        DiscountResult discountResult = recalculateDiscountsFromOrder(order, subtotal);
        BigDecimal discountAmount = discountResult.getDiscountAmount();
        BigDecimal shippingFee = discountResult.getShippingFee();
        List<ItemData> items = new ArrayList<>();

        // 1. Sản phẩm
        order.getOrderItems().forEach(oi -> {
            items.add(ItemData.builder()
                    .name(oi.getProduct().getName())
                    .quantity(oi.getQuantity())
                    .price(oi.getItemPrice().intValue())
                    .build());
        });

        // 2. Giảm giá
        if (discountAmount.compareTo(BigDecimal.ZERO) > 0) {
            items.add(ItemData.builder()
                    .name("Giảm giá")
                    .quantity(1)
                    .price(-discountAmount.intValue())
                    .build());
        }

        // 3. Phí vận chuyển
        if (shippingFee.compareTo(BigDecimal.ZERO) > 0) {
            items.add(ItemData.builder()
                    .name("Phí vận chuyển")
                    .quantity(1)
                    .price(shippingFee.intValue())
                    .build());
        }

        // Tạo PaymentData
        PaymentData data = PaymentData.builder()
                .orderCode(order.getOrderCode())
                .amount(order.getTotalAmount().intValue())
                .description("TT HOA DON " + order.getOrderCode())
                .items(items)
                .buyerEmail(order.getUser().getEmail())
                .cancelUrl(payOsConfig.getCancelUrl())
                .returnUrl(payOsConfig.getReturnUrl())
                .build();

        PaymentMethod method = order.getPaymentMethod();

        if (method == PaymentMethod.QR) {
            CheckoutResponseData resp = payOS.createPaymentLink(data);
            return Payment.builder()
                    .order(order)
                    .amount(resp.getAmount())
                    .paymentMethod(order.getPaymentMethod())
                    .paymentStatus(resp.getStatus())
                    .transactionId(resp.getOrderCode())
                    .qrCode(resp.getQrCode())
                    .deepLink(resp.getCheckoutUrl())
                    .build();
        } else if (method == PaymentMethod.MOMO) {
            MoMoPaymentResponse resp = createMoMoPaymentLink(data);
            return Payment.builder()
                    .order(order)
                    .amount(resp.getAmount())
                    .paymentMethod(order.getPaymentMethod())
                    .paymentStatus(resp.getStatus())
                    .transactionId(resp.getOrderCode())
                    .qrCode(resp.getQrCode())
                    .deepLink(resp.getCheckoutUrl())
                    .build();
        } else {
            throw new OrderCreationException("Phương thức thanh toán chưa hỗ trợ: " + method.name());
        }
    }

    public DiscountResult recalculateDiscountsFromOrder(Order order, BigDecimal subtotal) {
        List<CartItem> tempItems = order.getOrderItems().stream()
                .map(oi -> {
                    CartItem ci = new CartItem();
                    ci.setCartProduct(oi.getProduct());
                    ci.setProductVariantId(oi.getProductVariantId());
                    ci.setCartItemQuantity(oi.getQuantity());
                    return ci;
                })
                .toList();

        return applyDiscounts(subtotal,
                order.getDiscounts().stream().map(Discount::getId).toList(),
                tempItems);
    }

    // ============= MOMO PAYMENT =============

    private MoMoPaymentResponse createMoMoPaymentLink(PaymentData data) {
        String orderId = String.valueOf(data.getOrderCode());
        String amount = String.valueOf(data.getAmount());
        String requestId = UUID.randomUUID().toString().replace("-", "");
        String orderInfo = "TT HOA DON " + orderId;
        String extraData = "";

        String partnerCode = moMoConfig.getPartnerCode();
        String accessKey = moMoConfig.getAccessKey();

        try {
            // Tạo rawSignature theo thứ tự alphabet
            String rawSignature = "accessKey=" + accessKey +
                    "&amount=" + amount +
                    "&extraData=" + extraData +
                    "&ipnUrl=" + moMoConfig.getIpnUrl() +
                    "&orderId=" + orderId +
                    "&orderInfo=" + orderInfo +
                    "&partnerCode=" + partnerCode +
                    "&redirectUrl=" + moMoConfig.getRedirectUrl() +
                    "&requestId=" + requestId +
                    "&requestType=captureWallet";

            log.info("MoMo rawSignature: {}", rawSignature);

            String signature = hmacSHA256(moMoConfig.getSecretKey(), rawSignature);

            // Tạo JSON request bằng ObjectMapper
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("partnerCode", partnerCode);
            requestBody.put("accessKey", accessKey);
            requestBody.put("requestId", requestId);
            requestBody.put("amount", Long.parseLong(amount));
            requestBody.put("orderId", orderId);
            requestBody.put("orderInfo", orderInfo);
            requestBody.put("redirectUrl", moMoConfig.getRedirectUrl());
            requestBody.put("ipnUrl", moMoConfig.getIpnUrl());
            requestBody.put("requestType", "captureWallet");
            requestBody.put("extraData", extraData);
            requestBody.put("signature", signature);

            String jsonRequest = objectMapper.writeValueAsString(requestBody);
            log.info("MoMo FINAL JSON: {}", jsonRequest);

            // Gửi request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(jsonRequest, headers);

            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.postForObject(
                    moMoConfig.getEndpoint(),
                    entity,
                    String.class
            );

            log.info("MoMo response: {}", response);

            // Parse response bằng ObjectMapper - FIX LỖI Ở ĐÂY
            JsonNode responseNode = objectMapper.readTree(response);
            int resultCode = responseNode.get("resultCode").asInt();
            String message = responseNode.get("message").asText();

            log.info("MoMo resultCode: {}, message: {}", resultCode, message);

            if (resultCode != 0) {
                log.error("MoMo payment failed: resultCode={}, message={}", resultCode, message);
                throw new OrderCreationException("Lỗi tạo thanh toán MoMo: " + message);
            }

            String payUrl = responseNode.get("payUrl").asText();
            String qrCodeUrl = responseNode.has("qrCodeUrl") ?
                    responseNode.get("qrCodeUrl").asText() : null;

            log.info("MoMo payment created successfully: orderId={}, payUrl={}", orderId, payUrl);

            return MoMoPaymentResponse.builder()
                    .orderCode(data.getOrderCode())
                    .amount(data.getAmount())
                    .status("PENDING")
                    .checkoutUrl(payUrl)
                    .qrCode(qrCodeUrl)
                    .build();

        } catch (OrderCreationException e) {
            // Re-throw OrderCreationException
            throw e;
        } catch (Exception e) {
            log.error("Error creating MoMo payment for orderId: {}", orderId, e);
            throw new OrderCreationException("Lỗi tạo thanh toán MoMo: " + e.getMessage());
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

    // ============= ORDER MANAGEMENT =============

    @Override
    public TypeResponse<List<OrderDetailDTO>> getAllOrder(OrderStatus status, Principal principal) {
        UserEntity user = getUser(principal);
        List<OrderDetailDTO> dtos = orderRepository.findByUser(user).stream()
                .filter(o -> status == null || o.getOrderStatus() == status)
                .map(this::convertToOrderDetailDTO)
                .toList();
        return ResponseHelper.ok(dtos, "Lấy danh sách đơn hàng thành công");
    }

    @Override
    @Transactional
    public OrderDetailDTO retryPayment(UUID orderId, String paymentMethod) throws Exception {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundEx("Không tìm thấy đơn"));

        if (!order.getOrderStatus().equals(OrderStatus.CANCELLED)) {
            throw new OrderCreationException("Chỉ có thể thanh toán lại đơn bị hủy");
        }
        if (paymentMethod ==null || paymentMethod.isEmpty()) {
            order.setPaymentMethod(PaymentMethod.COD);
        }
        order.setPaymentMethod(PaymentMethod.valueOf(paymentMethod));
        order.setOrderCode(generateNewOrderCode(order));
        order = orderRepository.save(order);
        Payment newPayment = createPayment(order);
        order.setPayment(newPayment);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setExpiredAt(LocalDateTime.now().plusMinutes(VietQRConfig.ORDER_TIMEOUT_MINUTES));
        orderRepository.save(order);
        return convertToOrderDetailDTO(order);
    }

    @Override
    public void cancelOrderByOrderId(UUID id, Principal principal) {
        UserEntity user = getUser(principal);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEx("Không tìm thấy đơn"));

        if (!order.getUser().getId().equals(user.getId()))
            throw new OrderCreationException("Không có quyền");
        if (!order.getOrderStatus().equals(OrderStatus.PENDING))
            throw new OrderCreationException("Không thể hủy");

        order.setOrderStatus(OrderStatus.CANCELLED);
        rollbackStock(order);
        orderRepository.save(order);
    }

    private void rollbackStock(Order order) {
        order.getOrderItems().forEach(oi -> {
            ProductVariant v = productVariantRepository.findById(oi.getProductVariantId()).orElse(null);
            if (v != null) {
                v.setStockQuantity(v.getStockQuantity() + oi.getQuantity());
                productVariantRepository.save(v);
            }
        });
    }

    @Override
    public OrderDetailDTO updateOrderStatus(UUID orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundEx("Không tìm thấy đơn"));
        order.setOrderStatus(status);
        if (status == OrderStatus.PROCESSING && order.getPayment() != null) {
            order.getPayment().setPaymentStatus(PaymentStatus.PAID.name());
        }
        orderRepository.save(order);
        return convertToOrderDetailDTO(order);
    }

    @Override
    @Transactional
    public void updateOrderStatusFromPayment(Long orderCode, String status, boolean cancel) {
        try {
            Order order = orderRepository.findByOrderCode(orderCode)
                    .orElseThrow(() -> new ResourceNotFoundEx("Không tìm thấy đơn hàng với orderCode: " + orderCode));

            Payment payment = order.getPayment();
            if (payment == null) {
                log.warn("Đơn hàng {} chưa có payment", order.getId());
                return;
            }

            String finalStatus = status.toUpperCase();

            // HỦY HOẶC HẾT HẠN
            if (cancel || "CANCELLED".equals(finalStatus) || "EXPIRED".equals(finalStatus)) {
                if (order.getOrderStatus() == OrderStatus.CANCELLED) {
                    log.info("Đơn {} đã hủy trước đó", order.getId());
                    return;
                }
                String urlRetry = payOsConfig.getPaymentRetry() + "&orderId=" + order.getId() + "paymentMethod" + order.getPaymentMethod().name();
                payment.setPaymentStatus(PaymentStatus.CANCELLED.name());
                payment.setCancelledAt(LocalDateTime.now());
                order.setOrderStatus(OrderStatus.CANCELLED);
                rollbackStock(order);
                emailService.sendOrderCancelledEmail(order, urlRetry);
                log.info("Hủy đơn + gửi email thanh toán lại | orderId: {}", order.getId());
                orderRepository.save(order);
                paymentRepository.save(payment);
                return;
            }

            // THANH TOÁN THÀNH CÔNG
            if ("PAID".equals(finalStatus)) {
                payment.setPaymentStatus(PaymentStatus.PAID.name());
                payment.setPaidAt(LocalDateTime.now());
                order.setOrderStatus(OrderStatus.PROCESSING);
                emailService.sendOrderSuccessEmail(order);
                log.info("Thanh toán thành công + gửi hóa đơn | orderId: {}", order.getId());
                orderRepository.save(order);
                paymentRepository.save(payment);
                return;
            }

            // PENDING / PAYING
            if ("PENDING".equals(finalStatus) || "PAYING".equals(finalStatus)) {
                if (PaymentStatus.CREATED.name().equals(payment.getPaymentStatus())) {
                    payment.setPaymentStatus(PaymentStatus.PENDING.name());
                }
                orderRepository.save(order);
                paymentRepository.save(payment);
            }

        } catch (ResourceNotFoundEx e) {
            log.error("Không tìm thấy đơn hàng: {}", e.getMessage());
            throw new OrderCreationException("Lỗi xử lý callback: " + e.getMessage());
        } catch (Exception e) {
            log.error("Lỗi xử lý callback", e);
            throw new OrderCreationException("Lỗi xử lý callback: " + e.getMessage());
        }
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void checkExpiredOrders() {
        List<Order> expired = orderRepository.findExpiredPendingOrders(
                LocalDateTime.now(), PaymentStatus.CREATED.name());

        expired.forEach(order -> {
            order.setOrderStatus(OrderStatus.CANCELLED);
            rollbackStock(order);
            if (order.getPayment() != null) {
                order.getPayment().setPaymentStatus(PaymentStatus.EXPIRED.name());
            }
            orderRepository.save(order);
            log.info("Tự động hủy đơn hết hạn | orderId: {}", order.getId());
        });
    }

    public OrderDetailDTO convertToOrderDetailDTO(Order order) {
        return OrderDetailDTO.builder()
                .id(order.getId())
                .orderDate(order.getCreatedAt())
                .orderStatus(order.getOrderStatus())
                .totalAmount(order.getTotalAmount())
                .payment(order.getPayment())
                .orderItemList(order.getOrderItems().stream().map(this::toOrderItemDTO).toList())
                .address(order.getAddress() != null ? modelMapper.map(order.getAddress(), AddressDTO.class) : null)
                .build();
    }

    private OrderItemDTO toOrderItemDTO(OrderItem oi) {
        return OrderItemDTO.builder()
                .productId(oi.getProduct().getId())
                .productName(oi.getProduct().getName())
                .quantity(oi.getQuantity())
                .itemPrice(oi.getItemPrice())
                .variantDTO(toVariantDTOFromOrderItem(oi))
                .build();
    }

    private VariantDTO toVariantDTOFromOrderItem(OrderItem oi) {
        return productVariantRepository.findById(oi.getProductVariantId())
                .map(v -> modelMapper.map(v, VariantDTO.class))
                .orElse(null);
    }
}