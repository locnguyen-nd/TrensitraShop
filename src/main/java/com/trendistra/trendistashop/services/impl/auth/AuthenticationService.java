package com.trendistra.trendistashop.services.impl.auth;

import com.trendistra.trendistashop.Util.ResponseHelper;
import com.trendistra.trendistashop.config.JWTTokenHelper;
import com.trendistra.trendistashop.dto.request.RegisterRequest;
import com.trendistra.trendistashop.dto.request.ResetPassword;
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
                if(guard != null && guard == GuardType.ADMIN){
                    if(user.getRoles().stream().noneMatch(role -> role.getName().equalsIgnoreCase("admin"))){
                        return ResponseHelper.validationError("email", "Tài khoản không có quyền truy cập");
                    }
                }
                if (user.isLocked()) {
                    return ResponseHelper.unauthorized("Tài khoản đã bị khóa. Vui lòng liên hệ admin!");
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
                return ResponseHelper.ok(loginResponse, "Đăng nhập thành công");
            }
        } catch (BadCredentialsException e) {
            return ResponseHelper.validationError("email", "Tên người dùng hoặc mật khẩu không chính xác");
        }
        return ResponseHelper.badRequest("Đăng nhập không thành công");
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
            return ResponseHelper.validationError("email", "Tài khoản đã tồn tại");
        }
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return ResponseHelper.validationError("confirmPassword", "Mật khẩu không khớp");
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

            return ResponseHelper.created(registerResponse,
                    "Đăng ký thành công. Kiểm tra email để kích hoạt tài khoản.");
        } catch (Exception e) {
            return ResponseHelper.serverError("Lỗi tạo tài khoản");
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
                return ResponseHelper.badRequest("Link xác thực không hợp lệ hoặc đã hết hạn");
            }

            String tokenEmail = jwtTokenHelper.getUserNameFromToken(token);
            if (!email.equals(tokenEmail)) {
                return ResponseHelper.badRequest("Token không hợp lệ cho email này");
            }

            Optional<UserEntity> userOpt = userDetailRepository.findByEmail(email);
            if (userOpt.isEmpty()) {
                return ResponseHelper.badRequest("Không tìm thấy người dùng với email này");
            }

            UserEntity user = userOpt.get();

            if (user.isEnabled()) {
                return ResponseHelper.badRequest("Tài khoản đã được kích hoạt trước đó");
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
            return ResponseHelper.ok(null, "Xác thực tài khoản thành công");

        } catch (Exception e) {
            log.error("Lỗi xác thực tài khoản: ", e);
            return ResponseHelper.serverError("Lỗi xác thực tài khoản");
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
                return ResponseHelper.validationError("email", "Không tìm thấy người dùng với email này");
            }

            UserEntity user = userOpt.get();
            if (user.isEnabled()) {
                return ResponseHelper.validationError("email", "Tài khoản đã được kích hoạt trước đó");
            }

            int attemptCount = verificationAttemptRepository.countByUserIdAndCreatedAtAfter(
                    user.getId(),
                    LocalDateTime.now().minusHours(12));

            if (attemptCount >= maxAttempts) {
                return ResponseHelper.badRequest("Bạn đã gửi quá " + maxAttempts + " lần. Vui lòng thử lại sau 12 giờ");
            }

            String verificationToken = jwtTokenHelper.generateVerificationToken(user.getEmail());
            VerificationAttempt attempt = VerificationAttempt.builder()
                    .userId(user.getId())
                    .email(user.getEmail())
                    .token(verificationToken)
                    .build();
            verificationAttemptRepository.save(attempt);

            emailService.sendVerificationEmail(user, verificationToken);

            return ResponseHelper.ok(null,
                    String.format("Yêu cầu xác thực thành công. Vui lòng kiểm tra email. Bạn còn %d lần gửi",
                            (maxAttempts - attemptCount - 1)));

        } catch (Exception e) {
            log.error("Lỗi gửi lại email xác thực: ", e);
            return ResponseHelper.serverError("Lỗi xác thực tài khoản");
        }
    }

    @Override
    public TypeResponse<Object> logout(String token) {
        if (token == null) {
            return ResponseHelper.badRequest("Token không hợp lệ");
        }
        try {
            jwtTokenHelper.logout(token);
            return ResponseHelper.ok(null, "Đăng xuất thành công");
        } catch (Exception e) {
            return ResponseHelper.badRequest("Đăng xuất thất bại");
        }
    }

    @Override
    public TypeResponse<Map<String, String>> refreshToken(String refreshToken) {
        if (refreshToken == null) {
            return ResponseHelper.badRequest("Refresh token không hợp lệ");
        }

        try {
            String newToken = jwtTokenHelper.refreshToken(refreshToken);
            Map<String, String> tokenData = new HashMap<>();
            tokenData.put("token", newToken);

            return ResponseHelper.ok(tokenData, "Làm mới token thành công");
        } catch (Exception e) {
            return ResponseHelper.badRequest("Làm mới token thất bại");
        }
    }

    @Override
    public TypeResponse<ErrorResponse> forgotPassword(String email) {
        try {
            Optional<UserEntity> userOpt = userDetailRepository.findByEmail(email);
            if (userOpt.isEmpty()) {
                return ResponseHelper.validationError("email", "Không tìm thấy người dùng với email này");
            }
            UserEntity user = userOpt.get();
            String verificationCode = VerificationCodeGenerator.generateCode();
            user.setVerificationCode(verificationCode);
            user.setCodeExpiry(LocalDateTime.now().plusMinutes(15));
            userDetailRepository.save(user);
            emailService.sendMail(user);

            ErrorResponse errorResponse = new ErrorResponse(200, "Mã xác thực đã được gửi qua email");
            return ResponseHelper.ok(errorResponse, "Gửi mã xác thực thành công");
        } catch (Exception e) {
            return ResponseHelper.serverError("Lỗi khi xử lý yêu cầu quên mật khẩu");
        }
    }

    @Override
    public TypeResponse<ErrorResponse> resetPassword(ResetPassword resetPassword) {
        try {
            Optional<UserEntity> userOpt = userDetailRepository.findByEmail(resetPassword.getEmail());
            if (userOpt.isEmpty()) {
                return ResponseHelper.validationError("email", "Không tìm thấy người dùng với email này");
            }
            UserEntity user = userOpt.get();
            if (user.getVerificationCode() == null || !user.getVerificationCode().equals(resetPassword.getCode())) {
                return ResponseHelper.validationError("code", "Mã xác thực không hợp lệ");
            }
            if (user.getCodeExpiry() == null || user.getCodeExpiry().isBefore(LocalDateTime.now())) {
                return ResponseHelper.validationError("code", "Mã xác thực đã hết hạn");
            }

            user.setPassword(passwordEncoder.encode(resetPassword.getPassword()));
            user.setVerificationCode(null);
            user.setCodeExpiry(null);
            userDetailRepository.save(user);

            accountNotificationService.notifyPasswordChanged(user.getId());

            ErrorResponse errorResponse = new ErrorResponse(200, "Đổi mật khẩu thành công!");
            return ResponseHelper.ok(errorResponse, "Đổi mật khẩu thành công");

        } catch (Exception e) {
            return ResponseHelper.serverError("Lỗi khi đặt lại mật khẩu");
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
