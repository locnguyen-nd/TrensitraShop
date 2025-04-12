package com.trendistra.trendistashop.services;
import com.trendistra.trendistashop.dto.request.UserUpdateDTO;
import com.trendistra.trendistashop.dto.response.UserDetailDTO;
import com.trendistra.trendistashop.dto.response.TypeResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ICustomUserService {
    TypeResponse<List<UserDetailDTO>> getAllUser();
    TypeResponse<UserDetailDTO> getUserById(UUID id);
    TypeResponse<UserDetailDTO> updateUser( UserUpdateDTO userUpdateDTO);
//    TypeResponse<UserDetailDTO> updateAvatarUser( UUID userId, MultipartFile multipartFile) throws IOException;
    TypeResponse<UserDetailDTO> assignRolesToUser(UUID id , Set<UUID> roleIds);
    TypeResponse<Void> deleteUser(UUID id);
    TypeResponse<Void> deleteOwnAccount();
}
