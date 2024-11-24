package com.trendistra.trendistashop.services.impl;

import com.trendistra.trendistashop.config.JWTTokenHelper;
import com.trendistra.trendistashop.dto.request.RegisterRequest;
import com.trendistra.trendistashop.dto.response.LoginResponse;
import com.trendistra.trendistashop.dto.response.RegisterResponse;
import com.trendistra.trendistashop.entities.auth.UserEntity;
import com.trendistra.trendistashop.enums.ProviderEnum;
import com.trendistra.trendistashop.exceptions.AuthenticationFailedException;
import com.trendistra.trendistashop.exceptions.ResourceNotFoundEx;
import com.trendistra.trendistashop.exceptions.UnauthorizedException;
import com.trendistra.trendistashop.helper.VerificationCodeGenerator;
import com.trendistra.trendistashop.repositories.auth.UserDetailRepository;
import com.trendistra.trendistashop.services.IAuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

import java.util.Optional;

@Service
public class AuthenticationService implements IAuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    EmailService emailService;
    @Autowired
    JWTTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailRepository userDetailRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthorizationService authorizationService;

    /**
     * Xác thực người dùng bằng username và password, đồng thời sinh token JWT nếu xác thực thành công.
     *
     * @param userName Tên đăng nhập của người dùng.
     * @param password Mật khẩu của người dùng.
     * @return Đối tượng UserToken chứa JWT token.
     * @throws UnauthorizedException         Nếu tài khoản chưa kích hoạt.
     * @throws AuthenticationFailedException Nếu xác thực thất bại.
     */
    @Override
    public LoginResponse authenticateUser(String userName, CharSequence password) {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(userName, password);
            Authentication authenticationResponse = this.authenticationManager.authenticate(authentication);
            if(authenticationResponse.isAuthenticated()) {
                UserEntity user = (UserEntity) authenticationResponse.getPrincipal();
                if (!user.isEnabled()) {
                    log.warn("Tài khoản chưa xác thực: {}", userName);
                    throw new UnauthorizedException("Tài khoản chưa xác thực");
                }
                String token = jwtTokenHelper.generateToken(userName);
                LoginResponse loginResponse = LoginResponse.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .phoneNumber(user.getPhoneNumber())
                        .token(token)
                        .build();
                return loginResponse;
            }
        } catch (BadCredentialsException e) {
            log.warn("Xác thực thất bại cho tài khoản: {}", userName);
            throw new AuthenticationFailedException("Tên người dùng hoặc mật khẩu không chính xác");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.error("Xác thực thất bại không rõ lý do cho tài khoản: {}", userName);
        throw new AuthenticationFailedException("Đăng nhập không thành công");
    }
    /**
     * Tạo tài khoản người dùng mới và gửi sđt xác minh.
     *
     * @param request Thông tin đăng ký từ người dùng (họ, tên, email, số điện thoại, mật khẩu).
     * @return Đối tượng RegisterResponse chứa mã phản hồi và thông điệp kết quả.
     * @throws ServerErrorException Nếu có lỗi trong quá trình tạo tài khoản.
     */
    @Override
    public RegisterResponse createUser(RegisterRequest request) {
        Optional<UserEntity> userExisting = userDetailRepository.findByEmail(request.getEmail());
        if(userExisting.isPresent()){
            log.warn("Tài khoản đã tồn tại với email: {}", request.getEmail());
            throw new UnauthorizedException("Tài khoản đã tồn tại");
        }
        try {
            String code = VerificationCodeGenerator.generateCode();
            log.info("Generated verification code: {}", code);
            UserEntity user = UserEntity.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .phoneNumber(request.getPhoneNumber())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .verificationCode(code)
                    .provider(ProviderEnum.MANUAL)
                    .enabled(false)
                    .roles(authorizationService.getUserRole())
                    .build();
            // save user
            userDetailRepository.save(user);
            //Call send mail
            emailService.sendMail(user);
            return RegisterResponse.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .enabled(user.isEnabled())
                    .build();
        } catch (Exception e) {
            log.error("Error creating account");
            throw new ServerErrorException(e.getMessage(), e.getCause());
        }
    }
    /**
     * Kích hoạt tài khoản người dùng bằng cách xác minh email.
     *
     * @param userName Email của người dùng cần xác minh.
     * @throws ResourceNotFoundEx Nếu không tìm thấy người dùng với email được cung cấp.
     */
    @Override
    public void verifyUser(String userName) {
        Optional<UserEntity> userOptional = userDetailRepository.findByEmail(userName);
        if (userOptional.isEmpty()) {
            log.warn("Không tìm thấy người dùng với email: {}", userName);
            throw new ResourceNotFoundEx("User not found with email: " + userName);
        }
        UserEntity user = userOptional.get();
        user.setEnabled(true);
        userDetailRepository.save(user);
        log.info("Tài khoản với email {} đã được kích hoạt thành công", userName);
    }
}
