package com.trendistra.trendistashop.services;
import com.trendistra.trendistashop.entities.auth.RoleEntity;
import java.util.Set;

public interface IAuthorizationService {
    /**
     * Lấy danh sách các quyền (roles) của người dùng hiện tại.
     *
     * @return Một tập hợp (Set) các RoleEntity đại diện cho các quyền của người dùng.
     */
    Set<RoleEntity> getUserRole();
}
