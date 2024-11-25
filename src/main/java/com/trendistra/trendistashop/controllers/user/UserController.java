package com.trendistra.trendistashop.controllers.user;

import com.trendistra.trendistashop.dto.response.LoginResponse;
import com.trendistra.trendistashop.dto.response.UserDetailDTO;
import com.trendistra.trendistashop.entities.auth.UserEntity;
import com.trendistra.trendistashop.mapper.UserDetailMapper;
import com.trendistra.trendistashop.services.ICustomUserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/users")
@CrossOrigin
@Tag(name = "User")
public class UserController {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserDetailMapper userDetailMapper;

    private ICustomUserService iCustomUserService;
    @Autowired
    public UserController(ICustomUserService iCustomUserService) {
        this.iCustomUserService = iCustomUserService;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully fetched user profile.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDetailDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized access - User not authenticated.",
                    content = @Content)
    })
    @GetMapping("/profile")
    public ResponseEntity<UserDetailDTO> getUserProfile (Principal principal) {
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
        if( null == user) {
            return  new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        UserDetailDTO userDetailDTO = userDetailMapper.convertToDto(user);
        return new ResponseEntity<>(userDetailDTO, HttpStatus.OK);
    }
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully fetched all users.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDetailDTO.class))}),
            @ApiResponse(responseCode = "403", description = "Unauthorized access - User not authenticated.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found Any User.",
                    content = @Content)
    })
    @GetMapping()
    public ResponseEntity<List<UserDetailDTO>> getAllUsers() {
        List<UserDetailDTO> users =  iCustomUserService.getAllUser();
        if( users.isEmpty()) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @ApiResponses({
            @ApiResponse( responseCode = "403", description = "Unauthorized access - Admin privileges required."),
            @ApiResponse( responseCode = "404", description = "User Not Found."),
            @ApiResponse(responseCode = "201", description = "Successfully update user.")
    })
    @PutMapping("/{userId}")
    public ResponseEntity<UserEntity> updateUser(@Valid @PathVariable UUID userId , @RequestBody UserDetailDTO userDetailDTO) {
        UserEntity user = iCustomUserService.updateUser(userId , userDetailDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @ApiResponses({
            @ApiResponse( responseCode = "403", description = "Unauthorized access - Admin privileges required."),
            @ApiResponse( responseCode = "404", description = "User Not Found."),
            @ApiResponse(responseCode = "200", description = "Successfully delete user.")
        })
    @DeleteMapping("delete/{userId}")
    public ResponseEntity<String> adminDeleteUser(@PathVariable UUID userId) {
        iCustomUserService.deleteUser(userId);
        return ResponseEntity.ok("Account has been deleted successfully");
    }
    @ApiResponses({
            @ApiResponse( responseCode = "403", description = "Unauthorized access ."),
            @ApiResponse( responseCode = "404", description = "User Not Found."),
            @ApiResponse(responseCode = "200", description = "Successfully delete user.")
    })
    @DeleteMapping("delete/me")
    public ResponseEntity<String> deleteOwnAccount() {
        iCustomUserService.deleteOwnAccount();
        return ResponseEntity.ok("Your account has been deleted successfully.");
    }
}
