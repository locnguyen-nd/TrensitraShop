package com.trendistra.trendistashop.services.impl.order;

import com.trendistra.trendistashop.dto.request.OrderItemRequest;
import com.trendistra.trendistashop.dto.request.OrderRequest;
import com.trendistra.trendistashop.dto.response.OrderDetailDTO;
import com.trendistra.trendistashop.dto.response.OrderItemDetail;
import com.trendistra.trendistashop.entities.product.Product;
import com.trendistra.trendistashop.entities.product.ProductVariant;
import com.trendistra.trendistashop.entities.user.*;
import com.trendistra.trendistashop.enums.OrderStatus;
import com.trendistra.trendistashop.enums.PaymentStatus;
import com.trendistra.trendistashop.exceptions.OrderCancelException;
import com.trendistra.trendistashop.exceptions.OrderCreationException;
import com.trendistra.trendistashop.exceptions.ResourceNotFoundEx;
import com.trendistra.trendistashop.repositories.order.OrderRepository;
import com.trendistra.trendistashop.services.impl.product.ProductService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private  OrderRepository orderRepository;
    @Autowired
    private ProductService productService;

    @Transactional
    public Order createOrder (OrderRequest orderRequest, Principal principal) throws OrderCreationException {
        try{
            UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
            Address address = user.getAddressList()
                    .stream()
                    .filter(address1 -> Objects.equals(orderRequest.getAddressId(),address1.getId() ))
                    .findFirst()
                    .orElseThrow(
                            () -> new ResourceNotFoundEx("Address not found for the given ID")
                    );
            Order order = Order.builder()
                    .user(user)
                    .address(address)
                    .totalAmount(orderRequest.getTotalAmount())
                    .discount(orderRequest.getDiscount())
                    .expectedDeliveryDate(orderRequest.getExpectedDeliverDate())
                    .orderStatus(OrderStatus.PENDING)
                    .paymentMethod(orderRequest.getPaymentMethod())
                    .build();
            // Process order items
            List<OrderItem> orderItems = orderRequest.getOrderItems().stream()
                    .map(itemRequest -> {
                        try {
                            return processOrderItem(itemRequest, order);
                        } catch (OrderCreationException e) {
                            log.error("Error processing order items", e);
                            throw new RuntimeException("Error processing order items");
                        }
                    }) // Ensure processOrderItem returns OrderItem
                    .collect(Collectors.toList());
            order.setOrderItems(orderItems);
            // Create payment
            Payment payment = createPayment(order);
            order.setPayment(payment);
            // Save and return order
            return orderRepository.save(order);
        } catch (Exception e) {
            // Log the actual exception for debugging
            log.error("Order creation failed", e);
            throw new OrderCreationException("Failed to create order: " + e.getMessage());
        }
    }
    private OrderItem processOrderItem (OrderItemRequest itemRequest , Order order ) throws OrderCreationException {
        Product product = productService.getProductByIdEntity(itemRequest.getProductId());
        // Find product variant
        ProductVariant productVariant = product.getProductVariants().stream()
                .filter(variant -> Objects.equals(variant.getId(), itemRequest.getProductVariantId()))
                .findFirst()
                .orElseThrow(() -> new OrderCreationException("Invalid product variant"));
        // Check stock availability
        if (productVariant.getStockQuantity() < itemRequest.getQuantity()) {
            throw new OrderCreationException("Insufficient stock for product variant: " + productVariant.getId());
        }
        return OrderItem.builder()
                .product(product)
                .productVariantId(productVariant.getId())
                .quantity(itemRequest.getQuantity())
                .order(order)
                .build();
    }
    private Payment createPayment(Order order) {
        return Payment.builder()
                .paymentStatus(PaymentStatus.PENDING)
                .order(order)
                .paymentDate(new Date())
                .paymentMethod(order.getPaymentMethod())
                .amount(order.getTotalAmount())
                .build();
    }
    public List<OrderDetailDTO> getOrderByUser(String name) {
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(name);
        List<Order> orders = orderRepository.findByUser(user);

        return orders.stream()
                .map(order -> {
                    return  OrderDetailDTO.builder()

                                    .id(order.getId())
                                    .orderDate(order.getCreateAt())
                                    .orderStatus(order.getOrderStatus())
                                    .shipmentNumber(order.getShipmentTrackingNumber())
                                    .orderItemList(getItemDetails(order.getOrderItems()))
                                    .address(order.getAddress())
                                    .totalAmount(order.getTotalAmount())
                                    .expectedDeliveryDate(order.getExpectedDeliveryDate())
                                    .build();
                        }).toList();
    }

    private List<OrderItemDetail> getItemDetails(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem -> {
                    return OrderItemDetail.builder()
                                    .id(orderItem.getId())
                                    .itemPrice(orderItem.getItemPrice())
                                    .product(orderItem.getProduct())
                                    .productVariantId(orderItem.getProductVariantId())
                                    .quantity(orderItem.getQuantity())
                                    .build();
                        }).toList();
    }

    public  void cancelOrder(UUID id ,  Principal principal) {
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
        Order order = orderRepository.findById(id).get();
        // chỉ cho hủy khi chưa giao hàng
        if(null != order && order.getOrderStatus().equals(OrderStatus.PENDING) && order.getUser().getId().equals(user.getId())) {
            order.setOrderStatus(OrderStatus.CANCELLED);
            // refund amount
            /** Kiểm tra phương thức thanh toán
             * Nếu đơn đã thanh toón mà chưa  giao hàng thì hoàn tiền
             * Cập nhật trạng thái đơn hàng sang REFUNDED*/
        } else {
            new OrderCancelException("Cannot cancel this order ");
        }
    }
}
