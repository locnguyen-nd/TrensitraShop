package com.trendistra.trendistashop.services.impl.auth;

import com.trendistra.trendistashop.constants.ResponseMessage;
import com.trendistra.trendistashop.Util.ResponseHelper;
import com.trendistra.trendistashop.config.JWTTokenHelper;
import com.trendistra.trendistashop.dto.request.RegisterRequest;
import com.trendistra.trendistashop.dto.request.ResetPassword;
import com.trendistra.trendistashop.dto.request.VerifyResetPassword;
import com.trendistra.trendistashop.dto.response.ErrorResponse;
import com.trendistra.trendistashop.dto.response.LoginResponse;
import com.trendistra.trendistashop.dto.response.RegisterResponse;
import com.trendistra.trendistashop.dto.response.TypeResponse;
import com.trendistra.trendistashop.entities.user.Cart;
import com.trendistra.trendistashop.entities.user.UserEntity;
import com.trendistra.trendistashop.entities.user.VerificationAttempt;
import com.trendistra.trendistashop.enums.GuardType;
import com.trendistra.trendistashop.enums.ProviderEnum;
import com.trendistra.trendistashop.exceptions.AuthenticationFailedException;
import com.trendistra.trendistashop.exceptions.ResourceNotFoundEx;
import com.trendistra.trendistashop.exceptions.UnauthorizedException;
import com.trendistra.trendistashop.helper.VerificationCodeGenerator;
import com.trendistra.trendistashop.repositories.auth.UserDetailRepository;
import com.trendistra.trendistashop.repositories.auth.VerificationAttemptRepository;
import com.trendistra.trendistashop.services.IAuthenticationService;
import com.trendistra.trendistashop.services.impl.notification.AccountNotificationService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    private VerificationAttemptRepository verificationAttemptRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private AccountNotificationService accountNotificationService;

    @Value("${verification.max_attempts}")
    private int maxAttempts = 5;
    @Value("${jwt.auth.expires_in}")
    private int expiresIn;

    public Optional<UserEntity> getUser(String userName) {

        return userDetailRepository.findByEmail(userName);
    }

    /**
     * Xác thực người dùng bằng username và password, đồng thời sinh token JWT nếu
     * xác thực thành công.
     *
     * @param userName Tên đăng nhập của người dùng.
     * @param password Mật khẩu của người dùng.
     * @return Đối tượng UserToken chứa JWT token.
     * @throws UnauthorizedException         Nếu tài khoản chưa kích hoạt.
     * @throws AuthenticationFailedException Nếu xác thực thất bại.
     */
    @Override
    public TypeResponse<LoginResponse> authenticateUser(String userName, CharSequence password, GuardType guard) {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(userName, password);
            Authentication authenticationResponse = this.authenticationManager.authenticate(authentication);
            if (authenticationResponse.isAuthenticated()) {
                UserEntity user = (UserEntity) authenticationResponse.getPrincipal();
                if (guard != null && guard == GuardType.ADMIN) {
                    if (user.getRoles().stream().noneMatch(role -> role.getName().equalsIgnoreCase("admin"))) {
                        return ResponseHelper.validationError("email", ResponseMessage.UNAUTHORIZED);
                    }
                }
                if (user.isLocked()) {
                    return ResponseHelper.unauthorized(ResponseMessage.ACCOUNT_LOCKED);
                }
                if (!user.isEnabled()) {
                    String verificationToken = jwtTokenHelper.generateVerificationToken(user.getEmail());
                    emailService.sendVerificationEmail(user, verificationToken);
                }
                String token = jwtTokenHelper.generateToken(userName);
                LoginResponse loginResponse = LoginResponse.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .phoneNumber(user.getPhoneNumber())
                        .authorityList(user.getRoles().stream()
                                .map(role -> role.getName())
                                .collect(Collectors.toList()))
                        .isEnabled(user.isEnabled())
                        .isLocked(user.isLocked())
                        .token(token)
                        .expiresIn(expiresIn)
                        .build();
                return ResponseHelper.ok(loginResponse, ResponseMessage.AUTHENTICATED);
            }
        } catch (BadCredentialsException e) {
            return ResponseHelper.validationError("email", ResponseMessage.PASSWORD_DO_NOT_MATCH_RECORD);
        }
        return ResponseHelper.serverError(ResponseMessage.SERVER_ERROR);
    }

    /**
     * Tạo tài khoản người dùng mới và gửi sđt xác minh.
     *
     * @param request Thông tin đăng ký từ người dùng (họ, tên, email, số điện
     *                thoại, mật khẩu).
     * @return Đối tượng RegisterResponse chứa mã phản hồi và thông điệp kết quả.
     * @throws ServerErrorException Nếu có lỗi trong quá trình tạo tài khoản.
     */
    @Override
    public TypeResponse<RegisterResponse> createUser(RegisterRequest request) {
        Optional<UserEntity> userExisting = userDetailRepository.findByEmail(request.getEmail());
        if (userExisting.isPresent()) {
            return ResponseHelper.validationError("email", ResponseMessage.EMAIL_EXISTS);
        }
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return ResponseHelper.validationError("confirmPassword", ResponseMessage.PASSWORD_NOT_MATCH);
        }
        try {
            UserEntity user = UserEntity.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .phoneNumber(request.getPhoneNumber())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .provider(ProviderEnum.MANUAL)
                    .enabled(false)
                    .locked(false)
                    .roles(authorizationService.getUserRole())
                    .build();

            userDetailRepository.save(user);

            String verificationToken = jwtTokenHelper.generateVerificationToken(user.getEmail());

            VerificationAttempt attempt = VerificationAttempt.builder()
                    .userId(user.getId())
                    .email(user.getEmail())
                    .token(verificationToken)
                    .build();
            verificationAttemptRepository.save(attempt);

            emailService.sendVerificationEmail(user, verificationToken);

            RegisterResponse registerResponse = RegisterResponse.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .isEnabled(user.isEnabled())
                    .build();

            return ResponseHelper.created(registerResponse, ResponseMessage.CREATE_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.CREATE_FAILED);
        }
    }

    /**
     * Kích hoạt tài khoản người dùng bằng cách xác minh email.
     *
     * @param email Email của người dùng cần xác minh.
     * @throws ResourceNotFoundEx Nếu không tìm thấy người dùng với email được cung
     *                            cấp.
     */
    @Transactional
    @Override
    public TypeResponse<Void> verifyUser(String email, String token) {
        try {
            if (!jwtTokenHelper.validateVerificationToken(token)) {
                return ResponseHelper.badRequest(ResponseMessage.INVALID_OR_EXPIRED_TOKEN);
            }

            String tokenEmail = jwtTokenHelper.getUserNameFromToken(token);
            if (!email.equals(tokenEmail)) {
                return ResponseHelper.badRequest(ResponseMessage.TOKEN_INVALID);
            }

            Optional<UserEntity> userOpt = userDetailRepository.findByEmail(email);
            if (userOpt.isEmpty()) {
                return ResponseHelper.badRequest(ResponseMessage.USER_NOT_FOUND);
            }

            UserEntity user = userOpt.get();

            if (user.isEnabled()) {
                return ResponseHelper.badRequest(ResponseMessage.ACCOUNT_ALREADY_ACTIVATED);
            }

            user.setEnabled(true);

            if (user.getUserCart() == null) {
                Cart newCart = new Cart();
                newCart.setUser(user);
                newCart.setCartItems(new ArrayList<>());
                newCart.setCartTotal(BigDecimal.ZERO);
                user.setUserCart(newCart);
            }

            userDetailRepository.save(user);
            return ResponseHelper.ok(null, ResponseMessage.VERIFICATION_SUCCESS);

        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.VERIFICATION_FAILED);
        }
    }

    /**
     * Gửi lại link xác thực .
     *
     * @param userName Email của người dùng cần xác minh.
     * @throws ResourceNotFoundEx Nếu không tìm thấy người dùng với email được cung
     *                            cấp.
     */
    @Transactional
    @Override
    public TypeResponse<Void> resendTokenVerify(String userName) {
        try {
            Optional<UserEntity> userOpt = userDetailRepository.findByEmail(userName);
            if (userOpt.isEmpty()) {
                return ResponseHelper.validationError("email", ResponseMessage.USER_NOT_FOUND);
            }

            UserEntity user = userOpt.get();
            if (user.isEnabled()) {
                return ResponseHelper.validationError("email", ResponseMessage.ACCOUNT_ALREADY_ACTIVATED);
            }

            int attemptCount = verificationAttemptRepository.countByUserIdAndCreatedAtAfter(
                    user.getId(),
                    LocalDateTime.now().minusHours(12));

            if (attemptCount >= maxAttempts) {
                return ResponseHelper.badRequest(ResponseMessage.TOO_MANY_ATTEMPTS);
            }

            String verificationToken = jwtTokenHelper.generateVerificationToken(user.getEmail());
            VerificationAttempt attempt = VerificationAttempt.builder()
                    .userId(user.getId())
                    .email(user.getEmail())
                    .token(verificationToken)
                    .build();
            verificationAttemptRepository.save(attempt);

            emailService.sendVerificationEmail(user, verificationToken);

            return ResponseHelper.ok(null, ResponseMessage.VERIFICATION_REQUEST_SUCCESS);

        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.SERVER_ERROR);
        }
    }

    @Override
    public TypeResponse<Object> logout(String token) {
        if (token == null) {
            return ResponseHelper.badRequest(ResponseMessage.TOKEN_INVALID);
        }
        try {
            jwtTokenHelper.logout(token);
            return ResponseHelper.ok(null, ResponseMessage.LOGOUT_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.SERVER_ERROR);
        }
    }

    @Override
    public TypeResponse<Map<String, String>> refreshToken(String refreshToken) {
        if (refreshToken == null) {
            return ResponseHelper.badRequest(ResponseMessage.TOKEN_REFRESH_INVALID);
        }

        try {
            String newToken = jwtTokenHelper.refreshToken(refreshToken);
            Map<String, String> tokenData = new HashMap<>();
            tokenData.put("token", newToken);

            return ResponseHelper.ok(tokenData, ResponseMessage.TOKEN_REFRESH_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.TOKEN_REFRESH_FAILURE);
        }
    }

    @Override
    public TypeResponse<ErrorResponse> sendCodeResetPassword(String email) {
        try {
            Optional<UserEntity> userOpt = userDetailRepository.findByEmail(email);
            if (!userOpt.isPresent()) {
                return ResponseHelper.validationError("email", ResponseMessage.USER_NOT_FOUND);
            }
            UserEntity user = userOpt.get();
            String verificationCode = VerificationCodeGenerator.generateCode();
            user.setVerificationCode(verificationCode);
            user.setCodeExpiry(LocalDateTime.now().plusMinutes(15));
            userDetailRepository.save(user);
            emailService.sendMail(user);

            return ResponseHelper.ok(null, ResponseMessage.SEND_CODE_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.SEND_CODE_FAILED);
        }
    }

    @Override
    public TypeResponse<ErrorResponse> resendCodeResetPassword(String email) {
        try {
            Optional<UserEntity> userOpt = userDetailRepository.findByEmail(email);
            if (!userOpt.isPresent()) {
                return ResponseHelper.validationError("email", ResponseMessage.USER_NOT_FOUND);
            }
            UserEntity user = userOpt.get();
            String verificationCode = VerificationCodeGenerator.generateCode();
            user.setVerificationCode(verificationCode);
            user.setCodeExpiry(LocalDateTime.now().plusMinutes(15));
            userDetailRepository.save(user);
            emailService.sendMail(user);

            return ResponseHelper.ok(null, ResponseMessage.SEND_CODE_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.SEND_CODE_FAILED);
        }
    }

    @Override
    public TypeResponse<ErrorResponse> verifyResetPassword(VerifyResetPassword request) {
        try {
            Optional<UserEntity> userOpt = userDetailRepository.findByEmail(request.getEmail());
            if (userOpt.isEmpty()) {
                return ResponseHelper.validationError("email", ResponseMessage.USER_NOT_FOUND);
            }
            UserEntity user = userOpt.get();
            if (user.getVerificationCode() == null || !user.getVerificationCode().equals(request.getCode())) {
                return ResponseHelper.validationError("code", ResponseMessage.INVALID_OTP_CODE);
            }
            if (user.getCodeExpiry() == null || user.getCodeExpiry().isBefore(LocalDateTime.now())) {
                return ResponseHelper.validationError("code", ResponseMessage.EXPIRED_OTP_CODE);
            }

            user.setVerificationCode(null);
            user.setCodeExpiry(null);
            userDetailRepository.save(user);

            return ResponseHelper.ok(null, ResponseMessage.VERIFICATION_SUCCESS);

        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.VERIFICATION_FAILED);
        }
    }

    @Override
    public TypeResponse<ErrorResponse> resetPassword(ResetPassword request) {
        try {
            Optional<UserEntity> userOpt = userDetailRepository.findByEmail(request.getEmail());
            if (userOpt.isEmpty()) {
                return ResponseHelper.validationError("email", ResponseMessage.USER_NOT_FOUND);
            }
            UserEntity user = userOpt.get();

            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return ResponseHelper.validationError("password", ResponseMessage.PASSWORD_SAME_AS_OLD);
            }
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userDetailRepository.save(user);

            accountNotificationService.notifyPasswordChanged(user.getId());

            return ResponseHelper.ok(null, ResponseMessage.UPDATE_SUCCESS);

        } catch (Exception e) {
            System.out.println("error: " + e);
            return ResponseHelper.serverError(ResponseMessage.UPDATE_FAILED);
        }
    }

    @Override
    public UserEntity createUserWithGoogle(OAuth2User oAuth2User) {
        try {
            String firstName = oAuth2User.getAttribute("given_name");
            String lastName = oAuth2User.getAttribute("family_name");
            String email = oAuth2User.getAttribute("email");
            System.out.println(email);
            UserEntity user = UserEntity.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .provider(ProviderEnum.GOOGLE)
                    .enabled(true)
                    .roles(authorizationService.getUserRole())
                    .build();
            if (user.getUserCart() == null) {
                Cart newCart = new Cart();
                newCart.setUser(user);
                newCart.setCartItems(new ArrayList<>());
                newCart.setCartTotal(BigDecimal.ZERO);
                user.setUserCart(newCart);
            }
            return userDetailRepository.save(user);
        } catch (Exception e) {
            log.error("Error creating account");
            throw new ServerErrorException(e.getMessage(), e.getCause());
        }
    }
}
