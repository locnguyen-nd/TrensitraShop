package com.trendistashop.dto.chat;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author Locnd
 */
@Data
@Builder
public class ChatStatisticsDTO {
    private long totalMessages;
    private double averageResponseTime;
    private double responseRate;
}
