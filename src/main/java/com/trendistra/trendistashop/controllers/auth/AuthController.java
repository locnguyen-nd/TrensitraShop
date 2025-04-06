package com.trendistra.trendistashop.controllers.auth;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trendistra.trendistashop.config.JWTTokenHelper;
import com.trendistra.trendistashop.docs.auth.ForgotPasswordDocs;
import com.trendistra.trendistashop.docs.auth.LoginDocs;
import com.trendistra.trendistashop.docs.auth.LogoutDocs;
import com.trendistra.trendistashop.docs.auth.RefreshTokenDocs;
import com.trendistra.trendistashop.docs.auth.RegisterDocs;
import com.trendistra.trendistashop.docs.auth.ResetPasswordDocs;
import com.trendistra.trendistashop.docs.auth.VerifyEmailDocs;
import com.trendistra.trendistashop.docs.auth.examples.AuthRequestExamples;
import com.trendistra.trendistashop.dto.request.LoginRequest;
import com.trendistra.trendistashop.dto.request.RegisterRequest;
import com.trendistra.trendistashop.dto.request.ResetPassword;
import com.trendistra.trendistashop.dto.response.ErrorResponse;
import com.trendistra.trendistashop.dto.response.LoginResponse;
import com.trendistra.trendistashop.dto.response.RegisterResponse;
import com.trendistra.trendistashop.dto.response.TypeResponse;
import com.trendistra.trendistashop.services.IAuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("${api.prefix}/auth")
@Tag(name = "OAuth")
public class AuthController {

