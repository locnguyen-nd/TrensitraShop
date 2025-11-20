package com.trendistashop.dto.review;

import java.util.List;

/**
 *
 * @author Locnd
 */
public record UpdateReviewRequest(
        Integer rating,
        String content,
        List<String> mediaUrls
) {}
