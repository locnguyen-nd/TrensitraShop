package com.trendistashop.services.impl.order;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.pdf.BaseFont;
import com.trendistashop.config.MoMoConfig;
import com.trendistashop.config.PayOsConfig;
import com.trendistashop.config.UploadConfig;
import com.trendistashop.config.VietQRConfig;
import com.trendistashop.constants.ResponseMessage;
import com.trendistashop.dto.payos.MoMoPaymentResponse;
import com.trendistashop.dto.payos.PaymentData;
import com.trendistashop.dto.request.CheckoutRequest;
import com.trendistashop.dto.request.CreateOrder;
import com.trendistashop.dto.response.*;
import com.trendistashop.dto.response.record.RevenueReportDTO;
import com.trendistashop.dto.response.record.StatusSummaryDTO;
import com.trendistashop.dto.response.record.TopProductDTO;
import com.trendistashop.entities.product.Discount;
import com.trendistashop.entities.product.Product;
import com.trendistashop.entities.product.ProductImage;
import com.trendistashop.entities.product.ProductVariant;
import com.trendistashop.entities.user.*;
import com.trendistashop.enums.*;
import com.trendistashop.exceptions.OrderCreationException;
import com.trendistashop.exceptions.ResourceNotFoundEx;
import com.trendistashop.helper.BarcodeImage;
import com.trendistashop.repositories.order.*;
import com.trendistashop.repositories.product.DiscountRepository;
import com.trendistashop.repositories.product.ProductVariantRepository;
import com.trendistashop.services.IOrderService;
import com.trendistashop.services.impl.auth.EmailService;
import com.trendistashop.services.impl.product.DiscountService;
import com.trendistashop.utils.ResponseHelper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;
import vn.payos.PayOS;

