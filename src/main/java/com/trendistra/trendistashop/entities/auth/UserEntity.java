package com.trendistra.trendistashop.entities.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trendistra.trendistashop.entities.BaseEntity;
import com.trendistra.trendistashop.enums.ProviderEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Entity class đại diện cho bảng "User" trong cơ sở dữ liệu.
 * Sử dụng JPA để ánh xạ dữ liệu và Lombok để giảm boilerplate code.
 * <p>
 * - @Table: Định nghĩa bảng cơ sở dữ liệu.
 * - @Entity: Đánh dấu class là một entity của JPA.
 * - @Data: Sinh các phương thức getter, setter, toString, equals, hashCode.
 * - @NoArgsConstructor: Tạo constructor không tham số.
 * - @AllArgsConstructor: Tạo constructor đầy đủ tham số.
 * - @Builder: Hỗ trợ xây dựng đối tượng bằng Builder Pattern.
 * <p>
 *    Note: Đảm bảo rằng `StringCryptoConverter` được cấu hình để xử lý mã hóa và giải mã
 *  cho các trường phoneNumber và email.
 * Class này cũng triển khai giao diện {@link UserDetails} để tích hợp với Spring Security.
 */
@Table(name = "User")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    private String phoneNumber;
    @Column(name = "password", length = 255)
    @JsonIgnore
    private String password;
    @Column(name = "avatar")
    private String avatar;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProviderEnum provider;
    private String verificationCode;
    private boolean enabled = false;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "auth_user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id")
    )
    private Set<RoleEntity> roles ;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String prefixRole = "ROLE_";
        Set<GrantedAuthority> authorities = new HashSet<>();
        // Add roles as authorities
        for (RoleEntity role : this.roles) {
            if (role.getName() != null) {
                authorities.add(new SimpleGrantedAuthority(prefixRole + role.getName().toUpperCase()));
            }
            // Add permissions for each role
            if (role.getPermissions() != null) {
                for (PermissionEntity permission : role.getPermissions()) {
                    if (permission.getName() != null) {
                        authorities.add(new SimpleGrantedAuthority(permission.getName()));
                    }
                }
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
