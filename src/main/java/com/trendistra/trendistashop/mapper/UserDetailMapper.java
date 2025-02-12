package com.trendistra.trendistashop.mapper;

import com.trendistra.trendistashop.dto.response.UserDetailDTO;
import com.trendistra.trendistashop.entities.user.RoleEntity;
import com.trendistra.trendistashop.entities.user.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserDetailMapper {
    private final ModelMapper modelMapper;
    public UserDetailMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public UserDetailDTO convertToDto(UserEntity userEntity) {
        UserDetailDTO userDTO = modelMapper.map(userEntity, UserDetailDTO.class);
        userDTO.setAuthorityList(
                userEntity.getRoles()
                        .stream()
                        .map(roleEntity -> roleEntity.getId())
                        .collect(Collectors.toList()));
        userDTO.setAddressList(userEntity.getAddressList());
        return userDTO;
    }
    public UserEntity convertToEntity(UserDetailDTO userDTO) {
        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);

        // Handle fields like roles if necessary
        if (userDTO.getAuthorityList() != null) {
            List<RoleEntity> roles = new ArrayList<>();
            for (Object role :  userDTO.getAuthorityList()) {
                if (role instanceof RoleEntity) {
                    roles.add((RoleEntity) role);
                }
            }
            userEntity.setRoles(roles);
        }

        return userEntity;
    }
    public List<UserDetailDTO> getUserDtos(List<UserEntity> userEntities) {
        return userEntities.stream().map(this::convertToDto).toList();
    }
}
