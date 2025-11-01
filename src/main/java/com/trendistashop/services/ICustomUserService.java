package com.trendistashop.services;

import com.trendistashop.dto.request.UserUpdateDTO;
import com.trendistashop.dto.response.UserDetailDTO;
import com.trendistashop.dto.response.TypeResponse;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ICustomUserService {
    TypeResponse<List<UserDetailDTO>> getAllUser();

    TypeResponse<UserDetailDTO> getUserById(UUID id);

    TypeResponse<UserDetailDTO> updateUser(Principal principal,UserUpdateDTO userUpdateDTO);

     TypeResponse<UserDetailDTO> updateAvatarUser(Principal principal,String avatarUrl);
    TypeResponse<UserDetailDTO> assignRolesToUser(UUID id, Set<UUID> roleIds);

    TypeResponse<Void> deleteUser(UUID id);

    TypeResponse<Void> deleteOwnAccount();
}
