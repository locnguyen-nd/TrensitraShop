package com.trendistashop.controllers.user;

import com.trendistashop.docs.user.GetProfileDocs;
import com.trendistashop.docs.user.GetUsersDocs;
import com.trendistashop.docs.user.UpdateUserDocs;
import com.trendistashop.dto.request.UserUpdateDTO;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.dto.response.UserDetailDTO;
import com.trendistashop.entities.user.UserEntity;
import com.trendistashop.mapper.UserDetailMapper;
import com.trendistashop.services.ICustomUserService;
import com.trendistashop.utils.ResponseHelper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Lấy thông tin người dùng")
    @GetProfileDocs
    @GetMapping("/profile")
    public ResponseEntity<TypeResponse<UserDetailDTO>> getProfile(Principal principal) {
        try {
            if (principal == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ResponseHelper.unauthorized("Yêu cầu đăng nhập"));
            }

            UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseHelper.notFound("Không tìm thấy thông tin người dùng"));
            }

            UserDetailDTO userDetailDTO = userDetailMapper.convertToDto(user);
            return ResponseEntity.ok(ResponseHelper.ok(userDetailDTO, "Lấy thông tin người dùng thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseHelper.serverError("Lỗi hệ thống: " + e.getMessage()));
        }
    }

    @Operation(summary = "Lấy danh sách người dùng")
    @GetUsersDocs
    @GetMapping()
    public ResponseEntity<TypeResponse<List<UserDetailDTO>>> getAllUsers() {
        TypeResponse<List<UserDetailDTO>> response = iCustomUserService.getAllUser();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

     @Operation(summary = "Cập nhật ảnh đại diện người dùng")
     @UpdateUserDocs
     @PutMapping(value = "/avatar")
     public ResponseEntity<TypeResponse<UserDetailDTO>> updateUserAvatar(Principal principal, @RequestParam("avatarUrl") String avatarUrl) {
         TypeResponse<UserDetailDTO> response = iCustomUserService.updateAvatarUser(principal,avatarUrl);
         return ResponseEntity.status(response.getStatusCode()).body(response);
     }

    @Operation(summary = "Cập nhật thông tin người dùng")
    @UpdateUserDocs
    @PutMapping()
    public ResponseEntity<TypeResponse<UserDetailDTO>> updateUser(Principal principal,@RequestBody UserUpdateDTO updatedUser) {
        TypeResponse<UserDetailDTO> response = iCustomUserService.updateUser(principal,updatedUser);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @Operation(summary = "Gán vai trò cho người dùng")
    @PostMapping("/{userId}/roles")
    public ResponseEntity<TypeResponse<UserDetailDTO>> assignRoles(
            @PathVariable UUID userId,
            @RequestBody Set<UUID> roleIds) {
        TypeResponse<UserDetailDTO> response = iCustomUserService.assignRolesToUser(userId, roleIds);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @Operation(summary = "Xóa tài khoản người dùng")
    @ApiResponses({
            @ApiResponse(responseCode = "403", description = "Unauthorized access - Admin privileges required."),
            @ApiResponse(responseCode = "404", description = "User Not Found."),
            @ApiResponse(responseCode = "200", description = "Successfully delete user.")
    })
    @DeleteMapping("delete/{userId}")
    public ResponseEntity<String> adminDeleteUser(@PathVariable UUID userId) {
        iCustomUserService.deleteUser(userId);
        return ResponseEntity.ok("Account has been deleted successfully");
    }

    @Operation(summary = "Xóa tài khoản của chính mình")
    @ApiResponses({
            @ApiResponse(responseCode = "403", description = "Unauthorized access ."),
            @ApiResponse(responseCode = "404", description = "User Not Found."),
            @ApiResponse(responseCode = "200", description = "Successfully delete user.")
    })
    @DeleteMapping("delete/me")
    public ResponseEntity<String> deleteOwnAccount() {
        iCustomUserService.deleteOwnAccount();
        return ResponseEntity.ok("Your account has been deleted successfully.");
    }

}
