package com.trendistashop.dto.chat;

import com.trendistashop.dto.response.ProductDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * @author Locnd
 */
@Data
public class MessageResponseDTO {
    private UUID messageId;
    private SenderDTO sender;
    private String content;
    private String type;
    private LocalDateTime sentAt;
    private Boolean isRead;
    private String productUrl; // Gửi link sản phẩm về frontend
}
