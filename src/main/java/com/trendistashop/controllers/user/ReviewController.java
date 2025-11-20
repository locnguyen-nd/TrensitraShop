package com.trendistashop.controllers.user;

import com.trendistashop.constants.ResponseMessage;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.dto.review.CreateReviewRequest;
import com.trendistashop.dto.review.ReviewResponse;
import com.trendistashop.dto.review.UpdateReviewRequest;
import com.trendistashop.entities.user.Review;
import com.trendistashop.services.impl.order.ReviewService;
import com.trendistashop.utils.ResponseHelper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author Locnd
 */
@RestController
@RequestMapping(value = "${api.prefix}/review")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Review API", description = "API quản lý review, đánh giá của khách hàng")
public class ReviewController {
    private final ReviewService reviewService;
    /**
     * Tạo đánh giá mới cho sản phẩm trong đơn hàng đã giao
     */
    @PostMapping
    public ResponseEntity<TypeResponse<List<ReviewResponse>>> createReview(
            @Valid @RequestBody CreateReviewRequest request,
            Principal principal) {
        TypeResponse<List<ReviewResponse>> response = reviewService.createReview(request, principal);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Cập nhật đánh giá (chỉ được sửa nếu chưa duyệt hoặc theo chính sách)
     */
    @PutMapping("/{reviewId}")
    public ResponseEntity<TypeResponse<ReviewResponse>> updateReview(
            @PathVariable UUID reviewId,
            @Valid @RequestBody UpdateReviewRequest request,
            Principal principal) {
        TypeResponse<ReviewResponse> response = reviewService.updateReview(reviewId, request, principal);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Xóa đánh giá (chỉ người tạo)
     */
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<TypeResponse<Void>> deleteReview(
            @PathVariable UUID reviewId,
            Principal principal) {

        TypeResponse<Void> response = reviewService.deleteReview(reviewId, principal);
        return  ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Lấy danh sách đánh giá của sản phẩm
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<TypeResponse<Page<ReviewResponse>>> getProductReviews(
            @PathVariable UUID productId,
            @RequestParam(required = false) Boolean approved,
            @RequestParam(required = false) List<Integer> rating,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        TypeResponse<Page<ReviewResponse>> response = reviewService.getProductReviews(productId, approved, rating, page, size);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Lấy tất cả đánh giá của người dùng hiện tại
     */
    @GetMapping("/me")
    public ResponseEntity<TypeResponse<Page<ReviewResponse>>> getMyReviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Principal principal) {
        TypeResponse<Page<ReviewResponse>> response = reviewService.getMyReviews(page, size, principal);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @PatchMapping("/approve/{reviewId}")
    public ResponseEntity<TypeResponse<ReviewResponse>> approveReview(
            @PathVariable UUID reviewId,
            @RequestParam Boolean approved,
            Principal principal ) {
        TypeResponse<ReviewResponse> response = reviewService.approveReview(reviewId, approved, principal);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
