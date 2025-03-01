package com.trendistra.trendistashop.services.impl.auth;

import com.trendistra.trendistashop.dto.request.UserUpdateDTO;
import com.trendistra.trendistashop.dto.response.UserDetailDTO;
import com.trendistra.trendistashop.entities.user.RoleEntity;
import com.trendistra.trendistashop.entities.user.UserEntity;
import com.trendistra.trendistashop.exceptions.ResourceNotFoundEx;
import com.trendistra.trendistashop.mapper.UserDetailMapper;
import com.trendistra.trendistashop.repositories.auth.RoleRepository;
import com.trendistra.trendistashop.repositories.auth.UserDetailRepository;
import com.trendistra.trendistashop.services.CloudinaryService;
import com.trendistra.trendistashop.services.ICustomUserService;
import com.trendistra.trendistashop.services.VerificationCodeService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

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
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user= userDetailRepository.findByEmail(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("User Not Found with userName "+username);
        }
        return user.get();
    }
    @Override
    public List<UserDetailDTO> getAllUser() {
        Specification<UserEntity> userEntitySpecification = UserSpecification.hasRoleName("USER");
        List<UserEntity> users = userDetailRepository.findAll(userEntitySpecification);
        if(users == null) {
            throw new ResourceNotFoundEx("Không tồn tại user");
        }
        return userDetailMapper.getUserDtos(users);
    }

    @Override
    public UserDetailDTO getUserById(UUID id) {
        return null;
    }

    @Override
    @Transactional
    public UserDetailDTO updateUser(UserUpdateDTO userUpdateDTO, MultipartFile avatarFile) throws IOException {
        UserEntity user = userDetailRepository.findById(userUpdateDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundEx("User not found with ID: " + userUpdateDTO.getId()));

        // Xóa ảnh cũ trước khi tải ảnh mới (nếu có ảnh cũ)
        if (avatarFile != null && !avatarFile.isEmpty() && user.getAvatar() != null) {
            cloudinaryService.deleteFile(user.getAvatar());
        }
        // Cập nhật ảnh đại diện nếu có
        if (avatarFile != null && !avatarFile.isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(avatarFile, null, "AVATAR");
            user.setAvatar(imageUrl);
        }

        // Cập nhật thông tin người dùng
        user.setFirstName(userUpdateDTO.getFirstName());
        user.setLastName(userUpdateDTO.getLastName());
        user.setPhoneNumber(userUpdateDTO.getPhoneNumber());

        // Lưu lại dữ liệu
        UserEntity updatedUser = userDetailRepository.save(user);
        return userDetailMapper.convertToDto(updatedUser);
    }

    @Transactional
    public UserDetailDTO assignRolesToUser(UUID userId, Set<UUID> roleIds) {
        Optional<UserEntity> user = userDetailRepository.findById(userId);
        List<RoleEntity> uniqueRoles = new ArrayList<>(user.get().getRoles());
        roleIds.stream()
                .map(roleId -> roleRepository.findById(roleId)
                        .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleId)))
                .forEach(uniqueRoles::add);
        user.get().setRoles(uniqueRoles);
        UserEntity updatedUser = userDetailRepository.save(user.get());
        return userDetailMapper.convertToDto(updatedUser);
    }
    @Override
    public void deleteUser(UUID id) {
        Optional<UserEntity> userExisting = userDetailRepository.findById(id);
        if (userExisting.isEmpty()) {
            throw  new ResourceNotFoundEx("User not found");
        }
        // Xóa ảnh đại diện nếu có
        if (userExisting.get().getAvatar() != null && !userExisting.get().getAvatar().isEmpty()) {
            cloudinaryService.deleteFile(userExisting.get().getAvatar());
        }
        userDetailRepository.deleteById(id);
    }
    public void deleteOwnAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Optional<UserEntity> currentUserOpt = userDetailRepository.findByEmail(currentUsername);
        if (currentUserOpt.isEmpty()) {
            throw new ResourceNotFoundEx("Authenticated user not found.");
        }
        UserEntity currentUser = currentUserOpt.get();
        if(currentUser.getAvatar() != null && !currentUser.getAvatar().isEmpty()) {
            cloudinaryService.deleteFile(currentUser.getAvatar());
        }
        userDetailRepository.deleteById(currentUser.getId());
    }

}
