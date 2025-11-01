package com.trendistashop.repositories.order;

import com.trendistashop.entities.user.Order;
import com.trendistashop.entities.user.UserEntity;
import com.trendistashop.enums.OrderStatus;
import com.trendistashop.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    /**
     * Tìm tất cả đơn hàng của một user
     */
    List<Order> findByUser(UserEntity user);

    /**
     * Tìm đơn hàng theo user và trạng thái
     */
    List<Order> findByUserAndOrderStatus(UserEntity user, OrderStatus status);

    /**
     * Tìm các đơn hàng đã hết hạn thanh toán (PENDING + Payment = CREATED) để tự động hủy
     */
    @Query("""
        SELECT o FROM Order o
        JOIN o.payment p
        WHERE o.expiredAt < :now
          AND o.orderStatus = 'PENDING'
          AND p.paymentStatus = :paymentStatus
        """)
    List<Order> findExpiredPendingOrders(
            @Param("now") LocalDateTime now,
            @Param("paymentStatus") String paymentStatus
    );

    /**
     * Tìm đơn hàng theo mã đơn (orderCoder) - dùng cho webhook
     */
    @Query("SELECT o FROM Order o WHERE o.orderCode = :orderCode")
    Optional<Order> findByOrderCode(@Param("orderCode") Long orderCode);

    /**
     * Tìm đơn hàng theo trạng thái và user (phân trang nếu cần)
     */
    List<Order> findByUserAndOrderStatusIn(UserEntity user, List<OrderStatus> statuses);

    /**
     * Đếm số đơn hàng theo trạng thái
     */
    long countByUserAndOrderStatus(UserEntity user, OrderStatus status);
}
