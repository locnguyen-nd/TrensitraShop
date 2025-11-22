package com.trendistashop.dto.review;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Locnd
 */
public record ReplyResponse(
        UUID id,
        UUID userId,
        String userFullName,
        String userAvatar,
        String content,
        Boolean isAdminReply,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<ReplyResponse> children,
        boolean canEdit
) {
}
