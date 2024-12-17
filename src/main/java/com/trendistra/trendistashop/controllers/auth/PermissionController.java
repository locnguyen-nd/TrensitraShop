package com.trendistra.trendistashop.controllers.auth;

import com.trendistra.trendistashop.dto.response.PermissionDTO;
import com.trendistra.trendistashop.services.impl.PermissionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/permissions")
@RequiredArgsConstructor
@Tag(name = "Permissions")
public class PermissionController {
    private final PermissionService permissionService;
    @PostMapping
    public ResponseEntity<PermissionDTO> createPermission(@Valid @RequestBody PermissionDTO permissionDTO) {
        return new ResponseEntity<>(permissionService.createPermission(permissionDTO), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PermissionDTO> getPermissionById(@PathVariable UUID id) {
        return ResponseEntity.ok(permissionService.getPermissionById(id));
    }
    @GetMapping
    public ResponseEntity<List<PermissionDTO>> getAllPermissions() {
        return ResponseEntity.ok(permissionService.getAllPermissions());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PermissionDTO> updatePermission(
            @PathVariable UUID id,
            @Valid @RequestBody PermissionDTO permissionDTO
    ) {
        return ResponseEntity.ok(permissionService.updatePermission(id, permissionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable UUID id) {
        permissionService.deletePermission(id);
        return ResponseEntity.noContent().build();
    }
}
