package com.trendistra.trendistashop.services;

import com.trendistra.trendistashop.dto.response.LoginResponse;
import com.trendistra.trendistashop.dto.response.UserDetailDTO;
import com.trendistra.trendistashop.entities.auth.UserEntity;

import java.util.List;

public interface ICustomUserService {
    List<UserDetailDTO> getAllUser();
    UserDetailDTO getUserById(Long id);
    UserEntity updateUser(Long id,UserDetailDTO userDetailDTO);
    void deleteUser(Long id);
    void deleteOwnAccount();


}
