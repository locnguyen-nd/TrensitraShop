package com.trendistra.trendistashop.services.impl.notification;

import com.trendistra.trendistashop.dto.request.OrderStatusChangedEvent;
import com.trendistra.trendistashop.entities.user.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@Slf4j
public class OrderNotificationService {
    private final NotificationEventHandler notificationEventHandler;

    public OrderNotificationService(NotificationEventHandler notificationEventHandler) {
        this.notificationEventHandler = notificationEventHandler;
    }
    /**
     * Listen for order placement events and send notifications
     */
    @TransactionalEventListener
    public void handleOrderPlacedEvent(Order order) {
        log.info("Handling order placed event for order: {}", order.getId());
        notificationEventHandler.notifyOrderPlaced(
                order.getUser().getId(),
                order.getId(),
                order.getOrderCoder().toString()
        );
    }
    /**
     * Listen for order status change events and send notifications
     */
    @EventListener
    public void handleOrderStatusChanged(OrderStatusChangedEvent event) {
        log.info("Handling order status changed event: {} -> {}",
                event.getOrderId(), event.getNewStatus());

        notificationEventHandler.notifyOrderStatusChanged(
                event.getUserId(),
                event.getOrderId(),
                event.getOrderNumber(),
                event.getNewStatus()
        );
    }
}
