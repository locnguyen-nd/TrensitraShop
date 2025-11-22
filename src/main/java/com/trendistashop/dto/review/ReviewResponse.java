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
        UUID userId,
        String fullName,
        String avatar,
        UUID productId,
        String productName,
        String productSlug,
        String productImage,
        Integer rating,
        String content,
        List<String> mediaUrls,
        Boolean isRecommended,
        Boolean isApproved,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<ReplyResponse> reply,
        boolean canEdit
) {}
