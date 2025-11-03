package com.trendistashop.repositories.order;

import com.trendistashop.entities.user.Order;
import com.trendistashop.entities.user.UserEntity;
import com.trendistashop.enums.OrderStatus;
import com.trendistashop.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    Page<Order> findByUser(UserEntity user, Pageable pageable);

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
     * Đếm số đơn hàng theo trạng thái thanh toán
     */
    @Query("SELECT o FROM Order o WHERE o.createdAt BETWEEN :from AND :to")
    List<Order> findByCreatedAtBetween(@Param("from") LocalDateTime from,
                                       @Param("to") LocalDateTime to);

    @Query("SELECT o.orderStatus, COUNT(o), COALESCE(SUM(o.totalAmount), 0) " +
            "FROM Order o WHERE o.createdAt BETWEEN :start AND :end " +
            "GROUP BY o.orderStatus")
    List<Object[]> countOrdersByStatus(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("""
    SELECT p, SUM(oi.quantity), SUM(oi.itemPrice * oi.quantity)
    FROM Order o
    JOIN o.orderItems oi
    JOIN oi.product p
    WHERE o.createdAt BETWEEN :start AND :end
      AND o.orderStatus IN ('COMPLETED', 'DELIVERED')
    GROUP BY p.id, p.name
    ORDER BY SUM(oi.quantity) DESC
    """)
    List<Object[]> findTopSellingProducts(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            Pageable pageable
    );

    @Query("SELECT o FROM Order o WHERE o.createdAt BETWEEN :from AND :to AND o.orderStatus = :status")
    List<Order> findByCreatedAtBetweenAndStatus(
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            @Param("status") OrderStatus status
    );
    Optional<Order> findByShipmentTrackingNumber(String orderCode);
}
