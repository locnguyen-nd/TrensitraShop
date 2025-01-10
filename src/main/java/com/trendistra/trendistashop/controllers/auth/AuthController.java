package com.trendistra.trendistashop.controllers.auth;
import com.trendistra.trendistashop.config.JWTTokenHelper;
import com.trendistra.trendistashop.dto.request.LoginRequest;
import com.trendistra.trendistashop.dto.request.RegisterRequest;
import com.trendistra.trendistashop.dto.response.LoginResponse;
import com.trendistra.trendistashop.dto.response.RegisterResponse;
import com.trendistra.trendistashop.entities.user.UserEntity;
import com.trendistra.trendistashop.services.IAuthenticationService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    private UserDetailsService userDetailsService;
    @Autowired
    private JWTTokenHelper jwtTokenHelper;
    /**
     * Đăng ký người dùng mới vào hệ thống.
     *
     * @param request chứa các thông tin người dùng cần đăng ký như tên, email, số điện thoại, mật khẩu.
     * @return ResponseEntity chứa kết quả đăng ký và mã trạng thái.
     */

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User successfully registered.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RegisterResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid registration details or user already exists.",
                    content = @Content)
    })
    @PostMapping("/signup")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request) {
        RegisterResponse registerResponse = iAuthenticationService.createUser(request);
        return new ResponseEntity<>(registerResponse, (registerResponse != null) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
    }
    /**
     * Xác minh email người dùng thông qua mã xác minh.
     *
     * @param map chứa email và mã xác minh người dùng nhập vào.
     * @return ResponseEntity với mã trạng thái xác nhận nếu mã xác minh đúng, hoặc lỗi nếu sai.
     */
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Email successfully verified.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(example = "{ \"email\": \"user@example.com\", \"code\": \"123456\" }"))}),
            @ApiResponse(responseCode = "400", description = "Invalid verification code or email.",
                    content = @Content)
    })
    @PostMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestBody Map<String, String> map)  {
        String email = map.get("email");
        String code = map.get("code");
        if (email != null && code != null) {
            iAuthenticationService.verifyUser(email, code);
            return ResponseEntity.ok().body(Map.of(
                    "message", "Verify successful",
                    "status", HttpStatus.OK
            ));
        }
        return ResponseEntity.ok().body(Map.of(
                "message", "Verify error",
                "status", HttpStatus.BAD_REQUEST
        ));
    }
    /**
     * Đăng nhập người dùng và trả về một JWT token.
     *
     * @param request chứa email và mật khẩu của người dùng cần đăng nhập.
     * @return ResponseEntity chứa JWT token nếu đăng nhập thành công, hoặc lỗi nếu thông tin không chính xác.
     */
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Email successfully verified.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(example = "{ \"email\": \"user@example.com\", \"code\": \"123456\" }"))}),
            @ApiResponse(responseCode = "400", description = "Invalid verification code or email.",
                    content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse userInfo = iAuthenticationService.authenticateUser(request.getEmail(), request.getPassword());
        return new ResponseEntity<>(userInfo, userInfo != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout (HttpServletRequest request) {
        String token = jwtTokenHelper.getToken(request);
        if(token != null) {
            try {
                    iAuthenticationService.logout(token);
                    return ResponseEntity.ok().body(Map.of(
                            "message", "Logout successful",
                            "status", HttpServletResponse.SC_OK
                    ));
            } catch (Exception ex) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(Map.of(
                        "message", "Logout error",
                        "status", HttpServletResponse.SC_BAD_REQUEST
                ));
            }
        }
        return ResponseEntity.badRequest().body(Map.of(
                "message", "No token found",
                "status", HttpStatus.BAD_REQUEST
        ));
    }
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken (@RequestHeader("Refresh-Token") String refreshToken) {
        String newToken = iAuthenticationService.refreshToken(refreshToken);
        if (newToken != null) {
            return ResponseEntity.ok().body(Map.of(
                    "token", newToken,
                    "status", HttpStatus.OK
            ));
        }
        return ResponseEntity.badRequest().body(Map.of(
                "message", "No token found",
                "status", HttpStatus.BAD_REQUEST
        ));    }

}
