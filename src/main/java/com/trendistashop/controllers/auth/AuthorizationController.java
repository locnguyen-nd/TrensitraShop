package com.trendistashop.controllers.auth;

import com.trendistashop.dto.response.RoleDTO;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.entities.user.PermissionEntity;
import com.trendistashop.services.IAuthorizationService;
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
@Tag(name = "Role API" , description = "API quản lý vai trò (role) cho user")
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
    public ResponseEntity<TypeResponse<RoleDTO>> getRoleByName(@PathVariable String name) {
        TypeResponse<RoleDTO> response = roleService.getRoleByName(name);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping
    public ResponseEntity<TypeResponse<List<RoleDTO>>> getAllRoles() {
        TypeResponse<List<RoleDTO>> response = roleService.getAllRoles();
        return ResponseEntity.status(response.getStatusCode()).body(response);
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
