package com.trendistashop.repositories.order;

import com.trendistashop.entities.user.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author Locnd
 */
@Repository
public interface ReviewRepository  extends JpaRepository<Review, UUID> {
    // Kiểm tra user đã đánh giá sản phẩm trong đơn hàng này chưa
    boolean existsByUserIdAndProductIdAndOrderId(UUID userId, UUID productId, UUID orderId);

    // Lấy đánh giá của user theo đơn hàng
    List<Review> findByUserIdAndOrderId(UUID userId, UUID orderId);

    // Lấy tất cả đánh giá của user
    Page<Review> findByUserId(UUID userId, Pageable pageable);

    @Query("""
    SELECT r FROM Review r 
    WHERE r.product.id = :productId
      AND (:recomment IS NULL OR r.isRecommended = :recomment)
      AND (:approved IS NULL OR r.isApproved = :approved)
      AND (:minRating IS NULL OR r.rating >= :minRating)
      AND (:maxRating IS NULL OR r.rating < :maxRating)
    """)
    Page<Review> findReviewsWithFilter(
            @Param("productId") UUID productId,
            @Param("recomment") Boolean recomment,
            @Param("approved") Boolean approved,
            @Param("minRating") Double minRating,
            @Param("maxRating") Double maxRating,
            Pageable pageable
    );
    // Lấy tất cả đánh giá của sản phẩm (admin)
    Page<Review> findByProductId(UUID productId, Pageable pageable);

    // Đếm và tính trung bình rating đã duyệt
    @Query("""
       SELECT COALESCE(AVG(r.rating), 0.0), COUNT(r.id)
       FROM Review r
       WHERE r.product.id = :productId 
         AND r.isApproved = true
       """)
    Object[] calculateAverageRatingAndCount(@Param("productId") UUID productId);
    Optional<Review> findByIdAndUserId(UUID reviewId, UUID userId);
}
