package com.trendistra.trendistashop.controllers.admin;

import com.trendistra.trendistashop.dto.response.NotificationDTO;
import com.trendistra.trendistashop.entities.notification.Notification;
import com.trendistra.trendistashop.services.impl.notification.NotificationEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/notifications")
public class NotificationController {
    @Autowired
    private NotificationEventHandler notificationEventHandler;
    @GetMapping
    public ResponseEntity<Page<Notification>> getNotifications(
            @RequestParam UUID userId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(notificationEventHandler.getNotifications(userId, type, status, page, size));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<String> markAsRead(@PathVariable UUID id) {
        notificationEventHandler.markAsRead(id);
        return ResponseEntity.ok("Notification marked as read");
    }

    @PostMapping
    public ResponseEntity<String> sendNotification(@RequestBody NotificationDTO dto) {
        notificationEventHandler.sendNotification(dto);
        return ResponseEntity.ok("Notification sent");
    }
}