import vn.payos.model.v2.paymentRequests.CreatePaymentLinkRequest;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkResponse;
import vn.payos.model.v2.paymentRequests.PaymentLinkItem;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService implements IOrderService {

    @Autowired private UserDetailsService userDetailsService;
    @Autowired private OrderRepository orderRepository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private ProductVariantRepository productVariantRepository;
    @Autowired private CartRepository cartRepository;
    @Autowired private PaymentRepository paymentRepository;
    @Autowired private PayOsConfig payOsConfig;
    @Autowired private DiscountRepository discountRepository;
    @Autowired private DiscountService discountService;
    @Autowired private EmailService emailService;
    @Autowired private MoMoConfig moMoConfig;
    @Autowired
    private UploadConfig uploadConfig;
    @Autowired
    private SpringTemplateEngine templateEngine;
    private final PayOS payOS;
    private final ObjectMapper objectMapper = new ObjectMapper();
    public OrderService(PayOS payOS) {
        this.payOS = payOS;
    }

    @Override
    public TypeResponse<OrderReview> previewOrderReview(CreateOrder request, Principal principal) {
        try {
            UserEntity user = getUser(principal);
            List<CartItem> items = getSelectedCartItems(user, request.getOrderItems());
            BigDecimal subtotal = calculateSubtotal(items);
            DiscountResult result = applyDiscounts(subtotal, request.getDiscountId(), items);
            BigDecimal totalAfterDiscount = subtotal.subtract(result.getDiscountAmount());
            BigDecimal finalTotal = totalAfterDiscount.add(result.getShippingFee());
            List<VariantDTO> variantList = items.stream().map(this::toVariantDTO).toList();
            int totalQuantity = items.stream().mapToInt(CartItem::getCartItemQuantity).sum();
            OrderReview orderReview = OrderReview.builder()
                    .variantList(variantList)
                    .quantity(totalQuantity)
                    .shippingFee(result.getShippingFee())
                    .total(finalTotal)
                    .discount(result.getDiscountAmount())
                    .appliedDiscounts(result.getAppliedDiscounts())
                    .build();
            log.info("OrderReview: {}", orderReview);
            return  ResponseHelper.ok(orderReview, ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            log.error("Lỗi xem trước đơn hàng: {}", e.getMessage());
            return ResponseHelper.badRequest("Lỗi xem trước đơn hàng: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public TypeResponse<OrderDetailDTO> checkoutFromCart(CheckoutRequest request, Principal principal) {
        try {
            UserEntity user = getUser(principal);
            List<CartItem> items = getSelectedCartItems(user, request.getCartItemIds());
            BigDecimal subtotal = calculateSubtotal(items);
            DiscountResult result = applyDiscounts(subtotal, request.getDiscountId(), items);
            BigDecimal totalAfterDiscount = subtotal.subtract(result.getDiscountAmount());
            BigDecimal finalTotal = totalAfterDiscount.add(result.getShippingFee());

            // Kiểm tra tồn kho
            for (CartItem item : items) {
                ProductVariant v = productVariantRepository.findById(item.getProductVariantId()).get();
                if(v == null){
                    return  ResponseHelper.notFound(ResponseMessage.PRODUCT_NOT_FOUND);
                }
                if (v.getStockQuantity() < item.getCartItemQuantity()) {
                    return ResponseHelper.validationError("Stock", "Sản phẩm " + v.getProduct().getName() + " không đủ số lượng trong kho");
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
            List<Discount> discountEntities = result.getAppliedDiscounts().stream()
                    .map(da -> discountRepository.findById(da.getId()).orElse(null))
                    .filter(Objects::nonNull)
                    .toList();
            order.setDiscounts(discountEntities);
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

            Address address = user.getAddressList().stream()
                    .filter(a -> a.getId().equals(request.getAddressId()))
                    .findFirst()
                    .orElseThrow(() -> new OrderCreationException("Địa chỉ không hợp lệ"));
            order.setAddress(address);

            try {
                Payment payment = createPayment(order);
                order.setPayment(payment);
            } catch (Exception e) {
                log.error("Lỗi tạo thanh toán: {}", e.getMessage());
                return ResponseHelper.badRequest("Lỗi tạo thanh toán: " + e.getMessage());
            }
            order = orderRepository.save(order);
            removeItemsFromCart(user.getUserCart(), items);

            return ResponseHelper.ok(convertToOrderDetailDTO(order), ResponseMessage.CREATE_SUCCESS);
        } catch (Exception e) {
            log.error("Lỗi tạo đơn hàng: {}", e.getMessage());
            return ResponseHelper.badRequest("Lỗi tạo đơn hàng: " + e.getMessage());
        }
    }

    private DiscountResult applyDiscounts(BigDecimal subtotal, List<UUID> discountIds, List<CartItem> items) {
        BigDecimal discountAmount = BigDecimal.ZERO;
        BigDecimal originalShippingFee = BigDecimal.valueOf(30000); // phí ship gốc
        BigDecimal shippingFeeAfterDiscount = originalShippingFee;
        List<DiscountApply> appliedDiscounts = new ArrayList<>();
        if (discountIds != null && !discountIds.isEmpty()) {
            for (UUID discountId : discountIds) {
                DiscountApply apply = discountService.previewDiscountForOrder(discountId, subtotal, items);
                if (apply != null) {
                    if (apply.getApplyType() == DiscountApplyFor.ORDER) {
                        discountAmount = discountAmount.add(apply.getValueApply());
                    } else if (apply.getApplyType() == DiscountApplyFor.SHIPPING) {
                        shippingFeeAfterDiscount = shippingFeeAfterDiscount.subtract(apply.getValueApply());
                        if (shippingFeeAfterDiscount.compareTo(BigDecimal.ZERO) < 0) {
                            shippingFeeAfterDiscount = BigDecimal.ZERO;
                        }
                    }
                    appliedDiscounts.add(apply);
                }
            }
        }
        return new DiscountResult(discountAmount, shippingFeeAfterDiscount, appliedDiscounts);
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

    private Payment createPayment(Order order) {
        try {
            BigDecimal subtotal = order.getOrderItems().stream()
                    .map(oi -> oi.getItemPrice().multiply(BigDecimal.valueOf(oi.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            DiscountResult discountResult = recalculateDiscountsFromOrder(order, subtotal);
            BigDecimal discountAmount = discountResult.getDiscountAmount();
            BigDecimal shippingFee = discountResult.getShippingFee();
            BigDecimal finalTotal = order.getTotalAmount();
            BigDecimal savedAmount = subtotal.add(shippingFee).subtract(finalTotal);
            long amount = finalTotal.setScale(0, RoundingMode.HALF_UP).intValueExact();

            List<PaymentLinkItem> items = new ArrayList<>();
            // 1. Chỉ thêm các sản phẩm thật
            for (OrderItem oi : order.getOrderItems()) {
                long unitPrice = oi.getItemPrice().setScale(0, RoundingMode.HALF_UP).intValueExact();
                items.add(PaymentLinkItem.builder()
                        .name(oi.getProduct().getName())
                        .quantity(oi.getQuantity())
                        .price(unitPrice)
                        .build());
            }

            // 2. Nếu có giảm giá
            if (discountAmount.compareTo(BigDecimal.ZERO) > 0) {
                long discountInt = discountAmount.setScale(0, RoundingMode.HALF_UP).intValueExact();
                items.add(PaymentLinkItem.builder()
                        .name("Giảm giá")
                        .quantity(1)
                        .price(-discountInt)
                        .build());
            }

            // 3. Nếu có phí ship
            long shipInt = shippingFee.setScale(0, RoundingMode.HALF_UP).intValueExact();
            items.add(PaymentLinkItem.builder()
                    .name(shipInt == 0 ? "Miễn phí vận chuyển" : "Phí vận chuyển")
                    .quantity(1)
                    .price(shipInt)
                    .build());

            // Tạo PaymentData
            CreatePaymentLinkRequest data = CreatePaymentLinkRequest.builder()
                    .orderCode(order.getOrderCode())
                    .amount(amount)
                    .description("TT HOA DON " + order.getOrderCode())
                    .items(items)
                    .buyerName(order.getUser().getFullName())
                    .buyerEmail(order.getUser().getEmail())
                    .cancelUrl(payOsConfig.getCancelUrl())
                    .returnUrl(payOsConfig.getReturnUrl())
                    .expiredAt(LocalDateTime.now()
                            .plusMinutes(VietQRConfig.ORDER_TIMEOUT_MINUTES)
                            .atZone(ZoneId.systemDefault())
                            .toEpochSecond())
                    .build();

            PaymentMethod method = order.getPaymentMethod();
            if (method == PaymentMethod.QR) {
                int finalAmount = order.getTotalAmount()
                        .setScale(0, RoundingMode.HALF_UP)
                        .intValueExact();
                CreatePaymentLinkResponse resp = payOS.paymentRequests().create(data);
                return Payment.builder()
                        .order(order)
                        .amount(resp.getAmount())
                        .shippingFee(shipInt)
                        .discountAmount(discountAmount.setScale(0, RoundingMode.HALF_UP).intValueExact())
                        .savedAmount(savedAmount)
                        .paymentMethod(order.getPaymentMethod())
                        .paymentStatus(resp.getStatus().getValue())
                        .transactionId(resp.getOrderCode())
                        .qrCode(resp.getQrCode())
                        .deepLink(resp.getCheckoutUrl())
                        .build();
            } else if (method == PaymentMethod.MOMO) {
                PaymentData dataMomo = PaymentData.builder()
                        .orderCode(order.getOrderCode())
                        .amount((int) amount)
                        .description("TT HOA DON " + order.getOrderCode())
                        .build();
                MoMoPaymentResponse resp = createMoMoPaymentLink(dataMomo);
                return Payment.builder()
                        .order(order)
                        .amount(resp.getAmount())
                        .shippingFee(shipInt)
                        .discountAmount(discountAmount.setScale(0, RoundingMode.HALF_UP).intValueExact())
                        .paymentMethod(order.getPaymentMethod())
                        .paymentStatus(resp.getStatus())
                        .transactionId(resp.getOrderCode())
                        .qrCode(resp.getQrCode())
                        .deepLink(resp.getCheckoutUrl())
                        .build();
            } else {
                throw new OrderCreationException("Phương thức thanh toán chưa hỗ trợ: " + method.name());
            }
        } catch (Exception e) {
            log.error("Lỗi tạo payment: {}", e.getMessage());
            throw e;
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
    public TypeResponse<PageDTO<OrderDetailDTO>> getAllOrder(
            OrderStatus status,
            Principal principal,
            Pageable pageable) {

        try {
            UserEntity user = getUser(principal);
            Page<Order> orderPage = orderRepository.findByUser(user, pageable);
            List<OrderDetailDTO> dtos = orderPage.getContent().stream()
                    .filter(o -> status == null || o.getOrderStatus() == status)
                    .map(this::convertToOrderDetailDTO)
                    .collect(Collectors.toList());
            PageDTO<OrderDetailDTO> pageDTO = new PageDTO<>();
            pageDTO.setPage(orderPage.getNumber());
            pageDTO.setSize(orderPage.getSize());
            pageDTO.setTotal(orderPage.getTotalElements());
            pageDTO.setTotalPage(orderPage.getTotalPages());
            pageDTO.setContent(dtos);

            return ResponseHelper.ok(pageDTO, "Lấy danh sách đơn hàng thành công");

        } catch (Exception e) {
            log.error("Lỗi khi lấy danh sách đơn hàng", e);
            return ResponseHelper.serverError("Lấy danh sách đơn hàng thất bại");
        }
    }

    @Override
    @Transactional
    public TypeResponse<OrderDetailDTO> retryPayment(UUID orderId, String paymentMethod)  {
        try {
            Order order = orderRepository.findById(orderId).get();
            if (order == null) {
                return ResponseHelper.notFound(ResponseMessage.ORDER_NOT_FOUND);
            }
            if (!order.getOrderStatus().equals(OrderStatus.CANCELLED) && !order.getOrderStatus().equals(OrderStatus.PENDING)) {
                return ResponseHelper.validationError("orderStatus", "Chỉ có thể thanh toán lại cho đơn hàng đã hủy");
            }
            if (paymentMethod == null || paymentMethod.isEmpty()) {
                log.warn("paymentMethod is null or empty");
                log.warn("System will use COD as default payment method");
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
            return ResponseHelper.ok(convertToOrderDetailDTO(order), "Đã tạo lại thanh toán thành công");
        } catch (Exception e) {
            log.error("Lỗi tạo lại thanh toán: {}", e.getMessage());
            return ResponseHelper.badRequest("Lỗi tạo lại thanh toán: " + e.getMessage());
        }
    }

    @Override
    public TypeResponse<RevenueReportDTO> getRevenueReport(LocalDate from, LocalDate to) {
        try {
            LocalDateTime start = from.atStartOfDay();
            LocalDateTime end = to.atTime(23, 59, 59);

            List<Order> orders = orderRepository.findByCreatedAtBetween(start, end);

            BigDecimal totalRevenue = BigDecimal.ZERO;
            BigDecimal totalRefund = BigDecimal.ZERO;
            long totalOrders = 0;
            long completedOrders = 0;
            long cancelledOrders = 0;

            for (Order order : orders) {
                if (order.getOrderStatus() == OrderStatus.PROCESSING || order.getOrderStatus() == OrderStatus.DELIVERED) {
                    totalRevenue = totalRevenue.add(order.getTotalAmount());
                    completedOrders++;
                } else if (order.getOrderStatus() == OrderStatus.CANCELLED) {
                    cancelledOrders++;
                    totalRefund = totalRefund.add(order.getTotalAmount());
                }
                totalOrders++;
            }

            BigDecimal netRevenue = totalRevenue.subtract(totalRefund);

            RevenueReportDTO report = new RevenueReportDTO(
                    totalRevenue,
                    totalRefund,
                    netRevenue,
                    totalOrders,
                    completedOrders,
                    cancelledOrders,
                    "VND"
            );

            return ResponseHelper.ok(report, ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            log.error("Lỗi báo cáo doanh thu: {}", e.getMessage(), e);
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }

    @Override
    public TypeResponse<List<StatusSummaryDTO>> getOrderStatusSummary(LocalDate from, LocalDate to) {
        try {
            LocalDateTime start = (from != null) ? from.atStartOfDay() : LocalDateTime.now().minusYears(10);
            LocalDateTime end = (to != null) ? to.atTime(23, 59, 59) : LocalDateTime.now();

            List<Object[]> results = orderRepository.countOrdersByStatus(start, end);

            List<StatusSummaryDTO> summary = results.stream()
                    .map(row -> new StatusSummaryDTO(
                            (OrderStatus) row[0],
                            ((Number) row[1]).longValue(),
                            (BigDecimal) row[2]
                    ))
                    .toList();

            return ResponseHelper.ok(summary, ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            log.error("Lỗi tóm tắt trạng thái: {}", e.getMessage(), e);
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }

    @Override
    public TypeResponse<List<TopProductDTO>> getTopSellingProducts(int limit, LocalDate from, LocalDate to) {
        try {
            LocalDateTime start = (from != null) ? from.atStartOfDay() : LocalDateTime.now().minusYears(10);
            LocalDateTime end = (to != null) ? to.atTime(23, 59, 59) : LocalDateTime.now();

            Pageable pageable = PageRequest.of(0, limit);
            List<Object[]> results = orderRepository.findTopSellingProducts(start, end, pageable);

            List<TopProductDTO> topProducts = results.stream()
                    .map(row -> {
                        Product product = (Product) row[0];
                        Long quantity = ((Number) row[1]).longValue();
                        BigDecimal revenue = (BigDecimal) row[2];

                        String thumbnail = product.getImages().stream()
                                .filter(ProductImage::getIsThumbnail)
                                .map(ProductImage::getUrl)
                                .findFirst()
                                .orElse(null);

                        return new TopProductDTO(
                                product.getId(),
                                product.getName(),
                                thumbnail,
                                quantity,
                                revenue
                        );
                    })
                    .toList();

            return ResponseHelper.ok(topProducts, ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            log.error("Lỗi lấy sản phẩm bán chạy: {}", e.getMessage(), e);
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }

    @Override
    public TypeResponse<byte[]> exportInvoicePdf(UUID orderId) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new ResourceNotFoundEx("Không tìm thấy đơn hàng"));

            OrderDetailDTO orderDetailDTO = convertToOrderDetailDTO(order);
            String trackingCode = generateTrackingCode(order.getId());
            BarcodeImage barcodeImage = new BarcodeImage();
            // Tạo ảnh barcode (PNG → base64)
            byte[] barcodeImageBytes = barcodeImage.generateBarcodeImage(trackingCode);
            String barcodeBase64 = barcodeImageBytes != null
                    ? "data:image/png;base64," + Base64.getEncoder().encodeToString(barcodeImageBytes)
                    : null;

            // Tạo model
            Map<String, Object> model = new HashMap<>();
            model.put("order", orderDetailDTO);
            model.put("shopName", "Trendista Shop");
            model.put("shopPhone", "0123 456 789");
            model.put("shopAddress", "123 Đường ABC, Phường 1, Quận 1, TP.HCM");
            model.put("currentDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            model.put("shippingUnit", "Giao Hàng Nhanh");
            model.put("trackingCode", trackingCode);
            model.put("barcodeBase64", barcodeBase64); // ← Dữ liệu ảnh

            String htmlContent = templateEngine.process("shipping-label-template",
                    new Context(Locale.getDefault(), model));

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();

            // Font tiếng Việt
            renderer.getFontResolver().addFont("fonts/DejaVuSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);
            renderer.finishPDF();

            return ResponseHelper.ok(outputStream.toByteArray(), "Xuất tem vận chuyển thành công");

        } catch (Exception e) {
            log.error("Lỗi xuất tem vận chuyển: {}", e.getMessage(), e);
            return ResponseHelper.serverError("Không thể tạo tem vận chuyển");
        }
    }
    private String generateTrackingCode(UUID orderId) {
        // Format: SPXVN + timestamp + mã ngẫu nhiên
        String timestamp = String.valueOf(System.currentTimeMillis()).substring(7);
        String random = orderId.toString().replace("-", "").substring(0, 6).toUpperCase();
        return "SPXVN" + timestamp + random;
    }

    @Override
    public TypeResponse<byte[]> exportOrdersToExcel(OrderStatus status, LocalDate from, LocalDate to) {
        try {
            LocalDateTime start = (from != null) ? from.atStartOfDay() : LocalDateTime.now().minusYears(10);
            LocalDateTime end = (to != null) ? to.atTime(23, 59, 59) : LocalDateTime.now();

            List<Order> orders = orderRepository.findByCreatedAtBetweenAndStatus(start, end, status);

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Đơn hàng");

            // Header
            Row header = sheet.createRow(0);
            String[] columns = {"Mã đơn", "Khách hàng", "SĐT", "Ngày đặt", "Trạng thái", "Tổng tiền"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Data
            int rowNum = 1;
            for (Order order : orders) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(order.getOrderCode());
                row.createCell(1).setCellValue(order.getUser().getFullName());
                row.createCell(2).setCellValue(order.getUser().getPhoneNumber());
                row.createCell(3).setCellValue(order.getCreatedAt());
                row.createCell(4).setCellValue(order.getOrderStatus().name());
                row.createCell(5).setCellValue(order.getTotalAmount().doubleValue());
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            workbook.close();

            return ResponseHelper.ok(out.toByteArray(), "Xuất Excel thành công");
        } catch (Exception e) {
            log.error("Lỗi xuất Excel: {}", e.getMessage(), e);
            return ResponseHelper.serverError("Không thể xuất file Excel");
        }
    }
    @Override
    public TypeResponse<Void> cancelOrderByOrderId(UUID id, Principal principal) {
        try {
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
        return ResponseHelper.ok(null,ResponseMessage.ORDER_ALREADY_CANCELLED);
        } catch (Exception e) {
            log.error("Lỗi hủy đơn hàng: {}", e.getMessage());
            return ResponseHelper.badRequest("Lỗi hủy đơn hàng: " + e.getMessage());
        }
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
    public TypeResponse<OrderDetailDTO> updateOrderStatus(UUID orderId, OrderStatus status) {
        try{
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundEx("Không tìm thấy đơn"));
        order.setOrderStatus(status);
        if (status == OrderStatus.PROCESSING && order.getPayment() != null) {
            order.getPayment().setPaymentStatus(PaymentStatus.PAID.name());
        }
        orderRepository.save(order);
        return  ResponseHelper.ok(convertToOrderDetailDTO(order) ,ResponseMessage.UPDATE_SUCCESS);
    }  catch (Exception e) {
        log.error("Lỗi cập nhật trạng thái đơn hàng: {}", e.getMessage());
        return ResponseHelper.badRequest("Lỗi cập nhật trạng thái đơn hàng: " + e.getMessage());
    }}

    @Override
    @Transactional
    public TypeResponse<Void> updateOrderStatusFromPayment(Long orderCode, String status, boolean cancel) {
        try {
            Order order = orderRepository.findByOrderCode(orderCode)
                    .orElseThrow(() -> new ResourceNotFoundEx("Không tìm thấy đơn hàng với orderCode: " + orderCode));

            Payment payment = order.getPayment();
            if (payment == null) {
                log.warn("Đơn hàng {} chưa có payment", order.getId());
                return ResponseHelper.notFound(ResponseMessage.PAYMENT_NOT_FOUND);
            }

            String finalStatus = status.toUpperCase();

            // HỦY HOẶC HẾT HẠN
            if (cancel || "CANCELLED".equals(finalStatus) || "EXPIRED".equals(finalStatus)) {
                if (order.getOrderStatus() == OrderStatus.CANCELLED) {
                    log.info("Đơn {} đã hủy trước đó", order.getId());
                    return ResponseHelper.validationError("status", "Đơn đã hủy trước đó");
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
                return  ResponseHelper.ok(null,ResponseMessage.UPDATE_SUCCESS);
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
                return  ResponseHelper.ok(null,ResponseMessage.UPDATE_SUCCESS);
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
            return ResponseHelper.notFound(ResponseMessage.ORDER_NOT_FOUND);
        } catch (Exception e) {
            log.error("Lỗi xử lý callback", e);
            return  ResponseHelper.badRequest("Lỗi cập nhật trạng thái đơn hàng: "+e.getMessage());
        }
        return ResponseHelper.ok(null,ResponseMessage.UPDATE_SUCCESS);
    }

    @Override
    public TypeResponse<OrderDetailDTO> getOrderByOrderCode(Long orderCode, Principal principal) {
        try {
            Order order = orderRepository.findByOrderCode(orderCode).get();
            return ResponseHelper.ok(convertToOrderDetailDTO(order), ResponseMessage.FETCH_SUCCESS);
        } catch (ResourceNotFoundEx e) {
            log.error("Không tìm thấy đơn hàng: {}", e.getMessage());
            return ResponseHelper.notFound(ResponseMessage.ORDER_NOT_FOUND);
        } catch (Exception e) {
            log.error("Lỗi lấy đơn hàng: {}", e.getMessage());
            return ResponseHelper.badRequest("Lỗi lấy đơn hàng: " + e.getMessage());
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
                .discountAmount(BigDecimal.valueOf(order.getPayment().getDiscountAmount()))
                .shippingFee(BigDecimal.valueOf(order.getPayment().getShippingFee()))
                .savedAmount(order.getPayment().getSavedAmount())
                .payment(order.getPayment())
                .orderItemList(order.getOrderItems().stream().map(this::toOrderItemDTO).toList())
                .expectedDeliveryDate(order.getExpectedDeliveryDate() != null ?
                        order.getExpectedDeliveryDate() : order.getCreatedAt().plusDays(3))
                .shipmentNumber(order.getShipmentTrackingNumber())
                .address(order.getAddress() != null ? modelMapper.map(order.getAddress(), AddressDTO.class) : null)
                .build();
    }

    private OrderItemDTO toOrderItemDTO(OrderItem oi) {
        return OrderItemDTO.builder()
                .productId(oi.getProduct().getId())
                .productName(oi.getProduct().getName())
                .productSlug(oi.getProduct().getSlug())
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