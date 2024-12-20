package com.trendistra.trendistashop.services.impl.auth;

import com.trendistra.trendistashop.dto.response.PermissionDTO;
import com.trendistra.trendistashop.entities.user.PermissionEntity;
import com.trendistra.trendistashop.repositories.auth.PermissionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepository permissionRepository;
    @Transactional
    public PermissionDTO createPermission(PermissionDTO permissionDTO) {
        // Check if permission with the same name already exists
        if (permissionRepository.existsByName(permissionDTO.getName())) {
            throw new IllegalArgumentException("Permission with this name already exists");
        }

        // Create new permission entity
        PermissionEntity permission = PermissionEntity.builder()
                .name(permissionDTO.getName())
                .method(permissionDTO.getMethod())
                .endPoint(permissionDTO.getEndPoint())
                .build();

        // Save and return DTO
        PermissionEntity savedPermission = permissionRepository.save(permission);
        return new PermissionDTO(
                savedPermission.getId(),
                savedPermission.getName(),
                savedPermission.getMethod(),
                savedPermission.getEndPoint()
        );
    }
    @Transactional()
    public PermissionDTO getPermissionById(UUID id) {
        PermissionEntity permission = permissionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found: " + id));

        return new PermissionDTO(
                permission.getId(),
                permission.getName(),
                permission.getMethod(),
                permission.getEndPoint()
        );
    }
    @Transactional()
    public List<PermissionDTO> getAllPermissions() {
        return permissionRepository.findAll().stream()
                .map(permission -> new PermissionDTO(
                        permission.getId(),
                        permission.getName(),
                        permission.getMethod(),
                        permission.getEndPoint()
                ))
                .collect(Collectors.toList());
    }
    @Transactional
    public PermissionDTO updatePermission(UUID id, PermissionDTO permissionDTO) {
        // Find existing permission
        PermissionEntity existingPermission = permissionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found: " + id));

        // Check if name is being changed and is unique
        if (!existingPermission.getName().equals(permissionDTO.getName()) &&
                permissionRepository.existsByName(permissionDTO.getName())) {
            throw new IllegalArgumentException("Permission with this name already exists");
        }

        // Update permission details
        existingPermission.setName(permissionDTO.getName());
        existingPermission.setMethod(permissionDTO.getMethod());
        existingPermission.setEndPoint(permissionDTO.getEndPoint());

        // Save and return updated permission
        PermissionEntity updatedPermission = permissionRepository.save(existingPermission);
        return new PermissionDTO(
                updatedPermission.getId(),
                updatedPermission.getName(),
                updatedPermission.getMethod(),
                updatedPermission.getEndPoint()
        );
    }
    @Transactional
    public void deletePermission(UUID id) {
        // Check if permission exists
        PermissionEntity permission = permissionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found: " + id));

        // Delete the permission
        permissionRepository.delete(permission);
    }
    public Map<String, Map<String, List<String>>> loadPermissions(){
        List<PermissionEntity> permission = permissionRepository.findAll();
        Map<String, Map<String, List<String>>> permissionMap = new HashMap<>();
        for (PermissionEntity permission1 : permission) {
            permissionMap.computeIfAbsent(permission1.getEndPoint(), k-> new HashMap<>())
                    .computeIfAbsent(permission1.getMethod(), k-> new ArrayList<>())
                    .add(permission1.getName());
        }
        return permissionMap;
    }
}
