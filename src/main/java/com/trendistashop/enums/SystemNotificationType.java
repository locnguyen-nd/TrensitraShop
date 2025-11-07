package com.trendistashop.enums;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Locnd
 */
@Getter
@RequiredArgsConstructor
public enum SystemNotificationType {
    ORDER_STATUS_UPDATED("Đơn hàng #{orderId} đã cập nhật", "Trạng thái mới: {status}"),
    ORDER_PAYMENT_SUCCESS("Thanh toán thành công!", "Đơn hàng #{orderId} đã được thanh toán."),
    ORDER_SHIPPED("Đơn hàng đã giao!", "Đơn hàng #{orderId} đang được vận chuyển."),
    ORDER_CANCELLED("Đơn hàng đã hủy", "Đơn hàng #{orderId} đã bị hủy."),
    USER_LOGIN("Đăng nhập thành công", "Chào mừng bạn quay lại!"),
    CHAT_NEW_MESSAGE("Tin nhắn mới", "Từ {senderName}: {message}"),
    PROMOTION("Khuyến mãi mới", "{promoTitle}");
    private final String titleTemplate;
    private final String contentTemplate;
}
