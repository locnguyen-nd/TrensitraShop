package com.trendistra.trendistashop.services.impl.auth;

import com.trendistra.trendistashop.config.JWTTokenHelper;
import com.trendistra.trendistashop.dto.request.RegisterRequest;
import com.trendistra.trendistashop.dto.request.ResetPassword;
import com.trendistra.trendistashop.dto.response.ErrorResponse;
import com.trendistra.trendistashop.dto.response.LoginResponse;
import com.trendistra.trendistashop.dto.response.RegisterResponse;
import com.trendistra.trendistashop.entities.user.Cart;
import com.trendistra.trendistashop.entities.user.RoleEntity;
import com.trendistra.trendistashop.entities.user.UserEntity;
import com.trendistra.trendistashop.enums.ProviderEnum;
import com.trendistra.trendistashop.exceptions.AuthenticationFailedException;
import com.trendistra.trendistashop.exceptions.ResourceNotFoundEx;
import com.trendistra.trendistashop.exceptions.UnauthorizedException;
import com.trendistra.trendistashop.helper.VerificationCodeGenerator;
import com.trendistra.trendistashop.repositories.auth.UserDetailRepository;
import com.trendistra.trendistashop.repositories.order.CartRepository;
import com.trendistra.trendistashop.services.IAuthenticationService;
import com.trendistra.trendistashop.services.VerificationCodeService;
import com.trendistra.trendistashop.services.impl.notification.AccountNotificationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private AccountNotificationService accountNotificationService;
    private final Set<String> blacklistedTokens = new HashSet<>();

    public Optional<UserEntity> getUser(String userName) {

        return userDetailRepository.findByEmail(userName);
    }

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
            if (authenticationResponse.isAuthenticated()) {
                UserEntity user = (UserEntity) authenticationResponse.getPrincipal();
                // nếu người dùng chưa xác thực, mã hết hạn thì gửi lại
                if (!user.isEnabled() && user.getVerificationCode() == null && user.getCodeExpiry() == null) {
                    String code = VerificationCodeGenerator.generateCode();
                    user.setVerificationCode(code);
                    user.setCodeExpiry(LocalDateTime.now().plusMinutes(10));
                    userDetailRepository.save(user);
                    emailService.sendMail(user);
                }
                String token = jwtTokenHelper.generateToken(userName);
                LoginResponse loginResponse = LoginResponse.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .phoneNumber(user.getPhoneNumber())
                        .authorityList(user.getRoles().stream()
                                .map(role -> "ROLE_" + role.getName().toUpperCase())
                                .collect(Collectors.toList()))
                        .isEnabled(user.isEnabled())
                        .token(token)
                        .build();
                return loginResponse;
            }
        } catch (BadCredentialsException e) {
            log.warn("Xác thực thất bại cho tài khoản: {}", userName);
            throw new AuthenticationFailedException("Tên người dùng hoặc mật khẩu không chính xác");
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
        if (userExisting.isPresent()) {
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
                    .codeExpiry(LocalDateTime.now().plusMinutes(10))
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
    @Transactional
    @Override
    public void verifyUser(String userName, String code) {
        UserEntity user = userDetailRepository.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (user.getVerificationCode() == null || !user.getVerificationCode().equals(code)) {
            throw new IllegalArgumentException("Invalid verification code.");
        }

        if (user.getCodeExpiry().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Verification code has expired.");
        }
        // Kiểm tra nếu user đã được kích hoạt
        if (user.isEnabled()) {
            log.info("Tài khoản với email {} đã được kích hoạt trước đó", userName);
            return;
        }
        user.setEnabled(true);
        // Khởi tạo cart nếu chưa có
        if (user.getUserCart() == null) {
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.setCartItems(new ArrayList<>());
            newCart.setCartTotal(BigDecimal.ZERO);
            user.setUserCart(newCart);
        }
        user.setVerificationCode(null);
        user.setCodeExpiry(null);
        userDetailRepository.save(user); // Chỉ save một lần
        log.info("Tài khoản với email {} đã được kích hoạt thành công", userName);
    }
    @Override
    public void logout(String token) {
        if (token != null) {
            try {
                jwtTokenHelper.logout(token);
                log.info("logout success !");
            } catch (Exception e) {
                throw new ServerErrorException(e.getMessage(), e.getCause());
            }
        }
    }
    @Override
    public String refreshToken(String refreshToken) {
        if(refreshToken != null) {
            try {
               String newToken = jwtTokenHelper.refreshToken(refreshToken);
               log.info("Refresh token success !");
               return newToken;
            } catch (Exception e) {
                log.error("Refresh token err");
                throw new ServerErrorException(e.getMessage(), e.getCause());
            }
        }
        return  null;
    }

    @Override
    public ErrorResponse forgotPassword(String email) {
        Optional<UserEntity> userOpt = userDetailRepository.findByEmail(email);
        if (!userOpt.isPresent()) {
            return new ErrorResponse(404, "Không tìm thấy người dùng với email này");        }
        UserEntity user = userOpt.get();
        // Tạo mã xác thực (6 ký tự) và thời gian hết hạn (15 phút)
        String verificationCode = VerificationCodeGenerator.generateCode();
        user.setVerificationCode(verificationCode);
        user.setCodeExpiry(LocalDateTime.now().plusMinutes(15));
        userDetailRepository.save(user);
        // Gửi mã qua email sử dụng EmailService đã có
        emailService.sendMail(user);
        return new ErrorResponse(200, "Mã xác thực đã được gửi qua email");    }

    @Override
    public ErrorResponse resetPassword(ResetPassword resetPassword) {
        Optional<UserEntity> userOpt = userDetailRepository.findByEmail(resetPassword.getEmail());
        if (!userOpt.isPresent()) {
            return new ErrorResponse(404, "Không tìm thấy người dùng với email này");        }
        UserEntity user = userOpt.get();
        if (user.getVerificationCode() == null || !user.getVerificationCode().equals(resetPassword.getCode())) {
            return new ErrorResponse (400,"Mã xác thực không hợp lệ");
        }
        if (user.getCodeExpiry() == null || user.getCodeExpiry().isBefore(LocalDateTime.now())) {
            return new ErrorResponse (400,"Mã xác thực đã hết hạn");
        }
        // Mã hoá mật khẩu mới và cập nhật
        user.setPassword(passwordEncoder.encode(resetPassword.getPassword()));
        // Xóa thông tin mã xác thực sau khi đổi mật khẩu
        user.setVerificationCode(null);
        user.setCodeExpiry(null);
        userDetailRepository.save(user);
        // gửi thông báo
        accountNotificationService.notifyPasswordChanged(user.getId());
        return new ErrorResponse(200, "Đổi mật khẩu thành công!");
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
