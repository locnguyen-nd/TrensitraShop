package com.trendistashop.services;

import com.trendistashop.dto.response.ChatMessageDTO;
import com.trendistashop.dto.response.NotificationDTO;

public interface MessageBroadcaster {
    void broadcastMessage(ChatMessageDTO message);
    void broadcastNotification(NotificationDTO notification);
}
