package com.trendistashop.controllers.admin;

import com.trendistashop.dto.chat.AutoReplyConfigDTO;
import com.trendistashop.services.impl.notification.AutoReplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author Locnd
 */
@RestController
@RequestMapping("${api.prefix}/admin/auto-reply")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Chat Config API", description = "API cho phép Admin tạo các quy tắc trả lời, on/off chế độ auto chat")
public class AdminAutoReplyController {
    private final AutoReplyService autoReplyService;

    @GetMapping
    @Operation(summary = "Lấy tất cả các quy tắc hiện có")
    public ResponseEntity<List<AutoReplyConfigDTO>> getConfigs() {
        return ResponseEntity.ok(autoReplyService.getAllConfigs());
    }

    @PostMapping
    @Operation(summary = "Tạo quy tắc chat mới")
    public ResponseEntity<AutoReplyConfigDTO> create(@RequestBody AutoReplyConfigDTO dto) {
        return ResponseEntity.ok(autoReplyService.createConfig(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Sửa lại quy tắc chat")
    public ResponseEntity<AutoReplyConfigDTO> update(@PathVariable UUID id, @RequestBody AutoReplyConfigDTO dto) {
        return ResponseEntity.ok(autoReplyService.updateConfig(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Bỏ quy tắc chat")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        autoReplyService.deleteConfig(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/toggle")
    @Operation(summary = "Tắt chat bot, admin tự reply khách thủ công")
    public ResponseEntity<?> toggleAutoChat(@RequestBody Map<String, Boolean> body) {
        boolean enabled = body.getOrDefault("enabled", true);
        autoReplyService.toggleAutoChat(enabled);
        return ResponseEntity.ok(Map.of("autoChatEnabled", enabled));
    }

    @GetMapping("/status")
    @Operation(summary = "Kểm tra trạng thái của chat bot")
    public ResponseEntity<?> getStatus() {
        return ResponseEntity.ok(Map.of("autoChatEnabled", autoReplyService.isAutoChatEnabled()));
    }
}
