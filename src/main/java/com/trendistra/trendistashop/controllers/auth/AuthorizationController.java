package com.trendistra.trendistashop.controllers.auth;

import com.trendistra.trendistashop.dto.response.RoleDTO;
import com.trendistra.trendistashop.entities.user.PermissionEntity;
import com.trendistra.trendistashop.services.IAuthorizationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/role")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Role")
public class AuthorizationController {
    private final IAuthorizationService roleService;
    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@Valid @RequestBody RoleDTO roleDTO) {
        return new ResponseEntity<>(roleService.createRole(roleDTO), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable UUID id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<RoleDTO> getRoleByName(@PathVariable String name) {
        return ResponseEntity.ok(roleService.getRoleByName(name));
    }
    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }
    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> updateRole(
            @PathVariable UUID id,
            @Valid @RequestBody RoleDTO roleDTO
    ) {
        return ResponseEntity.ok(roleService.updateRole(id, roleDTO));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable UUID id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok().body(Map.of(
                "message", "Delete successful",
                "status", HttpStatus.OK
        ));
    }
    @PostMapping("/{roleId}/permissions")
    public ResponseEntity<RoleDTO> addPermissionsToRole(
            @PathVariable UUID roleId,
            @RequestBody Set<UUID> permissionIds
    ) {
        return ResponseEntity.ok(roleService.addPermissionsToRole(roleId, permissionIds));
    }

    @DeleteMapping("/{roleId}/permissions")
    public ResponseEntity<RoleDTO> removePermissionsFromRole(
            @PathVariable UUID roleId,
            @RequestBody Set<UUID> permissionIds
    ) {
        return ResponseEntity.ok(roleService.removePermissionsFromRole(roleId, permissionIds));
    }

    @GetMapping("/{roleId}/permissions")
    public ResponseEntity<Set<UUID>> getRolePermissions(@PathVariable UUID roleId) {
        Set<UUID> permissionIds = roleService.getRolePermissions(roleId).stream()
                .map(PermissionEntity::getId)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(permissionIds);
    }

    @GetMapping("/{roleId}/permissions/{permissionId}")
    public ResponseEntity<Boolean> hasPermission(
            @PathVariable UUID roleId,
            @PathVariable UUID permissionId
    ) {
        return ResponseEntity.ok(roleService.hasPermission(roleId, permissionId));
    }

}
