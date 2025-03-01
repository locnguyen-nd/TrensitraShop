package com.trendistra.trendistashop.services;

import com.trendistra.trendistashop.dto.response.ChatMessageDTO;
import com.trendistra.trendistashop.dto.response.NotificationDTO;

public interface MessageBroadcaster {
    void broadcastMessage(ChatMessageDTO message);
    void broadcastNotification(NotificationDTO notification);
}
