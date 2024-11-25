package com.trendistra.trendistashop.services;
import com.trendistra.trendistashop.dto.response.UserDetailDTO;
import com.trendistra.trendistashop.entities.auth.UserEntity;

import java.util.List;
import java.util.UUID;

public interface ICustomUserService {
    List<UserDetailDTO> getAllUser();
    UserDetailDTO getUserById(UUID id);
    UserEntity updateUser(UUID id,UserDetailDTO userDetailDTO);
    void deleteUser(UUID id);
    void deleteOwnAccount();


}
