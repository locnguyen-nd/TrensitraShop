package com.trendistra.trendistashop.services;

import com.trendistra.trendistashop.entities.user.UserEntity;
import com.trendistra.trendistashop.repositories.auth.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VerificationCodeService {
    @Autowired
    private UserDetailRepository userRepository;
    @Scheduled(fixedRate = 60000) // Chạy mỗi phút
    public void cleanupExpiredCodes() {
        userRepository.deleteExpiredVerificationCodes(LocalDateTime.now());
    }
}
