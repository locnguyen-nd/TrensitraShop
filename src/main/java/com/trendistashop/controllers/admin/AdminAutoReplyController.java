package com.trendistashop.controllers.admin;

import com.trendistashop.dto.chat.AutoReplyConfigDTO;
import com.trendistashop.dto.response.TypeResponse;
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
    public ResponseEntity<TypeResponse<List<AutoReplyConfigDTO>>> getConfigs() {
        TypeResponse<List<AutoReplyConfigDTO>> response = autoReplyService.getAllConfigs();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping
    @Operation(summary = "Tạo quy tắc chat mới")
    public ResponseEntity<TypeResponse<AutoReplyConfigDTO>> create(@RequestBody AutoReplyConfigDTO dto) {
        TypeResponse<AutoReplyConfigDTO> response = autoReplyService.createConfig(dto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Sửa lại quy tắc chat")
    public ResponseEntity<TypeResponse<AutoReplyConfigDTO>> update(@PathVariable UUID id, @RequestBody AutoReplyConfigDTO dto) {
        TypeResponse<AutoReplyConfigDTO> response = autoReplyService.updateConfig(id, dto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Bỏ quy tắc chat")
    public ResponseEntity<TypeResponse<Void>> delete(@PathVariable UUID id) {
        TypeResponse<Void> response = autoReplyService.deleteConfig(id);
        return  ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/toggle")
    @Operation(summary = "Tắt chat bot, admin tự reply khách thủ công")
    public ResponseEntity<TypeResponse<Map<String,Boolean>>> toggleAutoChat(@RequestParam Boolean enabled) {
        TypeResponse<Boolean> response = autoReplyService.toggleAutoChat(enabled);
        return ResponseEntity.status(response.getStatusCode()).body((TypeResponse<Map<String, Boolean>>) Map.of("autoChatEnabled", enabled));
    }

    @GetMapping("/status")
    @Operation(summary = "Kểm tra trạng thái của chat bot")
    public ResponseEntity<TypeResponse<Map<String, Boolean>>> getStatus() {
        TypeResponse<Boolean> response = autoReplyService.isAutoChatEnabled();
        return ResponseEntity.status(response.getStatusCode()).body((TypeResponse<Map<String, Boolean>>) Map.of("autoChatEnabled", response));
    }
}
