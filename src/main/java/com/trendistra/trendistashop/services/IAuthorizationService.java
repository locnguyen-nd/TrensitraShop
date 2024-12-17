package com.trendistra.trendistashop.services;
import com.trendistra.trendistashop.dto.response.RoleDTO;
import com.trendistra.trendistashop.entities.user.PermissionEntity;
import com.trendistra.trendistashop.entities.user.RoleEntity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IAuthorizationService {
    /**
     * Lấy danh sách các quyền (roles) của người dùng hiện tại.
     *
     * @return Một tập hợp (Set) các RoleEntity đại diện cho các quyền của người dùng.
     */
    Set<RoleEntity> getUserRole();
    RoleDTO createRole(RoleDTO roleDTO);
    RoleDTO getRoleById(UUID roleId);
    RoleDTO getRoleByName (String name);
    List<RoleDTO> getAllRoles();
    RoleDTO updateRole(UUID roleId , RoleDTO roleDTO);
    void deleteRole(UUID roleId);
    RoleDTO addPermissionsToRole(UUID roleId, Set<UUID> permissionIds);
    RoleDTO removePermissionsFromRole(UUID roleId, Set<UUID> permissionIds);
    Set<PermissionEntity> getRolePermissions(UUID roleId);
    boolean hasPermission(UUID roleId, UUID permissionIds);

}
