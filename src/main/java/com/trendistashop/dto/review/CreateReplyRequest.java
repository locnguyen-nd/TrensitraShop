package com.trendistashop.dto.review;

import java.util.UUID;

/**
 *
 * @author Locnd
 */
public record CreateReplyRequest(
        UUID reviewId,
        String content,
        UUID parentReplyId
) { }
