package com.trendistra.trendistashop.services.impl.order;

import com.trendistra.trendistashop.config.VietQRConfig;
import com.trendistra.trendistashop.dto.request.OrderRequest;
import com.trendistra.trendistashop.dto.response.*;
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
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
    private VietQRService vietQRService;
    @Autowired
    private VietQRConfig vietQRConfig;

    @Override
    public OrderDetailDTO updateOrderByOrder(OrderRequest orderRequest, Principal principal) {
        return null;
    }

    @Override
    public List<OrderDetailDTO> getAllOrder(Principal principal) {
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
        List<Order> orders = orderRepository.findByUser(user);
        return orders.stream()
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

    @Override
    @Transactional
    public OrderDetailDTO saveOrder(OrderRequest orderRequest, Principal principal) throws OrderCreationException {
        try {
            UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
            Cart useCart = user.getUserCart();
            if (useCart.getCartItems().isEmpty()) {
                throw new OrderCreationException("No products in cart");
            }
            List<UUID> listItemIds = orderRequest.getOrderItems()
                    .stream()
                    .map(CartItemDTO::getId) // Extract IDs
                    .collect(Collectors.toList()); // Collect into a List
            if (listItemIds == null || listItemIds.isEmpty()) {
                throw new OrderCreationException("No items selected for order");
            }
            List<CartItem> selectedItems = useCart.getCartItems().stream()
                    .filter(item -> listItemIds.contains(item.getId()))
                    .collect(Collectors.toList());
            if (selectedItems.isEmpty()) {
                new OrderCreationException("No items selected for order");
            }
            Address address = user.getAddressList()
                    .stream()
                    .filter(address1 -> Objects.equals(orderRequest.getAddressId(), address1.getId()))
                    .findFirst()
                    .orElseThrow(
                            () -> new ResourceNotFoundEx("Address not found for the given ID")
                    );
            BigDecimal totalAmount = calculateTotalAmount(selectedItems);
            String orderCode = generateOrderCode();
            Order order = new Order().builder()
                    .user(user)
                    .address(address)
                    .totalAmount(totalAmount)
                    .expectedDeliveryDate(calculateExpectedDeliveryDate())
                    .orderStatus(OrderStatus.PENDING)
                    .paymentMethod(orderRequest.getPaymentMethod())
                    .orderCoder(orderCode)
                    .note(orderRequest.getNote())
                    .expiredAt(LocalDateTime.now().plusMinutes(VietQRConfig.ORDER_TIMEOUT_MINUTES))
                    .orderDate(LocalDateTime.now())
                    .build();
            Order saveOrder = orderRepository.save(order);
            List<OrderItem> orderItems = processOrderItem(selectedItems, saveOrder);
            saveOrder.setOrderItems(orderItems);
            Payment payment = createPayment(saveOrder, vietQRService, orderRequest.getBankApp());
            log.info("Payment created: {}", payment);
            saveOrder.setPayment(payment);
            paymentRepository.save(payment);
            // Remove item in cart
            removeItemsFromCart(useCart, selectedItems);
            return convertToOrderDetailDTO(orderRepository.save(saveOrder));
        } catch (Exception e) {
            log.error("Order creation failed", e);
            throw new OrderCreationException("Failed to create order: " + e.getMessage());
        }
    }

    @Override
    public OrderDetailDTO getOrderByOrderId(UUID orderId) {
        return null;
    }

    @Override
    public List<OrderDetailDTO> getAllOrder() {
        return null;
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

    private String generateOrderCode() {
        return "ORD" + System.currentTimeMillis() +
                RandomStringUtils.randomNumeric(4);
    }

    private Date calculateExpectedDeliveryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7); // Default delivery time of 7 days
        return calendar.getTime();
    }

    private Payment createPayment(Order order, VietQRService vietQRService, String bankApp) {
        // Tao QR Code thanh toan
        String qrCode = null;
        String deeplinkUrl = null;
        if (order.getPaymentMethod().equals(PaymentMethod.QR_CODE)) {
            // Tạo QR Code thanh toán
            qrCode = vietQRService.generateQRCode(order);
            if (bankApp != null) {
                deeplinkUrl = String.format("https://dl.vietqr.io/pay?app=%s&ba=%s&am=%d&tn=%s",
                        bankApp, vietQRConfig.getAccountNo(), order.getTotalAmount().toBigInteger(), order.getOrderCoder());
            }
            log.info("Deep link generated: {}", deeplinkUrl);
        } else if (order.getPaymentMethod().equals(PaymentMethod.COD)) {
            log.info("Payment method is Cash on Delivery (COD). No QR code or deeplink required.");
        }

        return Payment.builder()
                .transactionId(null)
                .paymentStatus(PaymentStatus.CREATED)
                .order(order)
                .createdAt(LocalDateTime.now())
                .paidAt(null)
                .paymentMethod(order.getPaymentMethod())
                .amount(order.getTotalAmount())
                .qrCode(qrCode)
                .deepLink(deeplinkUrl)
                .build();
    }

    private OrderDetailDTO convertToOrderDetailDTO(Order order) {
        return OrderDetailDTO.builder()
                .id(order.getId())
                .orderDate(order.getCreateAt())
                .orderStatus(order.getOrderStatus())
                .shipmentNumber(order.getShipmentTrackingNumber())
                .orderItemList(getItemDetails(order.getOrderItems()))
                .address(modelMapper.map(order.getAddress(), AddressDTO.class))
                .totalAmount(order.getTotalAmount())
                .expectedDeliveryDate(order.getExpectedDeliveryDate())
                .payment(order.getPayment())
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
}
