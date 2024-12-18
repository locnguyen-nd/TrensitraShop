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
    public UserDetailDTO updateUser( UserUpdateDTO userUpdateDTO, MultipartFile avatarFile) throws IOException {
        Optional<UserEntity> optionalUser = userDetailRepository.findById(userUpdateDTO.getId());
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundEx("User not found with ID: " + userUpdateDTO.getId());
        }
        UserEntity user = optionalUser.get();
        if (avatarFile != null && !avatarFile.isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(avatarFile, null , "AVATAR");
            user.setAvatar(imageUrl);
        }
         user.setFirstName(userUpdateDTO.getFirstName());
         user.setLastName(userUpdateDTO.getLastName());
         user.setEmail(userUpdateDTO.getEmail());
         user.setPhoneNumber(userUpdateDTO.getPhoneNumber());
        return userDetailMapper.convertToDto(userDetailRepository.save(user));
    }
    @Transactional
    public UserDetailDTO assignRolesToUser(UUID userId, Set<UUID> roleIds) {
        Optional<UserEntity> user = userDetailRepository.findById(userId);
        Set<RoleEntity> uniqueRoles = new HashSet<>(user.get().getRoles());
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
        userDetailRepository.deleteById(currentUser.getId());
    }
}
