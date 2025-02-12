package com.trendistra.trendistashop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private String type;
    private String message;
    private long timestamp;
    private UUID recipient; // dành riêng cho người nhận thông báo
}

