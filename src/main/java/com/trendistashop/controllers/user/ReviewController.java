package com.trendistashop.controllers.user;

import com.trendistashop.constants.ResponseMessage;
import com.trendistashop.dto.response.PageDTO;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.dto.review.CreateReviewRequest;
import com.trendistashop.dto.review.ReviewResponse;
import com.trendistashop.dto.review.UpdateReviewRequest;
import com.trendistashop.entities.user.Review;
import com.trendistashop.services.impl.order.ReviewService;
import com.trendistashop.utils.ResponseHelper;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(description = "Tạo đánh giá với đơn hàng đã giao")
    public ResponseEntity<TypeResponse<List<ReviewResponse>>> createReview(
            @Valid @RequestBody CreateReviewRequest request,
            Principal principal) {
        TypeResponse<List<ReviewResponse>> response = reviewService.createReview(request, principal);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Cập nhật đánh giá
     */
    @PutMapping("/{reviewId}")
    @Operation(description = "Cập nhật lại đánh giá")
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
    @Operation(description = "Xóa đánh giá")
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
    @Operation(description = "Lọc đánh giá theo sản phẩm, nếu là User thì bỏ filter approved")
    public ResponseEntity<TypeResponse<PageDTO<ReviewResponse>>> getProductReviews(
            @PathVariable UUID productId,
            @RequestParam(required = false) Boolean recomment,
            @RequestParam(required = false) Boolean approved,
            @RequestParam(required = false) Integer rating,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        TypeResponse<PageDTO<ReviewResponse>> response = reviewService.getProductReviews(productId,recomment, approved, rating, page, size);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Lấy tất cả đánh giá của người dùng hiện tại
     */
    @GetMapping("/me")
    @Operation(description = "Lấy các đánh giá mà mình đã tạo")
    public ResponseEntity<TypeResponse<PageDTO<ReviewResponse>>> getMyReviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Principal principal) {
        TypeResponse<PageDTO<ReviewResponse>> response = reviewService.getMyReviews(page, size, principal);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @PutMapping("/approve/{reviewId}")
    @Operation(description = "Admin phê duyệt các đánh giá < 3 sao, đánh dấu recomment")
    public ResponseEntity<TypeResponse<ReviewResponse>> approveReview(
            @PathVariable UUID reviewId,
            @RequestParam Boolean approved,
            @RequestParam Boolean recomment,
            Principal principal ) {
        TypeResponse<ReviewResponse> response = reviewService.approveReview(reviewId, approved, recomment, principal);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
