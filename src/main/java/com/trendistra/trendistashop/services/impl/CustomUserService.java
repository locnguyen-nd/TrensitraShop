package com.trendistra.trendistashop.services.impl;

import com.trendistra.trendistashop.dto.response.LoginResponse;
import com.trendistra.trendistashop.dto.response.UserDetailDTO;
import com.trendistra.trendistashop.entities.auth.UserEntity;
import com.trendistra.trendistashop.exceptions.ResourceNotFoundEx;
import com.trendistra.trendistashop.mapper.UserDetailMapper;
import com.trendistra.trendistashop.repositories.auth.UserDetailRepository;
import com.trendistra.trendistashop.services.ICustomUserService;
import com.trendistra.trendistashop.specifications.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomUserService implements UserDetailsService, ICustomUserService {
    @Autowired
    private UserDetailRepository userDetailRepository;
    @Autowired
    private UserDetailMapper userDetailMapper;
    /**
     * Tìm người dùng theo email và trả về thông tin người dùng nếu tồn tại.
     * Phương thức này được sử dụng để xác thực người dùng trong Spring Security khi đăng nhập.
     *
     * @param username Email của người dùng muốn đăng nhập
     * @return UserDetails đối tượng chứa thông tin người dùng đã xác thực (trả về đối tượng UserEntity)
     * @throws UsernameNotFoundException nếu không tìm thấy người dùng có email này trong hệ thống
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userDetailRepository.findByEmail(username);
        if(user.isEmpty()) {
            throw new UsernameNotFoundException("User Not Found with email" + username);
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
    public UserEntity updateUser(UUID userId , UserDetailDTO userDetailDTO) {
        Optional<UserEntity> optionalUser = userDetailRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundEx("User not found with ID: " + userId);
        }
        return userDetailRepository.save(userDetailMapper.convertToEntity(userDetailDTO));
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
