package com.trendistra.trendistashop.services.impl;

import com.trendistra.trendistashop.dto.response.RoleDTO;
import com.trendistra.trendistashop.entities.user.PermissionEntity;
import com.trendistra.trendistashop.entities.user.RoleEntity;
import com.trendistra.trendistashop.repositories.auth.PermissionRepository;
import com.trendistra.trendistashop.repositories.auth.RoleRepository;
import com.trendistra.trendistashop.services.IAuthorizationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements IAuthorizationService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public Set<RoleEntity> getUserRole() {
        Set<RoleEntity> roles  = new HashSet<>();
        Optional<RoleEntity> role = roleRepository.findByName("USER");
        roles.add(role.get());
        return roles;
    }
    @Transactional
    public RoleDTO createRole(RoleDTO roleDTO) {
        if (roleRepository.existsByName(roleDTO.name())) {
            throw new IllegalArgumentException("Role with this name already exists");
        }
        // Fetch and validate permissions
        Set<PermissionEntity> permissions = roleDTO.permissionIds().stream()
                .map(permId -> permissionRepository.findById(permId)
                        .orElseThrow(() -> new EntityNotFoundException("Permission not found: " + permId)))
                .collect(Collectors.toSet());
        RoleEntity role = RoleEntity.builder()
                .name(roleDTO.name())
                .description(roleDTO.description())
                .permissions(permissions)
                .build();
        RoleEntity savedRole = roleRepository.save(role);
        return new RoleDTO(
                savedRole.getId(),
                savedRole.getName(),
                savedRole.getDescription(),
                savedRole.getPermissions().stream().map(PermissionEntity::getId).collect(Collectors.toSet())
        );
    }
    public RoleDTO getRoleById(UUID id) {
        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + id));

        return new RoleDTO(
                role.getId(),
                role.getName(),
                role.getDescription(),
                role.getPermissions().stream().map(PermissionEntity::getId).collect(Collectors.toSet())
        );
    }
    public RoleDTO getRoleByName(String name) {
        RoleEntity role = roleRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + name));

        return new RoleDTO(
                role.getId(),
                role.getName(),
                role.getDescription(),
                role.getPermissions().stream().map(PermissionEntity::getId).collect(Collectors.toSet())
        );
    }

    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(role -> new RoleDTO(
                        role.getId(),
                        role.getName(),
                        role.getDescription(),
                        role.getPermissions().stream().map(PermissionEntity::getId).collect(Collectors.toSet())
                ))
                .collect(Collectors.toList());
    }
    @Transactional
    public RoleDTO updateRole(UUID id, RoleDTO roleDTO) {
        RoleEntity existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + id));

        // Check if name is being changed and is unique
        if (!existingRole.getName().equals(roleDTO.name()) &&
                roleRepository.existsByName(roleDTO.name())) {
            throw new IllegalArgumentException("Role with this name already exists");
        }
        // Fetch and validate permissions
        Set<PermissionEntity> permissions = roleDTO.permissionIds().stream()
                .map(permId -> permissionRepository.findById(permId)
                        .orElseThrow(() -> new EntityNotFoundException("Permission not found: " + permId)))
                .collect(Collectors.toSet());
        existingRole.setName(roleDTO.name());
        existingRole.setDescription(roleDTO.description());
        existingRole.setPermissions(permissions);
        RoleEntity updatedRole = roleRepository.save(existingRole);

        return new RoleDTO(
                updatedRole.getId(),
                updatedRole.getName(),
                updatedRole.getDescription(),
                updatedRole.getPermissions().stream().map(PermissionEntity::getId).collect(Collectors.toSet())
        );
    }
    @Transactional
    public void deleteRole(UUID id) {
        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + id));

        roleRepository.delete(role);
    }
    @Transactional
    public RoleDTO addPermissionsToRole(UUID roleId, Set<UUID> permissionIds) {
        // Find the role
        RoleEntity role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleId));

        // Fetch and validate permissions
        Set<PermissionEntity> newPermissions = permissionIds.stream()
                .map(permId -> permissionRepository.findById(permId)
                        .orElseThrow(() -> new EntityNotFoundException("Permission not found: " + permId)))
                .collect(Collectors.toSet());

        // Add new permissions to existing role permissions
        role.getPermissions().addAll(newPermissions);

        // Save updated role
        RoleEntity updatedRole = roleRepository.save(role);

        // Return updated DTO
        return new RoleDTO(
                updatedRole.getId(),
                updatedRole.getName(),
                updatedRole.getDescription(),
                updatedRole.getPermissions().stream()
                        .map(PermissionEntity::getId)
                        .collect(Collectors.toSet())
        );
    }
    @Transactional
    public RoleDTO removePermissionsFromRole(UUID roleId, Set<UUID> permissionIds) {
        // Find the role
        RoleEntity role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleId));

        // Remove specified permissions
        role.getPermissions().removeIf(permission ->
                permissionIds.contains(permission.getId()) // cos hay kh
        );
        // Save updated role
        RoleEntity updatedRole = roleRepository.save(role);

        // Return updated DTO
        return new RoleDTO(
                updatedRole.getId(),
                updatedRole.getName(),
                updatedRole.getDescription(),
                updatedRole.getPermissions().stream()
                        .map(PermissionEntity::getId)
                        .collect(Collectors.toSet())
        );
    }
    @Transactional()
    public Set<PermissionEntity> getRolePermissions(UUID roleId) {
        RoleEntity role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleId));

        return role.getPermissions();
    }

    @Transactional()
    public boolean hasPermission(UUID roleId, UUID permissionId) {
        RoleEntity role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleId));

        return role.getPermissions().stream()
                .anyMatch(permission -> permission.getId().equals(permissionId));
    }
}
