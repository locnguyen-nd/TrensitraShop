package com.trendistashop.services.impl.auth;

import com.trendistashop.utils.ResponseHelper;
import com.trendistashop.constants.ResponseMessage;
import com.trendistashop.dto.request.UserUpdateDTO;
import com.trendistashop.dto.response.UserDetailDTO;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.entities.user.RoleEntity;
import com.trendistashop.entities.user.UserEntity;
import com.trendistashop.mapper.UserDetailMapper;
import com.trendistashop.repositories.auth.RoleRepository;
import com.trendistashop.repositories.auth.UserDetailRepository;
import com.trendistashop.services.CloudinaryService;
import com.trendistashop.services.ICustomUserService;
import com.trendistashop.specifications.UserSpecification;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomUserService implements UserDetailsService, ICustomUserService {
    @Autowired
    private UserDetailRepository userDetailRepository;
    @Autowired
    private UserDetailMapper userDetailMapper;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    /**
     * Tải thông tin người dùng bằng username.
     *
     * @param username Tên đăng nhập của người dùng.
     * @return Đối tượng UserToken chứa JWT token.
     * @throws UsernameNotFoundException Nếu tài khoản không tồn tại.
     */
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userDetailRepository.findByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User Not Found with userName " + username);
        }
        return user.get();
    }

    @Transactional
    @Override
    public TypeResponse<List<UserDetailDTO>> getAllUser() {
        try {
            Specification<UserEntity> userEntitySpecification = UserSpecification.hasRoleName("USER");
            List<UserEntity> users = userDetailRepository.findAll(userEntitySpecification);
            if (users == null || users.isEmpty()) {
                return ResponseHelper.notFound(ResponseMessage.USER_NOT_FOUND);
            }
            return ResponseHelper.ok(userDetailMapper.getUserDtos(users), ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }

    @Transactional
    @Override
    public TypeResponse<UserDetailDTO> getUserById(UUID id) {
        Optional<UserEntity> userOpt = userDetailRepository.findById(id);
        if (userOpt.isEmpty()) {
            return ResponseHelper.notFound(ResponseMessage.USER_NOT_FOUND);
        }
        UserEntity user = userOpt.get();
        return ResponseHelper.ok(userDetailMapper.convertToDto(user), ResponseMessage.FETCH_SUCCESS);
    }

    @Override
    @Transactional
    public TypeResponse<UserDetailDTO> updateUser(UserUpdateDTO userUpdateDTO) {
        Optional<UserEntity> userOpt = userDetailRepository.findById(userUpdateDTO.getId());
        if (userOpt.isEmpty()) {
            return ResponseHelper.notFound(ResponseMessage.USER_NOT_FOUND);
        }
        UserEntity user = userOpt.get();
        try {
            user.setFirstName(userUpdateDTO.getFirstName());
            user.setLastName(userUpdateDTO.getLastName());
            UserEntity updatedUser = userDetailRepository.save(user);
            return ResponseHelper.ok(userDetailMapper.convertToDto(updatedUser),
                    ResponseMessage.UPDATE_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.UPDATE_FAILED);
        }
    }

    // @Override
    // @Transactional
    // public TypeResponse<UserDetailDTO> updateAvatarUser(UUID userId,
    // MultipartFile avatarFile) throws IOException {
    // Optional<UserEntity> userOpt = userDetailRepository.findById(userId);
    // if (userOpt.isEmpty()) {
    // return ResponseHelper.notFound("Không tìm thấy người dùng");
    // }
    // UserEntity user = userOpt.get();
    //
    // if (avatarFile != null && !avatarFile.isEmpty() && user.getAvatar() != null)
    // {
    // cloudinaryService.deleteFile(user.getAvatar());
    // }
    // if (avatarFile != null && !avatarFile.isEmpty()) {
    // String imageUrl = cloudinaryService.uploadFile(avatarFile, null, "AVATAR");
    // user.setAvatar(imageUrl);
    // }
    // UserEntity updatedUser = userDetailRepository.save(user);
    // return ResponseHelper.ok(userDetailMapper.convertToDto(updatedUser), "Cập
    // nhật ảnh đại diện người dùng thành công");
    // }

    @Transactional
    public TypeResponse<UserDetailDTO> assignRolesToUser(UUID userId, Set<UUID> roleIds) {
        Optional<UserEntity> user = userDetailRepository.findById(userId);
        List<RoleEntity> uniqueRoles = new ArrayList<>(user.get().getRoles());
        roleIds.stream()
                .map(roleId -> roleRepository.findById(roleId)
                        .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleId)))
                .forEach(uniqueRoles::add);
        user.get().setRoles(uniqueRoles);
        UserEntity updatedUser = userDetailRepository.save(user.get());
        return ResponseHelper.ok(userDetailMapper.convertToDto(updatedUser), ResponseMessage.UPDATE_SUCCESS);
    }

    @Transactional
    @Override
    public TypeResponse<Void> deleteUser(UUID id) {
        Optional<UserEntity> userExisting = userDetailRepository.findById(id);
        if (userExisting.isEmpty()) {
            return ResponseHelper.notFound(ResponseMessage.USER_NOT_FOUND);
        }
        try {
            if (userExisting.get().getAvatar() != null && !userExisting.get().getAvatar().isEmpty()) {
                cloudinaryService.deleteFile(userExisting.get().getAvatar());
            }
            UserEntity user = userExisting.get();
            user.setLocked(true);
            userDetailRepository.save(user);
            return ResponseHelper.ok(null, ResponseMessage.DELETE_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.DELETE_FAILED);
        }
    }

    @Transactional
    @Override
    public TypeResponse<Void> deleteOwnAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Optional<UserEntity> currentUserOpt = userDetailRepository.findByEmail(currentUsername);
        if (currentUserOpt.isEmpty()) {
            return ResponseHelper.notFound(ResponseMessage.USER_NOT_FOUND);
        }
        try {
            UserEntity currentUser = currentUserOpt.get();
            if (currentUser.getAvatar() != null && !currentUser.getAvatar().isEmpty()) {
                cloudinaryService.deleteFile(currentUser.getAvatar());
            }
            userDetailRepository.deleteById(currentUser.getId());
            return ResponseHelper.ok(null, ResponseMessage.DELETE_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.DELETE_FAILED);
        }
    }

}
