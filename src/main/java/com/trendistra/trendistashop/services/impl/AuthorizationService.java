package com.trendistra.trendistashop.services.impl;

import com.trendistra.trendistashop.entities.auth.RoleEntity;
import com.trendistra.trendistashop.repositories.auth.RoleRepository;
import com.trendistra.trendistashop.services.IAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
@Service
public class AuthorizationService implements IAuthorizationService {
    @Autowired
    private RoleRepository roleRepository;
    public Set<RoleEntity> getUserRole() {
        Set<RoleEntity> roles  = new HashSet<>();
        RoleEntity role = roleRepository.findByName("USER");
        roles.add(role);
        return roles;
    }
}