    private final IAuthenticationService iAuthenticationService;
    @Autowired
    public AuthController(IAuthenticationService iAuthenticationService) {
        this.iAuthenticationService = iAuthenticationService;
    }
    
    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    /**
     * Đăng ký người dùng mới vào hệ thống.
     *
     * @param request chứa các thông tin người dùng cần đăng ký như tên, email, số điện thoại, mật khẩu.
     * @return ResponseEntity chứa kết quả đăng ký và mã trạng thái.
     */
    @Operation(summary = "Đăng ký tài khoản mới")
    @RegisterDocs
    @PostMapping("/register")
    public ResponseEntity<TypeResponse<RegisterResponse>> register(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                schema = @Schema(implementation = RegisterRequest.class),
                examples = @ExampleObject(value = AuthRequestExamples.REGISTER_REQUEST)
            )
        )
        @RequestBody @Valid RegisterRequest request) {
        TypeResponse<RegisterResponse> response = iAuthenticationService.createUser(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Xác minh email người dùng thông qua mã xác minh.
     *
     * @param map chứa email và mã xác minh người dùng nhập vào.
     * @return ResponseEntity với mã trạng thái xác nhận nếu mã xác minh đúng, hoặc lỗi nếu sai.
     */
    @Operation(summary = "Xác minh email người dùng", description = "Xác thực email thông qua token được gửi qua email")
    @VerifyEmailDocs
    @PostMapping("/verify")
    public ResponseEntity<TypeResponse<Void>> verifyEmail(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                schema = @Schema(implementation = RegisterRequest.class),
                examples = @ExampleObject(value = AuthRequestExamples.VERIFY_EMAIL_REQUEST)
            )
        )
        @RequestBody Map<String, String> map)  {
        String email = map.get("email");
        String token = map.get("token");
        if (email == null || token == null) {
            return ResponseEntity.badRequest().build();
        }
        TypeResponse<Void> response = iAuthenticationService.verifyUser(email, token);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Gửi lại mã xác minh qua email.
     *
     * @param map chứa email và mã xác minh người dùng nhập vào.
     * @return ResponseEntity với mã trạng thái xác nhận nếu mã xác minh đúng, hoặc lỗi nếu sai.
     */
    @Operation(summary = "Gửi lại email xác thực", description = "Gửi lại email xác thực cho tài khoản chưa được kích hoạt")
    @PostMapping("/resend-verify")
    public ResponseEntity<TypeResponse<Void>> resendVerifyEmail(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                schema = @Schema(implementation = RegisterRequest.class),
                examples = @ExampleObject(value = AuthRequestExamples.RESEND_VERIFY_EMAIL_REQUEST)
            )
        )
        @RequestBody Map<String, String> map)  {
        String email = map.get("email");
        if (email == null) {
            return ResponseEntity.badRequest().build();
        }
        TypeResponse<Void> response = iAuthenticationService.resendTokenVerify(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Đăng nhập người dùng và trả về một JWT token.
     *
     * @param request chứa email và mật khẩu của người dùng cần đăng nhập.
     * @return ResponseEntity chứa JWT token nếu đăng nhập thành công, hoặc lỗi nếu thông tin không chính xác.
     */
    @Operation(summary = "Đăng nhập")
    @LoginDocs
    @PostMapping("/login")
    public ResponseEntity<TypeResponse<LoginResponse>> login(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                schema = @Schema(implementation = RegisterRequest.class),
                examples = @ExampleObject(value = AuthRequestExamples.LOGIN_REQUEST)
            )
        )
        @RequestBody @Valid LoginRequest request) {
        TypeResponse<LoginResponse> response = iAuthenticationService.authenticateUser(request.getEmail(), request.getPassword(), request.getGuard());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Đăng xuất người dùng khỏi hệ thống.
     *
     * @param request chứa token được gửi từ header.
     * @return ResponseEntity chứa JWT token nếu đăng xuất thành công, hoặc lỗi nếu token không hợp lệ.
     */
    @Operation(summary = "Đăng xuất")
    @LogoutDocs
    @PostMapping("/logout")
    public ResponseEntity<TypeResponse<Object>> logout (HttpServletRequest request) {
        String token = jwtTokenHelper.getToken(request);
        if (token == null) {
            return ResponseEntity.badRequest().build();
        }
        TypeResponse<Object> response = iAuthenticationService.logout(token);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
    /**
     * Làm mới token.
     *
     * @param refreshToken chứa token được gửi từ header.
     * @return ResponseEntity chứa JWT token mới nếu làm mới thành công, hoặc lỗi nếu token không hợp lệ.
     */
    @Operation(summary = "Làm mới token")
    @RefreshTokenDocs
    @PostMapping("/refresh-token")
    public ResponseEntity<TypeResponse<Map<String, String>>> refreshToken (@RequestHeader("Refresh-Token") String refreshToken) {
        TypeResponse<Map<String, String>> response = iAuthenticationService.refreshToken(refreshToken);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Quên mật khẩu.
     *
     * @param email chứa email của người dùng cần quên mật khẩu.
     * @return ResponseEntity chứa lỗi nếu email không tồn tại, hoặc không tồn tại.
     */
    @Operation(summary = "Quên mật khẩu")
    @ForgotPasswordDocs
    @PostMapping("/forgot-password")
    public ResponseEntity<TypeResponse<ErrorResponse>> forgotPassword(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                examples = @ExampleObject(value = AuthRequestExamples.FORGOT_PASSWORD_REQUEST)
            )
        )
        @RequestBody String email) {
        TypeResponse<ErrorResponse> response = iAuthenticationService.forgotPassword(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Đặt lại mật khẩu.
     *
     * @param request chứa email và mật khẩu mới của người dùng.
     * @return ResponseEntity chứa lỗi nếu dữ liệu không hợp lệ, hoặc không tồn tại.
     */
    @Operation(summary = "Đặt lại mật khẩu")
    @ResetPasswordDocs
    @PostMapping("/reset-password")
    public ResponseEntity<TypeResponse<ErrorResponse>> resetPassword(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                schema = @Schema(implementation = ResetPassword.class),
                examples = @ExampleObject(value = AuthRequestExamples.RESET_PASSWORD_REQUEST)
            )
        )
        @RequestBody @Valid  ResetPassword request) {
        TypeResponse<ErrorResponse> response = iAuthenticationService.resetPassword(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}   
