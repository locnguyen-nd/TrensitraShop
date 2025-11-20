package com.trendistashop.dto.review;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Locnd
 */
public record ReviewResponse(
        UUID id,
        UUID reviewId,
        String fullName,
        String avatar,
        UUID productId,
        String productName,
        String productSlug,
        String productImage,
        Integer rating,
        String content,
        Boolean isRecommended,
        List<String> mediaUrls,
        Boolean isApproved,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        boolean canEdit
) {}
