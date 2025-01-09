package com.trendistra.trendistashop.controllers.auth;

import com.trendistra.trendistashop.config.JWTTokenHelper;
import com.trendistra.trendistashop.entities.user.UserEntity;
import com.trendistra.trendistashop.services.IAuthenticationService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/oauth2")
@Tag(name = "OAuth2")
public class Oauth2Controller {
    private final IAuthenticationService iAuthenticationService;

    public Oauth2Controller(IAuthenticationService iAuthenticationService) {
        this.iAuthenticationService = iAuthenticationService;
    }
    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successfully .",
                    content = {@Content(mediaType = "application/json", schema = @Schema(example = "{ \"email\": \"user@example.com\" }"))}),
            @ApiResponse(responseCode = "400", description = "Error Login With Google.",
                    content = @Content)
    })
    @GetMapping("/success")
    public void createAccountWithGoogle(
            @AuthenticationPrincipal OAuth2User oauth2User,
            HttpServletResponse response) throws IOException {
        try {
            String email = oauth2User.getAttribute("email");
            System.out.println("Email: " + email); // Debug log

            Optional<UserEntity> user = iAuthenticationService.getUser(email);
            if (user.isEmpty()) {
                user = Optional.of(iAuthenticationService.createUserWithGoogle(oauth2User));
            }

            String token = jwtTokenHelper.generateToken(user.get().getUsername());
            response.sendRedirect("http://localhost:3000/oauth2/callback?token=" + token);

        } catch (Exception e) {
            System.err.println("Error in OAuth2 success: " + e.getMessage());
            response.sendRedirect("http://localhost:3000/login?error=true");
        }
    }
}
