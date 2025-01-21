package com.trendistra.trendistashop.services.impl.auth;

import com.trendistra.trendistashop.dto.response.RoleDTO;
import com.trendistra.trendistashop.entities.user.PermissionEntity;
import com.trendistra.trendistashop.entities.user.RoleEntity;
import com.trendistra.trendistashop.exceptions.ResourceNotFoundEx;
import com.trendistra.trendistashop.repositories.auth.PermissionRepository;
import com.trendistra.trendistashop.repositories.auth.RoleRepository;
import com.trendistra.trendistashop.services.IAuthorizationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements IAuthorizationService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<RoleEntity> getUserRole() {
        List<RoleEntity> roles  = new ArrayList<>();
        RoleEntity role = roleRepository.findByName("USER");
        roles.add(role);
        return roles;
    }
    @Transactional
    public RoleDTO createRole(RoleDTO roleDTO) {
        if (roleRepository.existsByName(roleDTO.getName())) {
            throw new IllegalArgumentException("Role with this name already exists");
        }
        // Fetch and validate permissions
        List<PermissionEntity> permissions = roleDTO.getPermissionIds().stream()
                .map(permId -> permissionRepository.findById(permId)
                        .orElseThrow(() -> new ResourceNotFoundEx("Permission not found: " + permId)))
                .collect(Collectors.toList());
        RoleEntity role = RoleEntity.builder()
                .name(roleDTO.getName())
                .description(roleDTO.getDescription())
                .permissions(permissions)
                .build();
        RoleEntity savedRole = roleRepository.save(role);
        return modelMapper.map(savedRole, RoleDTO.class);
    }
    public RoleDTO getRoleById(UUID id) {
        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEx("Role not found: " + id));

        return  modelMapper.map(role, RoleDTO.class);
    }
    public RoleDTO getRoleByName(String name) {
        RoleEntity role = roleRepository.findByName(name);
        return modelMapper.map(role, RoleDTO.class);
    }

    public List<RoleDTO> getAllRoles() {
        List<RoleEntity> roles =  roleRepository.findAll();
        List<RoleDTO> roleDTOS = roles.stream().map(
                role -> modelMapper.map(role, RoleDTO.class)
                ).collect(Collectors.toList());
        return roleDTOS;
    }
    @Transactional
    public RoleDTO updateRole(UUID id, RoleDTO roleDTO) {
        RoleEntity existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEx("Role not found: " + id));

        // Check if name is being changed and is unique
        if (!existingRole.getName().equals(roleDTO.getName()) &&
                roleRepository.existsByName(roleDTO.getName())) {
            throw new IllegalArgumentException("Role with this name already exists");
        }
        // Fetch and validate permissions
        List<PermissionEntity> permissions = roleDTO.getPermissionIds().stream()
                .map(permId -> permissionRepository.findById(permId)
                        .orElseThrow(() -> new ResourceNotFoundEx("Permission not found: " + permId)))
                .collect(Collectors.toList());
        existingRole.setName(roleDTO.getName());
        existingRole.setDescription(roleDTO.getDescription());
        existingRole.setPermissions(permissions);
        RoleEntity updatedRole = roleRepository.save(existingRole);

        return modelMapper.map(updatedRole, RoleDTO.class);
    }
    @Transactional
    public void deleteRole(UUID id) {
        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEx("Role not found: " + id));
        roleRepository.delete(role);
    }
    @Transactional
    public RoleDTO addPermissionsToRole(UUID roleId, Set<UUID> permissionIds) {
        // Find the role
        RoleEntity role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundEx("Role not found: " + roleId));

        // Fetch and validate permissions
        Set<PermissionEntity> newPermissions = permissionIds.stream()
                .map(permId -> permissionRepository.findById(permId)
                        .orElseThrow(() -> new ResourceNotFoundEx("Permission not found: " + permId)))
                .collect(Collectors.toSet());

        // Add new permissions to existing role permissions
        role.getPermissions().addAll(newPermissions);

        // Save updated role
        RoleEntity updatedRole = roleRepository.save(role);

        // Return updated DTO
        return modelMapper.map(updatedRole, RoleDTO.class);
    }
    @Transactional
    public RoleDTO removePermissionsFromRole(UUID roleId, Set<UUID> permissionIds) {
        // Find the role
        RoleEntity role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundEx("Role not found: " + roleId));

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
                        .collect(Collectors.toList())
        );
    }
    @Transactional()
    public List<PermissionEntity> getRolePermissions(UUID roleId) {
        RoleEntity role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundEx("Role not found: " + roleId));

        return role.getPermissions();
    }

    @Transactional()
    public boolean hasPermission(UUID roleId, UUID permissionId) {
        RoleEntity role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundEx("Role not found: " + roleId));

        return role.getPermissions().stream()
                .anyMatch(permission -> permission.getId().equals(permissionId));
    }
}
