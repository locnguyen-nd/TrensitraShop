package com.trendistashop.controllers.user;

import com.trendistashop.dto.chat.FilterDTO;
import com.trendistashop.dto.chat.notification.AdminNotificationRequestDTO;
import com.trendistashop.dto.chat.notification.AdminNotificationResponseDTO;
import com.trendistashop.dto.chat.notification.UserNotificationDTO;
import com.trendistashop.dto.response.PageDTO;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.entities.notification.Notification;
import com.trendistashop.entities.user.UserEntity;
import com.trendistashop.enums.NotificationType;
import com.trendistashop.repositories.notification.NotificationRepository;
import com.trendistashop.services.impl.notification.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Locnd
 */
@RestController
@RequestMapping("${api.prefix}/notifications")
@Tag(name = "Notifications API" , description = "API gửi và nhận thông báo, sử dụng Websocket")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserDetailsService userDetailsService;


    @PostMapping("/admin")
    @Operation(summary = "Admin tạo 1 thông báo")
    public ResponseEntity<AdminNotificationResponseDTO> create(
            @RequestBody AdminNotificationRequestDTO req,
            Principal admin) {
        UserEntity adminAcc = getUser(admin);
        return ResponseEntity.ok(notificationService.createNotification(req, adminAcc));
    }
    @PutMapping("/admin/{id}")
    @Operation(summary = "Admin sửa 1 thông báo theo ID")
    public ResponseEntity<AdminNotificationResponseDTO> update(
            @PathVariable UUID id,
            @RequestBody AdminNotificationRequestDTO req,
            Principal admin) {
        UserEntity adminAcc = getUser(admin);
        return ResponseEntity.ok(notificationService.updateNotification(id, req, adminAcc));
    }

    @DeleteMapping("/admin/{id}")
    @Operation(summary = "Admin xóa thông báo đã tạo")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping("/admin/all")
    @Operation(summary = "Lấy danh sách thông báo của Admin",
            description = "Hỗ trợ tìm kiếm, lọc trạng thái, loại, phân trang")
    public ResponseEntity<TypeResponse<PageDTO<AdminNotificationResponseDTO>>> getNotifications(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isSend, // true - SENT, false - NOT_SENT
            @RequestParam(required = false) NotificationType type,
            @RequestParam(defaultValue = "0") @Min(0) Integer page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer size,
            @RequestParam(defaultValue = "desc") String sortDir,
            Principal admin
    ) {
        UserEntity adminAcc = getUser(admin);
        String swapSend = null;
        if(isSend != null) {
            swapSend = isSend ? "SENT": "NOT_SENT";
        }
        String swapType = null;
        if (type != null) {
            swapType = NotificationType.valueOf(String.valueOf(type)).name();
        }
        FilterDTO filter = FilterDTO.builder()
                .search(search)
                .isSend(swapSend)
                .type(swapType)
                .page(page)
                .size(size)
                .sortDir(sortDir)
                .build();

        TypeResponse<PageDTO<AdminNotificationResponseDTO>> response =
                notificationService.getAdminNotifications(adminAcc.getId(), filter);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/admin/send/{id}")
    @Operation(summary = "Gửi ngay nếu không đánh dấu là bản nháp")
    public ResponseEntity<String> sendNow(@PathVariable UUID id) {
        Notification notif = notificationRepository.findById(id).orElseThrow();
        if (notif.isSent()) {
            return ResponseEntity.badRequest().body("Already sent");
        }
        notificationService.sendNotificationAsync(notif, null);
        return ResponseEntity.ok("Sent to all users");
    }
    @GetMapping("/user/me")
    @Operation(summary = "Lấy danh sách thông báo", description = "Lấy danh sách thông báo được gửi tới user")
    public ResponseEntity<List<UserNotificationDTO>> getMyNotifications(
            Principal user) {
        UserEntity userAcc = getUser(user);
        return ResponseEntity.ok(notificationService.getUserNotifications(userAcc.getId()));
    }
    @PutMapping("/user/read")
    @Operation(summary = "Đánh dấu đã đọc", description = "Nếu cung cấp notificationId thì đánh dấu riêng cho notificatión đó, còn nếu null thì đánh dấu all ")
    public ResponseEntity<String> markAsRead(
            @RequestParam(required = false) UUID notificationId,
            Principal user) {
        UserEntity userAcc = getUser(user);
        notificationService.markAsRead(notificationId, userAcc.getId());
        return ResponseEntity.ok("Marked as read");
    }
    private UserEntity getUser(Principal principal) {
        return (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
    }
}
