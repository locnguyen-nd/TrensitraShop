package com.trendistra.trendistashop.services;
import com.trendistra.trendistashop.dto.request.UserUpdateDTO;
import com.trendistra.trendistashop.dto.response.UserDetailDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ICustomUserService {
    List<UserDetailDTO> getAllUser();
    UserDetailDTO getUserById(UUID id);
    UserDetailDTO updateUser( UserUpdateDTO userUpdateDTO, MultipartFile multipartFile) throws IOException;
    UserDetailDTO assignRolesToUser(UUID id , Set<UUID> roleIds);
    void deleteUser(UUID id);
    void deleteOwnAccount();
}
