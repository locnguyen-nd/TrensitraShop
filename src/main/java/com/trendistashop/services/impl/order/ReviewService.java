package com.trendistashop.services.impl.order;

import com.trendistashop.constants.ResponseMessage;
import com.trendistashop.dto.chat.notification.UserNotificationDTO;
import com.trendistashop.dto.response.PageDTO;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.dto.review.*;
import com.trendistashop.entities.BaseEntity;
import com.trendistashop.entities.product.Product;
import com.trendistashop.entities.user.*;
import com.trendistashop.enums.GuardType;
import com.trendistashop.enums.NotificationType;
import com.trendistashop.enums.OrderStatus;
import com.trendistashop.enums.SystemNotificationType;
import com.trendistashop.repositories.order.OrderRepository;
import com.trendistashop.repositories.order.ReviewReplyRepository;
import com.trendistashop.repositories.order.ReviewRepository;
import com.trendistashop.repositories.product.ProductRepository;
import com.trendistashop.services.CloudinaryService;
import com.trendistashop.services.impl.notification.NotificationService;
import com.trendistashop.utils.ResponseHelper;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
/**
 *
 * @author Locnd
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserDetailsService userDetailsService;
    private final ReviewReplyRepository reviewReplyRepository;
    private final NotificationService notificationService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private EntityManager entityManager;
    @Transactional
    public TypeResponse<List<ReviewResponse>> createReview(CreateReviewRequest req, Principal principal) {
        try {
        UserEntity user = getUser(principal);
        if (user == null) return ResponseHelper.unauthorized(ResponseMessage.UNAUTHORIZED);
        Optional<Order> orderOpt = orderRepository.findById(req.orderId());
        if (orderOpt.isEmpty()) {
            return ResponseHelper.notFound(ResponseMessage.ORDER_NOT_FOUND);
        }
        Order order = orderOpt.get();
        if (!OrderStatus.DELIVERED.equals(order.getOrderStatus())) {
            return ResponseHelper.badRequest(ResponseMessage.ORDER_UNFINISHED);
        }
        List<OrderItem> orderItems = order.getOrderItems();
        if (orderItems.isEmpty()) {
            return ResponseHelper.badRequest(ResponseMessage.PRODUCT_NOT_ORDER);
        }
        // Lấy danh sách sản phẩm -> xác định sản phẩm gốc --> thêm đánh giá cho product root
        Map<UUID, Product> rootProductMap = new LinkedHashMap<>();
        for (OrderItem orderItem : orderItems) {
            Product product = orderItem.getProduct();
            rootProductMap.putIfAbsent(product.getId(), product);
        }
        if (rootProductMap.isEmpty()) {
            return ResponseHelper.badRequest(ResponseMessage.PRODUCT_NOT_ORDER);
        }

        List<Review> createdReviews = new ArrayList<>();
        for (Product product : rootProductMap.values()) {
            UUID productId = product.getId();
            boolean alreadyReviewed = reviewRepository.existsByUserIdAndProductIdAndOrderId(
                    user.getId(), productId, order.getId());
            if (alreadyReviewed) {
                log.info("User {} đã đánh giá sản phẩm {} trong đơn {} → bỏ qua", user.getFullName(), productId, order.getOrderCode());
                continue;
            }
            Review review = Review.builder()
                    .user(user)
                    .product(product)
                    .order(order)
                    .rating(req.rating())
                    .content(req.content())
                    .isRecommended(req.rating() >= 4)  // Nếu > 4 thì recommend
                    .isApproved(req.rating() >= 3)  // đánh giá mà > 3 thì duyệt luôn
                    .build();

            if (req.mediaUrls() != null && !req.mediaUrls().isEmpty()) {
                req.mediaUrls().stream()
                        .filter(Objects::nonNull)
                        .map(String::trim)
                        .filter(url -> !url.isEmpty())
                        .forEach(review::addMedia);
            }
            Review saved = reviewRepository.save(review);
            createdReviews.add(saved);
            log.info("Tạo đánh giá thành công cho đơn hàng: {} ",order.getOrderCode());
        }
        if (createdReviews.isEmpty()) {
            return ResponseHelper.validationError(
                    "duplicate",
                    "Đơn hàng đã được đánh giá !"
            );
        }
         createdReviews.forEach(review -> updateProductRating(review.getProduct().getId()));
        List<ReviewResponse> responses = createdReviews.stream()
                .map(this::toResponse)
                .toList();
        return ResponseHelper.created(responses, ResponseMessage.CREATE_SUCCESS);
    } catch (Exception e) {
            log.info("Exception create review : ", e);
            return ResponseHelper.badRequest(ResponseMessage.CREATE_FAILED);
        }
    }
    @Transactional
    public TypeResponse<ReplyResponse> createReply(CreateReplyRequest req, Principal principal) {
        try {
            UserEntity currentUser = getUser(principal);
            if (currentUser == null) return ResponseHelper.unauthorized(ResponseMessage.UNAUTHORIZED);

            Review review = reviewRepository.findById(req.reviewId())
                    .orElse(null);
            if (review == null) {
                return ResponseHelper.notFound("Review không tồn tại");
            }
            ReviewReply parent = null;
            if (req.parentReplyId() != null) {
                parent = reviewReplyRepository.findById(req.parentReplyId()).orElse(null);
                if (parent == null || !parent.getReview().getId().equals(req.reviewId())) {
                    return ResponseHelper.badRequest("Parent reply không hợp lệ");
                }
            }
            ReviewReply reply = ReviewReply.builder()
                    .review(review)
                    .user(currentUser)
                    .content(req.content().trim())
                    .parentReply(parent)
                    .isAdminReply(currentUser.getRoles().stream()
                            .anyMatch(r -> GuardType.ADMIN.equals(r.getName())))
                    .children(new ArrayList<>())
                    .build();

            if (parent != null) {
                parent.addChild(reply);
            }
            ReviewReply saved = reviewReplyRepository.save(reply);
            // Gửi thông báo realtime + hệ thống
            sendReplyNotification(saved, currentUser);
            return ResponseHelper.created(toReplyResponse(saved), ResponseMessage.CREATE_SUCCESS );
        } catch (Exception e) {
            log.error("Lỗi tạo reply", e);
            return ResponseHelper.badRequest(ResponseMessage.CREATE_FAILED);
        }
    }
    // Lấy replies theo review (chỉ top-level, children sẽ được map đệ quy)
    public List<ReplyResponse> getRepliesByReview(UUID reviewId) {
        List<ReviewReply> topLevel = reviewReplyRepository.findByReviewIdAndParentReplyIsNullOrderByCreatedAtAsc(reviewId);
        return topLevel.stream()
                .map(this::toReplyResponse)
                .toList();
    }

    @Transactional
    public TypeResponse<ReviewResponse> updateReview (UUID reviewId, UpdateReviewRequest req, Principal principal) {
        try {
            UserEntity user = getUser(principal);
            if (user == null) return ResponseHelper.unauthorized(ResponseMessage.UNAUTHORIZED);
           Optional<Review> review = reviewRepository.findById(reviewId);
           if (review.isEmpty()) {
               log.info("Review id {} not found", reviewId);
               return ResponseHelper.badRequest(ResponseMessage.NOT_FOUND);
           }
           Review reviewUp = review.get();
            if (req.mediaUrls() != null) {
                List<String> newUrls = new ArrayList<>(req.mediaUrls());
                for (String oldUrl : reviewUp.getMediaUrls()) {
                    if (!newUrls.contains(oldUrl)) {
                        try {
                            cloudinaryService.deleteFile(oldUrl);
                            log.info("Deleted old media from Cloudinary: {}", oldUrl);
                        } catch (Exception e) {
                            log.warn("Failed to delete media {}: {}", oldUrl, e.getMessage());
                        }
                    }
                }
            }
            review.get().getMediaUrls().clear();
            if (req.mediaUrls() != null && !req.mediaUrls().isEmpty()) {
                req.mediaUrls().stream()
                        .filter(Objects::nonNull)
                        .map(String::trim)
                        .filter(url -> !url.isEmpty())
                        .forEach(review.get()::addMedia);
            }
           reviewUp.setRating(req.rating());
           reviewUp.setContent(req.content());
           reviewUp.setIsRecommended(req.rating() >= 4);
           reviewUp.setIsApproved(req.rating() >= 3);
           Review reviewReturn = reviewRepository.save(reviewUp);
           updateProductRating(review.get().getProduct().getId());
           return ResponseHelper.created(this.toResponse(reviewReturn), ResponseMessage.UPDATE_SUCCESS);
        } catch (Exception e) {
            log.info("Exception update review : ", e);
            return ResponseHelper.badRequest(ResponseMessage.UPDATE_FAILED);
        }
    }
    @Transactional
    public TypeResponse<ReplyResponse> updateReply(UUID replyId, UpdateReplyRequest req, Principal principal) {
        try {
            UserEntity currentUser = getUser(principal);
            if (currentUser == null) return ResponseHelper.unauthorized(ResponseMessage.UNAUTHORIZED);

            ReviewReply reply = reviewReplyRepository.findById(replyId)
                    .orElse(null);
            if (reply == null) {
                return ResponseHelper.notFound(ResponseMessage.NOT_FOUND);
            }

            if (!canEditReply(reply)) {
                return ResponseHelper.forbidden(ResponseMessage.FORBIDDEN);
            }

            reply.setContent(req.content().trim());
            ReviewReply updated = reviewReplyRepository.save(reply);

            return ResponseHelper.ok(toReplyResponse(updated), ResponseMessage.UPDATE_SUCCESS);

        } catch (Exception e) {
            log.error("Lỗi cập nhật reply", e);
            return ResponseHelper.badRequest(ResponseMessage.UPDATE_FAILED);
        }
    }
    @Transactional
    public TypeResponse<Void> deleteReview (UUID reviewId, Principal principal) {
        try{
            UserEntity user = getUser(principal);
            if (user == null) return ResponseHelper.unauthorized(ResponseMessage.UNAUTHORIZED);
            Optional<Review> reviewOpt = reviewRepository.findById(reviewId);
            if (reviewOpt.isEmpty()) {
                log.info("Review id {} not found", reviewId);
                return ResponseHelper.badRequest(ResponseMessage.NOT_FOUND);
            }
            Review review = reviewOpt.get();
            if (!review.getMediaUrls().isEmpty()) {
                for (String url : review.getMediaUrls()) {
                        try {
                            cloudinaryService.deleteFile(url);
                            log.info("Deleted review media: {}", url);
                        } catch (Exception e) {
                            log.warn("Failed to delete media {}: {}", url, e.getMessage());
                        }
                    }
                }
            reviewRepository.delete(review);
            updateProductRating(review.getProduct().getId());
            return ResponseHelper.ok(null,ResponseMessage.DELETE_SUCCESS);
        } catch (Exception e) {
            log.info("Exception delete review : ", e);
            return ResponseHelper.badRequest(ResponseMessage.DELETE_FAILED);
        }
    }
    @Transactional
    public TypeResponse<Void> deleteReply(UUID replyId, Principal principal) {
        try {
            UserEntity currentUser = getUser(principal);
            if (currentUser == null) return ResponseHelper.unauthorized(ResponseMessage.UNAUTHORIZED);

            ReviewReply reply = reviewReplyRepository.findById(replyId)
                    .orElse(null);
            if (reply == null) {
                return ResponseHelper.notFound(ResponseMessage.NOT_FOUND);
            }

            if (!canEditReply(reply)) {
                return ResponseHelper.forbidden(ResponseMessage.FORBIDDEN);
            }

            reviewReplyRepository.delete(reply);
            return ResponseHelper.ok(null, ResponseMessage.DELETE_SUCCESS);

        } catch (Exception e) {
            log.error("Lỗi xóa reply", e);
            return ResponseHelper.badRequest(ResponseMessage.DELETE_FAILED);
        }
    }
    public void updateProductRating(UUID productId) {
        productRepository.updateRatingByProductId(productId);
    }
    public TypeResponse<PageDTO<ReviewResponse>> getProductReviews(UUID productId, Boolean recomment, Boolean approved, Integer ratings, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
            Double minRating = null;
            Double maxRating = null;

            if (ratings != null) {
                if (ratings == 5) {
                    minRating = 5.0;
                } else if (ratings >= 1 && ratings <= 4) {
                    minRating = ratings.doubleValue();
                    maxRating = ratings + 1.0;
                }
            }
            Page<Review> reviews = reviewRepository.findReviewsWithFilter(
                    productId,recomment, approved, minRating, maxRating, pageable);
            List<ReviewResponse> content = reviews.stream()
                    .map(this::toResponse)
                    .toList();
            return ResponseHelper.okPage(reviews, content, ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            log.error("Exception getProductReviews : ", e);
            return ResponseHelper.badRequest(ResponseMessage.FETCH_FAILED);
        }
    }
    public TypeResponse<PageDTO<ReviewResponse>> getMyReviews(int page, int size, Principal principal) {
        try {
            UserEntity user = getUser(principal);
            if (user == null)  return ResponseHelper.unauthorized(ResponseMessage.UNAUTHORIZED);
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
            Page<Review> reviews = reviewRepository.findByUserId(user.getId(), pageable);
            List<ReviewResponse> content = reviews.stream()
                    .map(this::toResponse)
                    .toList();
            return ResponseHelper.okPage(reviews, content, ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            log.error("Exception getMyReviews : ", e);
            return ResponseHelper.badRequest(ResponseMessage.FETCH_FAILED);
        }
    }
    public TypeResponse<ReviewResponse> approveReview(UUID reviewId, Boolean approved, Boolean recomment, Principal principal) {
        try{
            if (approved == null || recomment == null) {
                return ResponseHelper.validationError("params", "Trường approved / recomment là bắt buộc");
            }
            UserEntity user = getUser(principal);
            if (user == null) return ResponseHelper.unauthorized(ResponseMessage.UNAUTHORIZED);
            Optional<Review> reviewOpt = reviewRepository.findById(reviewId);
            if (reviewOpt.isEmpty()) return ResponseHelper.badRequest(ResponseMessage.NOT_FOUND);
            reviewOpt.get().setIsApproved(approved);
            reviewOpt.get().setIsRecommended(recomment);
            Review review = reviewRepository.save(reviewOpt.get());
            return ResponseHelper.ok(this.toResponse(review), ResponseMessage.UPDATE_SUCCESS);
        } catch (Exception e) {
            log.error("Exception approveReview : ", e);
            return ResponseHelper.badRequest(ResponseMessage.UPDATE_FAILED);
        }
    }
    private UserEntity getUser(Principal principal) {
        return (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
    }
    private UserEntity getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal() instanceof String && "anonymousUser".equals(auth.getPrincipal())) {
            return null;
        }
        return (UserEntity) auth.getPrincipal();
    }
    private boolean hasEditPermission(BaseEntity entity, UserEntity userEntity) {
        if (userEntity == null) return false;
        boolean isAdmin = userEntity.getRoles().stream()
                .anyMatch(role -> GuardType.ADMIN.equals(role.getName()));
        if (isAdmin) return true;
        if (entity instanceof Review review) {
            return review.getUser() != null && review.getUser().getId().equals(userEntity.getId());
        }
        if (entity instanceof ReviewReply reply) {
            return reply.getUser() != null && reply.getUser().getId().equals(userEntity.getId());
        }
        return false;
    }
    private boolean canEditReview(Review review) {
        return hasEditPermission(review, getCurrentUser());
    }

    private boolean canEditReply(ReviewReply reply) {
        return hasEditPermission(reply, getCurrentUser());
    }
    private ReviewResponse toResponse(Review r) {
        List<ReplyResponse> replies = getRepliesByReview(r.getId());
        return new ReviewResponse(
                r.getId(),
                r.getUser().getId(),
                r.getUser().getFullName(),
                r.getUser().getAvatar(),
                r.getProduct().getId(),
                r.getProduct().getName(),
                r.getProduct().getSlug(),
                r.getProduct().getFeaturedImage(),
                r.getRating(),
                r.getContent(),
                r.getMediaUrls(),
                r.getIsRecommended(),
                r.getIsApproved(),
                r.getCreatedAt(),
                r.getUpdatedAt(),
                replies,
                canEditReview(r)
        );
    }
    private ReplyResponse toReplyResponse(ReviewReply r) {
        List<ReplyResponse> children = Optional.ofNullable(r.getChildren())
                .orElse(Collections.emptyList())
                .stream()
                .sorted(Comparator.comparing(BaseEntity::getCreatedAt))
                .map(this::toReplyResponse)
                .toList();

        return new ReplyResponse(
                r.getId(),
                r.getUser().getId(),
                r.getUser().getFullName(),
                r.getUser().getAvatar(),
                r.getContent(),
                r.getIsAdminReply(),
                r.getCreatedAt(),
                r.getUpdatedAt(),
                children,
                canEditReply(r)
        );
    }
    private void sendReplyNotification(ReviewReply reply, UserEntity replier) {
        Review review = reply.getReview();
        UserEntity reviewAuthor = review.getUser();
        // Không gửi thông báo nếu tự reply chính mình
        if (reviewAuthor.getId().equals(replier.getId())) {
            return;
        }

        String title = "Có phản hồi mới cho đánh giá của bạn";
        String content = String.format("%s đã trả lời đánh giá của bạn về sản phẩm \"%s\"",
                replier.getFullName(), review.getProduct().getName());

        if (reply.getParentReply() != null) {
            content = String.format("%s đã trả lời bình luận của bạn", replier.getFullName());
        }

        Map<String, Object> data = Map.of(
                "replierName", replier.getFullName(),
                "productName", review.getProduct().getName(),
                "reviewId", review.getId(),
                "replyId", reply.getId()
        );

        // Gửi system notification
        notificationService.sendSystemNotification(
                SystemNotificationType.NEW_REVIEW_REPLY,
                reviewAuthor.getId(),
                data
        );

        // Gửi realtime WebSocket
        UserNotificationDTO wsDto = UserNotificationDTO.builder()
                .notificationId(UUID.randomUUID())
                .title(title)
                .content(content)
                .type(NotificationType.REVIEW_REPLY)
                .sentAt(LocalDateTime.now())
                .isRead(false)
                .build();

        messagingTemplate.convertAndSendToUser(
                reviewAuthor.getUsername(),
                "/queue/notifications",
                wsDto
        );

        // Nếu là reply cho reply → thông báo cho cả người được reply
        if (reply.getParentReply() != null && reply.getParentReply().getUser() != null) {
            UserEntity parentAuthor = reply.getParentReply().getUser();
            if (!parentAuthor.getId().equals(replier.getId()) && !parentAuthor.getId().equals(reviewAuthor.getId())) {
                notificationService.sendSystemNotification(
                        SystemNotificationType.NEW_REVIEW_REPLY,
                        parentAuthor.getId(),
                        data
                );

                messagingTemplate.convertAndSendToUser(
                        parentAuthor.getUsername(),
                        "/queue/notifications",
                        wsDto
                );
            }
        }
    }
}
