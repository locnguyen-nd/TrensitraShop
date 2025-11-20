package com.trendistashop.dto.review;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author Locnd
 */
public record CreateReviewRequest(
        UUID orderId,
        Integer rating,
        String content,
        List<String> mediaUrls
) {}
