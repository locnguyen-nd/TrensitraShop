package com.trendistra.trendistashop.controllers.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendistra.trendistashop.dto.request.UserUpdateDTO;
import com.trendistra.trendistashop.dto.response.UserDetailDTO;
import com.trendistra.trendistashop.entities.user.UserEntity;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Set;
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
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUser(@Valid
                                        @RequestPart(value = "profile") String updateDTOJson,
                                        @RequestPart(value = "avatar", required = false) MultipartFile avatarFile) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        UserUpdateDTO updateDTO = objectMapper.readValue(updateDTOJson, UserUpdateDTO.class);
        try {
            if ((updateDTO == null) && (avatarFile == null || avatarFile.isEmpty())) {
                return ResponseEntity.badRequest().body("Không có dữ liệu để cập nhật");
            }
            UserDetailDTO updateUser = iCustomUserService.updateUser( updateDTO, avatarFile);
            return ResponseEntity.ok(updateUser);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi cập nhật: " + e.getMessage());
        }
    }
    @PostMapping("/{userId}/roles")
    public ResponseEntity<UserDetailDTO> assignRoles(
            @PathVariable UUID userId,
            @RequestBody Set<UUID> roleIds
    ) {
        return ResponseEntity.ok(iCustomUserService.assignRolesToUser(userId, roleIds));
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
