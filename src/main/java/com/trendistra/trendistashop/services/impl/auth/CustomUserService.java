package com.trendistra.trendistashop.services.impl.auth;

import com.trendistra.trendistashop.Util.ResponseHelper;
import com.trendistra.trendistashop.dto.request.UserUpdateDTO;
import com.trendistra.trendistashop.dto.response.UserDetailDTO;
import com.trendistra.trendistashop.dto.response.TypeResponse;
import com.trendistra.trendistashop.entities.user.RoleEntity;
import com.trendistra.trendistashop.entities.user.UserEntity;
import com.trendistra.trendistashop.mapper.UserDetailMapper;
import com.trendistra.trendistashop.repositories.auth.RoleRepository;
import com.trendistra.trendistashop.repositories.auth.UserDetailRepository;
import com.trendistra.trendistashop.services.CloudinaryService;
import com.trendistra.trendistashop.services.ICustomUserService;
import com.trendistra.trendistashop.specifications.UserSpecification;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user= userDetailRepository.findByEmail(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("User Not Found with userName "+username);
        }
        return user.get();
    }

    @Override
    public TypeResponse<List<UserDetailDTO>> getAllUser() {
        try {
            Specification<UserEntity> userEntitySpecification = UserSpecification.hasRoleName("USER");
            List<UserEntity> users = userDetailRepository.findAll(userEntitySpecification);
            if(users == null || users.isEmpty()) {
                return ResponseHelper.notFound("Không tồn tại user");
            }
            return ResponseHelper.ok(userDetailMapper.getUserDtos(users), "Lấy danh sách người dùng thành công");
        } catch (Exception e) {
            return ResponseHelper.serverError("Lỗi hệ thống: " + e.getMessage());
        }
    }

    @Override
    public TypeResponse<UserDetailDTO> getUserById(UUID id) {
        Optional<UserEntity> userOpt = userDetailRepository.findById(id);
        if (userOpt.isEmpty()) {
            return ResponseHelper.notFound("Không tìm thấy người dùng");
        }
        UserEntity user = userOpt.get();
        return ResponseHelper.ok(userDetailMapper.convertToDto(user), "Lấy thông tin người dùng thành công");
    }

    @Override
    @Transactional
    public TypeResponse<UserDetailDTO> updateUser(UserUpdateDTO userUpdateDTO, MultipartFile avatarFile) throws IOException {
        Optional<UserEntity> userOpt = userDetailRepository.findById(userUpdateDTO.getId());
        if (userOpt.isEmpty()) {
            return ResponseHelper.notFound("Không tìm thấy người dùng");
        }
        UserEntity user = userOpt.get();

        if (avatarFile != null && !avatarFile.isEmpty() && user.getAvatar() != null) {
            cloudinaryService.deleteFile(user.getAvatar());
        }
        if (avatarFile != null && !avatarFile.isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(avatarFile, null, "AVATAR");
            user.setAvatar(imageUrl);
        }

        user.setFirstName(userUpdateDTO.getFirstName());
        user.setLastName(userUpdateDTO.getLastName());
        user.setPhoneNumber(userUpdateDTO.getPhoneNumber());

        UserEntity updatedUser = userDetailRepository.save(user);
        return ResponseHelper.ok(userDetailMapper.convertToDto(updatedUser), "Cập nhật người dùng thành công");
    }

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
            return ResponseHelper.ok(userDetailMapper.convertToDto(updatedUser), "Gán vai trò cho người dùng thành công");
    }

    @Override
    public TypeResponse<Void> deleteUser(UUID id) {
        Optional<UserEntity> userExisting = userDetailRepository.findById(id);
        if (userExisting.isEmpty()) {
            return ResponseHelper.notFound("Không tìm thấy người dùng");
        }
        if (userExisting.get().getAvatar() != null && !userExisting.get().getAvatar().isEmpty()) {
            cloudinaryService.deleteFile(userExisting.get().getAvatar());
        }
        UserEntity user = userExisting.get();
        user.setLocked(true);
        userDetailRepository.save(user);
        return ResponseHelper.ok(null, "Xóa người dùng thành công");
    }

    @Override
    public TypeResponse<Void> deleteOwnAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Optional<UserEntity> currentUserOpt = userDetailRepository.findByEmail(currentUsername);
        if (currentUserOpt.isEmpty()) {
            return ResponseHelper.notFound("Không tìm thấy người dùng");
        }
        UserEntity currentUser = currentUserOpt.get();
        if(currentUser.getAvatar() != null && !currentUser.getAvatar().isEmpty()) {
            cloudinaryService.deleteFile(currentUser.getAvatar());
        }
        userDetailRepository.deleteById(currentUser.getId());
        return ResponseHelper.ok(null, "Xóa tài khoản thành công");
    }

}
