package com.trendistra.trendistashop.services.impl.notification;

import com.trendistra.trendistashop.dto.request.PasswordChangedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class AccountNotificationService {
    private final NotificationEventHandler notificationEventHandler;

    public AccountNotificationService(NotificationEventHandler notificationEventHandler) {
        this.notificationEventHandler = notificationEventHandler;
    }
    /**
     * Listen for password change events and send notifications
     */
    @EventListener
    public void handlePasswordChanged(PasswordChangedEvent event) {
        log.info("Handling password changed event for user: {}", event.getUserId());
        notificationEventHandler.notifyPasswordChanged(event.getUserId());
    }
    /**
     * Method to be called directly when password is changed
     */
    public void notifyPasswordChanged(UUID userId) {
        notificationEventHandler.notifyPasswordChanged(userId);
    }

}
