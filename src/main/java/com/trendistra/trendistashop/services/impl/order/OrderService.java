package com.trendistra.trendistashop.services.impl.order;

import com.trendistra.trendistashop.config.PayOsConfig;
import com.trendistra.trendistashop.config.VietQRConfig;
import com.trendistra.trendistashop.dto.request.CreateOrder;
import com.trendistra.trendistashop.dto.request.OrderRequest;
import com.trendistra.trendistashop.dto.request.OrderStatusChangedEvent;
import com.trendistra.trendistashop.dto.response.*;
import com.trendistra.trendistashop.entities.product.Discount;
import com.trendistra.trendistashop.entities.product.Product;
import com.trendistra.trendistashop.entities.product.ProductVariant;
import com.trendistra.trendistashop.entities.user.*;
import com.trendistra.trendistashop.enums.OrderStatus;
import com.trendistra.trendistashop.enums.PaymentMethod;
import com.trendistra.trendistashop.enums.PaymentStatus;
import com.trendistra.trendistashop.exceptions.OrderCancelException;
import com.trendistra.trendistashop.exceptions.OrderCreationException;
import com.trendistra.trendistashop.exceptions.ResourceNotFoundEx;
import com.trendistra.trendistashop.repositories.order.CartRepository;
import com.trendistra.trendistashop.repositories.order.OrderRepository;
import com.trendistra.trendistashop.repositories.order.PaymentRepository;
import com.trendistra.trendistashop.repositories.product.ProductVariantRepository;
import com.trendistra.trendistashop.services.IOrderService;
import com.trendistra.trendistashop.services.impl.notification.OrderNotificationService;
import com.trendistra.trendistashop.services.impl.product.DiscountService;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductVariantRepository productVariantRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private DiscountService discountService;
    @Autowired
    private OrderNotificationService orderNotificationService;
    @Autowired
    private PayOsConfig payOsConfig;
    private final PayOS payOS ;

    public OrderService(PayOS payOS) {
        super();
        this.payOS = payOS;
    }

    @Override
    public OrderDetailDTO updateOrderStatus(UUID orderId,OrderStatus orderStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundEx("Order not found for ID: " + orderId));
        // Publish order status changed event
        orderNotificationService.handleOrderStatusChanged(new OrderStatusChangedEvent(
                order.getId(),
                order.getUser().getId(),
                order.getOrderCoder().toString(),
                order.getOrderStatus().name(),
                orderStatus.name()
        ));
        order.setOrderStatus(orderStatus);
        if(orderStatus.equals(OrderStatus.PROCESSING)) {
            order.getPayment().setPaymentStatus(PaymentStatus.PAID.name());
        }
        orderRepository.save(order);
        // gửi thôngbaoso qua mail and thông báo đơn hàng
        return convertToOrderDetailDTO(order);
    }

    @Override
    public List<OrderDetailDTO> getAllOrder(OrderStatus orderStatus, Principal principal) {
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
        List<Order> orders = orderRepository.findByUser(user);

        return orders.stream()
                .filter(order -> orderStatus == null || order.getOrderStatus() == orderStatus)
                .map(this::convertToOrderDetailDTO)
                .toList();
    }


    private List<OrderItemDTO> getItemDetails(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(this::convertToOrderItemDTO)
                .toList();
    }

    @Override
    public void cancelOrderByOrderId(UUID id, Principal principal) {
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
        Order order = orderRepository.findById(id).get();
        // chỉ cho hủy khi chưa giao hàng
        if (null != order && order.getOrderStatus().equals(OrderStatus.PENDING) && order.getUser().getId().equals(user.getId())) {
            order.setOrderStatus(OrderStatus.CANCELLED);
            List items = order.getOrderItems().stream().map(item -> item.getId()).collect(Collectors.toList());

            // refund amount
            /** Kiểm tra phương thức thanh toán
             * Nếu đơn đã thanh toón mà chưa  giao hàng thì hoàn tiền
             * Cập nhật trạng thái đơn hàng sang REFUNDED*/
        } else {
            new OrderCancelException("Cannot cancel this order ");
        }
        orderRepository.save(order);
    }

    /**
     * Creates an initial order when a user clicks "Buy Now"
     * This method stores minimal order information and product selections
     */
    @Override
    @Transactional
    public OrderDetailDTO createOrder(CreateOrder createOrder, Principal principal) throws OrderCreationException {
        try{
            UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
            Cart userCart = user.getUserCart();
            if (userCart.getCartItems().isEmpty()) {
                throw new OrderCreationException("No products in cart");
            }
            List<UUID> listItemIds = createOrder.getOrderItems();
            if (listItemIds == null || listItemIds.isEmpty()) {
                throw new OrderCreationException("No items selected for order");
            }
            List<CartItem> selectedItems = userCart.getCartItems().stream()
                    .filter(item -> listItemIds.contains(item.getId()))
                    .collect(Collectors.toList());
            if (selectedItems.isEmpty()) {
                new OrderCreationException("No items selected for order");
            }
            BigDecimal totalAmount = calculateTotalAmount(selectedItems);
            Long orderCode = generateOrderCode();
            Order order = new Order().builder()
                    .user(user)
                    .totalAmount(totalAmount)
                    .expectedDeliveryDate(calculateExpectedDeliveryDate())
                    .orderStatus(OrderStatus.CREATED)
                    .orderCoder(orderCode)
                    .orderDate(LocalDateTime.now())
                    .build();
            Order saveOrder = orderRepository.save(order);
            List<OrderItem> orderItems = processOrderItem(selectedItems, saveOrder);
            saveOrder.setOrderItems(orderItems);
            return convertToOrderDetailDTO(orderRepository.save(saveOrder));
        } catch (Exception e) {
            log.error("Order creation failed", e);
            throw new OrderCreationException("Failed to create order: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public OrderDetailDTO checkoutOrder(OrderRequest orderRequest, Principal principal) throws OrderCreationException {
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        try {
            UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
            // Tìm đơn hàng đã được tạo trước đó
            Order existingOrder = orderRepository.findById(orderRequest.getOrderId())
                    .orElseThrow(() -> new ResourceNotFoundEx("Order not found with ID: " + orderRequest.getOrderId()));

            // Kiểm tra đơn hàng thuộc về người dùng hiện tại
            if (!existingOrder.getUser().getId().equals(user.getId())) {
                throw new OrderCreationException("Unauthorized access to order");
            }
            // Xác định địa chỉ giao hàng
            Address address = user.getAddressList()
                    .stream()
                    .filter(addr -> Objects.equals(orderRequest.getAddressId(), addr.getId()))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundEx("Address not found for the given ID"));
            if(orderRequest.getDiscountValue() != null) {
                existingOrder.setTotalAmount(existingOrder.getTotalAmount().subtract(orderRequest.getDiscountValue()));
            }
            // Cập nhật thông tin còn thiếu cho đơn hàng
            existingOrder.setAddress(address);
            existingOrder.setPaymentMethod(PaymentMethod.valueOf(orderRequest.getPaymentMethod()));
            existingOrder.setOrderStatus(OrderStatus.PENDING);
            existingOrder.setNote(orderRequest.getNote());
            existingOrder.setExpiredAt(LocalDateTime.now().plusMinutes(VietQRConfig.ORDER_TIMEOUT_MINUTES));
            // Kiểm tra và cập nhật tồn kho
            List<OrderItem> orderItems = existingOrder.getOrderItems();
            for (OrderItem item : orderItems) {
                ProductVariant variant = productVariantRepository.findById(item.getProductVariantId())
                        .orElseThrow(() -> new OrderCreationException("Product variant not found: " + item.getProductVariantId()));

                if (variant.getStockQuantity() == null || variant.getStockQuantity() < item.getQuantity()) {
                    throw new OrderCreationException("Insufficient stock for product: " + item.getProduct().getName() +
                            ", variant: " + variant.getId());
                }

                // Cập nhật tồn kho
                variant.setStockQuantity(variant.getStockQuantity() - item.getQuantity());
                productVariantRepository.save(variant);
            }
            // Tạo thông tin thanh toán
            Payment payment = createPayment(existingOrder);
            log.info("Payment created: {}", payment);
            existingOrder.setPayment(payment);
            paymentRepository.save(payment);
            List<CartItem> itemsToRemove = extractCartItemsFromOrder(existingOrder, user.getUserCart());
            // Remove item in cart
            removeItemsFromCart(user.getUserCart(), itemsToRemove);
            Order completedOrder = orderRepository.save(existingOrder);
            orderDetailDTO = convertToOrderDetailDTO(completedOrder);
            return orderDetailDTO;
        } catch (Exception e) {
            log.error("Order creation failed", e);
            throw new OrderCreationException("Failed to create order: " + e.getMessage());
        }
    }
    private List<CartItem> extractCartItemsFromOrder(Order order, Cart userCart) {
        List<UUID> variantIds = order.getOrderItems().stream()
                .map(OrderItem::getProductVariantId)
                .collect(Collectors.toList());

        return userCart.getCartItems().stream()
                .filter(cartItem -> variantIds.contains(cartItem.getProductVariantId()))
                .collect(Collectors.toList());
    }
    private List<OrderItem> processOrderItem(List<CartItem> cartItems, Order order) {
        return cartItems.stream()
                .map(cartItem -> {
                    try {
                        if (cartItem == null || cartItem.getCartProduct() == null) {
                            throw new OrderCreationException("Invalid cart item or product is null");
                        }
                        return covertCartItemToOrderItem(cartItem, order);
                    } catch (OrderCreationException e) {
                        log.error("Failed to process cart item: {}", cartItem.getId(), e);
                        throw new RuntimeException("Failed to process order item: " + e.getMessage(), e);
                    }
                })
                .collect(Collectors.toList());
    }

    private OrderItem covertCartItemToOrderItem(CartItem cartItem, Order order) throws OrderCreationException {
        Product product = cartItem.getCartProduct();
        if (product == null) {
            throw new OrderCreationException("Product not found for cart item: " + cartItem.getId());
        }

        ProductVariant productVariant = product.getProductVariants().stream()
                .filter(variant -> variant != null && variant.getId() != null &&
                        variant.getId().equals(cartItem.getProductVariantId()))
                .findFirst()
                .orElseThrow(() -> new OrderCreationException("Product variant not found for ID: " +
                        cartItem.getProductVariantId()));
        System.out.println(productVariant.getStockQuantity());
        if (productVariant.getStockQuantity() == null || productVariant.getStockQuantity() < cartItem.getCartItemQuantity()) {
            throw new OrderCreationException("Insufficient stock for product: " + product.getName() +
                    ", variant: " + productVariant.getId());
        }
        productVariant.setStockQuantity(productVariant.getStockQuantity() - cartItem.getCartItemQuantity());
        System.out.println(productVariant.getStockQuantity());
        productVariantRepository.save(productVariant);

        return OrderItem.builder()
                .itemPrice(product.getPrice())
                .product(product)
                .productVariantId(productVariant.getId())
                .quantity(cartItem.getCartItemQuantity())
                .order(order)
                .build();
    }

    private void removeItemsFromCart(Cart cart, List<CartItem> itemsToRemove) {
        if (cart == null || itemsToRemove == null || itemsToRemove.isEmpty()) {
            return;
        }
        BigDecimal totalToRemove = itemsToRemove.stream()
                .map(item -> item.getCartProduct().getPrice().multiply(BigDecimal.valueOf(item.getCartItemQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setCartTotal(cart.getCartTotal().subtract(totalToRemove));
        List<CartItem> listItem = cart.getCartItems();
        listItem.removeAll(itemsToRemove);
        cart.setCartItems(listItem); // Correctly remove from cart's list
        cartRepository.save(cart);
        System.out.println("Delete item success");
    }

    private BigDecimal calculateTotalAmount(List<CartItem> items) {
        return items.stream()
                .map(item -> item.getCartProduct().getPrice()
                        .multiply(BigDecimal.valueOf(item.getCartItemQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Long generateOrderCode() {
        String currentTimeString = String.valueOf(new Date().getTime());
        long orderCode = Long.parseLong(currentTimeString.substring(currentTimeString.length() - 6));
        return orderCode;
    }

    private Date calculateExpectedDeliveryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7); // Default delivery time of 7 days
        return calendar.getTime();
    }

    private Payment createPayment(Order order) throws Exception {
        Payment payment;

        if (order.getPaymentMethod().equals(PaymentMethod.QR)) {
            // Với QR: Tạo dữ liệu thanh toán và gọi API PayOS để nhận CheckoutResponseData chứa thông tin thanh toán (qrCode, checkoutUrl,...)
            PaymentData paymentData = PaymentData.builder()
                    .orderCode(order.getOrderCoder())
                    .amount(order.getTotalAmount().intValue())
                    .description("TT hóa đơn " + order.getOrderCoder())
                    .items(order.getOrderItems().stream()
                            .map(item -> ItemData.builder()
                                    .name(item.getProduct().getName())
                                    .quantity(item.getQuantity())
                                    .price(item.getItemPrice().intValue())
                                    .build())
                            .collect(Collectors.toList()))
                    .cancelUrl(payOsConfig.getCancelUrl())
                    .returnUrl(payOsConfig.getReturnUrl())
                    .build();

            CheckoutResponseData checkoutResponseData = payOS.createPaymentLink(paymentData);

            payment = Payment.builder()
                    .transactionId(checkoutResponseData.getOrderCode())  // Mã giao dịch từ PayOS
                    .paymentStatus(checkoutResponseData.getStatus())       // Trạng thái trả về từ PayOS
                    .order(order)
                    .createdAt(LocalDateTime.now())
                    .paidAt(null)
                    .paymentMethod(PaymentMethod.QR)
                    .amount(checkoutResponseData.getAmount())
                    .qrCode(checkoutResponseData.getQrCode())              // Mã QR nhận được
                    .deepLink(checkoutResponseData.getCheckoutUrl())       // URL thanh toán
                    .build();
        } else if (order.getPaymentMethod().equals(PaymentMethod.COD)) {
            // Với COD: Không cần gọi API bên ngoài. Tạo Payment trực tiếp với trạng thái mặc định là CREATED
            payment = Payment.builder()
                    .transactionId(order.getOrderCoder())         // Mã giao dịch tự sinh cho COD
                    .paymentStatus(String.valueOf(PaymentStatus.CREATED))                   // COD thường được gán trạng thái CREATED ban đầu
                    .order(order)
                    .createdAt(LocalDateTime.now())
                    .paidAt(null)
                    .paymentMethod(PaymentMethod.COD)
                    .amount(order.getTotalAmount().intValue())
                    .qrCode(null)
                    .deepLink(null)
                    .build();
        } else {
            throw new Exception("Unsupported payment method: " + order.getPaymentMethod());
        }

        return payment;
    }
    /**
     * Cập nhật trạng thái đơn hàng dựa vào kết quả thanh toán từ PayOS.
     * Nếu thanh toán bằng QR:
     *    - Nếu trạng thái SUCCESS thì chuyển order sang PAID.
     *    - Nếu trạng thái khác SUCCESS thì giữ trạng thái cho phép người dùng retry (ví dụ: PENDING_PAYMENT).
     * Nếu thanh toán bằng COD:
     *    - Chuyển đơn hàng sang trạng thái PENDING_APPROVAL để chờ admin duyệt.
     */
    @Override
    public void updateOrderStatusFromPayment(Long transactionId, String newPaymentStatus) throws Exception {
        Payment payment = paymentRepository.findByTransactionId(transactionId);
        if (payment == null) {
            throw new Exception("Không tìm thấy Payment với transactionId: " + transactionId);
        }
        Order order = payment.getOrder();
        String updatedStatus = newPaymentStatus;
        System.out.println(updatedStatus);
        // Cập nhật trạng thái Payment (có thể thực hiện trước)
        payment.setPaymentStatus(updatedStatus);
        if (updatedStatus.equals(PaymentStatus.PAID.name())) {
            payment.setPaidAt(LocalDateTime.now());
            order.setOrderStatus(OrderStatus.PROCESSING);
        } else {
            payment.setPaymentStatus(PaymentStatus.FAILED.name());
        }

        paymentRepository.save(payment);
        orderRepository.save(order);
}

    @Transactional
    @Override
    public   OrderDetailDTO retryPayment (UUID orderId, String paymentMethod) throws Exception {
        Order order = orderRepository.findById(orderId).get();
        Payment newPayment = new Payment();
        if (order == null) {
            throw new ResourceNotFoundEx("Order not found for ID: " + orderId);
        }
        // Kiểm tra hình thức thanh toán và trạng thái đơn hàng
        if (!order.getPayment().getPaymentMethod().equals(PaymentMethod.QR)) {
            throw new OrderCreationException("Order payment method is not QR. Retry payment not applicable.");
        }
        if (!order.getOrderStatus().equals(OrderStatus.PENDING)) {
            throw new OrderCreationException("Order is not pending payment.");
        }
        if (order.getPayment().getPaymentStatus().equals(PaymentStatus.FAILED.name())) {
            throw new OrderCreationException("Payment is success.");
        }
        if(paymentMethod != null) {
            order.setPaymentMethod(PaymentMethod.valueOf(paymentMethod));
            if(order.getPaymentMethod().equals(PaymentMethod.COD)) {
                order.setOrderStatus(OrderStatus.PROCESSING);
            }
        }
        // Nếu Payment hiện tại chưa hết hạn, không cho phép retry
        if (order.getExpiredAt() != null && LocalDateTime.now().isBefore(order.getExpiredAt())) {
            newPayment = order.getPayment();
        }
        // Nếu QR cũ đã hết hạn, cập nhật trạng thái của Payment cũ thành FAILED
        if (order.getPayment() != null && order.getExpiredAt() != null && LocalDateTime.now().isAfter(order.getExpiredAt())) {
            Long orderCode = generateOrderCode();
            order.setOrderCoder(orderCode);
            PaymentData paymentData = PaymentData.builder()
                    .orderCode(order.getOrderCoder())
                    .amount(order.getTotalAmount().intValue())
                    .description("TT hóa đơn " + order.getOrderCoder())
                    .items(order.getOrderItems().stream()
                            .map(item -> ItemData.builder()
                                    .name(item.getProduct().getName())
                                    .quantity(item.getQuantity())
                                    .price(item.getItemPrice().intValue())
                                    .build())
                            .collect(Collectors.toList()))
                    .cancelUrl(payOsConfig.getCancelUrl())
                    .returnUrl(payOsConfig.getReturnUrl())
                    .build();

            CheckoutResponseData checkoutResponseData = payOS.createPaymentLink(paymentData);
            Payment oldPayment = order.getPayment();
                    oldPayment.setTransactionId(checkoutResponseData.getOrderCode());
                    oldPayment.setPaymentStatus(checkoutResponseData.getStatus());
                    oldPayment.setPaymentMethod(PaymentMethod.QR);
                    oldPayment.setAmount(checkoutResponseData.getAmount());
                    oldPayment.setQrCode(checkoutResponseData.getQrCode());
                    oldPayment.setDeepLink(checkoutResponseData.getCheckoutUrl());
                    oldPayment.setCreatedAt(LocalDateTime.now());
                    oldPayment.setPaidAt(LocalDateTime.now().plusMinutes(PayOsConfig.ORDER_TIMEOUT_MINUTES));
                    oldPayment.setOrder(order);
            log.info("New deep link generated: {}", oldPayment);
            paymentRepository.save(oldPayment);
            log.info("Old QR payment updated.");
            newPayment = oldPayment;
        }
        // Cập nhật lại đơn hàng: gán Payment mới và reset thời gian hết hạn (ví dụ: thêm 10 phút)
        order.setPayment(newPayment);// Lưu lại đơn hàng và Payment mới
        orderRepository.save(order);
        return convertToOrderDetailDTO(order);
    }

    public OrderDetailDTO convertToOrderDetailDTO(Order order) {

        return OrderDetailDTO.builder()
                .id(order.getId())
                .orderDate(order.getCreateAt())
                .discountApply(order.getDiscount() != null ? convertDiscount(order) : null)
                .orderStatus(order.getOrderStatus())
                .shipmentNumber(order.getShipmentTrackingNumber())
                .orderItemList(getItemDetails(order.getOrderItems()))
                .address(order.getAddress() != null ? modelMapper.map(order.getAddress(), AddressDTO.class) : null)
                .totalAmount(order.getTotalAmount())
                .expectedDeliveryDate(order.getExpectedDeliveryDate())
                .payment(order.getPayment() != null ? order.getPayment() : null)
                .build();
    }
    public DiscountApply convertDiscount(Order order) {
        BigDecimal originPrice = order.getOrderItems().stream()
                .findFirst()
                .map(orderItem -> orderItem.getProduct().getOriginPrice())
                .orElse(BigDecimal.ZERO);
        BigDecimal price = order.getOrderItems().stream()
                .findFirst()
                .map(orderItem -> orderItem.getProduct().getPrice())
                .orElse(BigDecimal.ZERO);
        BigDecimal saved = originPrice.subtract(order.getTotalAmount());
        BigDecimal valueApply = price.subtract(order.getTotalAmount());
        return DiscountApply.builder()
                .code(order.getDiscount().getCode())
                .valueApply(valueApply)
                .saved(saved)
                .build();
    }


    private OrderItemDTO convertToOrderItemDTO(OrderItem orderItem) {
        return OrderItemDTO.builder()
                .id(orderItem.getId())
                .itemPrice(orderItem.getItemPrice())
                .productId(orderItem.getProduct().getId())
                .productName(orderItem.getProduct().getName())
                .productSlug(orderItem.getProduct().getSlug())
                .variantDTO(getVariantDTO(orderItem.getProductVariantId()))
                .quantity(orderItem.getQuantity())
                .build();
    }

    private VariantDTO getVariantDTO(UUID variantId) {
        return productVariantRepository.findById(variantId)
                .map(variant -> modelMapper.map(variant, VariantDTO.class))
                .orElse(null); // Handle the case where the variant is not found
    }
    @Scheduled(fixedRate = 60000)
    public void checkOrderStatus() {
        LocalDateTime now = LocalDateTime.now();
        List<Order> expiredOrders = orderRepository.findAllByExpiredAtBeforeAndPayment_PaymentStatus( now,PaymentStatus.CREATED);
        if (expiredOrders.isEmpty()) {
            return;
        }
        for (Order order : expiredOrders) {
            Payment payment = order.getPayment();
            if(payment != null) {
//                payment.setPaymentStatus(PaymentStatus.FAILED);
                paymentRepository.save(payment);
                log.info("Cập nhật Order {}: Payment chuyển sang PAID", order.getId());
            }
        }
    }
}
