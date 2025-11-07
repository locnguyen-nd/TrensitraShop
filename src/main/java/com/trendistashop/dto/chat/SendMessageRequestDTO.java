package com.trendistashop.dto.chat;

import com.trendistashop.enums.MessageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

/**
 *
 * @author Locnd
 */
@Data
public class SendMessageRequestDTO {
    /** Nếu chưa có thì tạo mới đoạn hội thoại*/
    private UUID conversationId;
    /** Dùng cho guest, không cần khi user đã login */
    @Size(max = 36, message = "guestSessionId must be a valid UUID string")
    private UUID guestSessionId;
    @NotNull(message = "type is required")
    private MessageType type;
    /** Nội dung tin nhắn */
    @NotBlank(message = "content is required")
    @Size(max = 4000, message = "content must not exceed 4000 characters")
    private String content;
    /** User có thể gửi link sản phẩm, mã sản phẩm*/
    private String productInfo;
}
