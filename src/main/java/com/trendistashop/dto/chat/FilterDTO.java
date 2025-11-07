package com.trendistashop.dto.chat;

import com.trendistashop.enums.ConversationStatus;
import com.trendistashop.enums.NotificationType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 *
 * @author Locnd
 */
@Data
@Builder
public class FilterDTO {
    private String search;
    private ConversationStatus status;
    private String type;
    private String isSend;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private String sortBy = "lastMessageAt";
    private String sortDir = "desc"; // asc/desc
    private int page = 0;
    private int size = 10;
}
