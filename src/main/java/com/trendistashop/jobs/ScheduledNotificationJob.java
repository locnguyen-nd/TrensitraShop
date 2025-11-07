package com.trendistashop.jobs;

import com.trendistashop.entities.notification.Notification;
import com.trendistashop.repositories.notification.NotificationRepository;
import com.trendistashop.services.impl.notification.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Locnd
 */
@Component
@Slf4j

public class ScheduledNotificationJob {
    @Autowired
    private NotificationRepository notificationRepo;
    @Autowired
    private NotificationService notificationService;
    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void processScheduledNotifications() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime window = now.plusSeconds(60); // Trong 60s tới

        List<Notification> pending = notificationRepo.findByScheduledAtBetweenAndIsSentFalseAndIsDraftFalse(
                now, window
        );

        if (pending.isEmpty()) return;

        log.info("Found {} scheduled notifications to send", pending.size());

        for (Notification notif : pending) {
            try {
                notificationService.sendNotificationAsync(notif, null);
            } catch (Exception e) {
                log.error("Failed to send scheduled notification {}", notif.getId(), e);
            }
        }
    }
}
