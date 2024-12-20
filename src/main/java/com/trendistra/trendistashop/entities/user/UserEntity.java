package com.trendistra.trendistashop.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trendistra.trendistashop.entities.BaseEntity;
import com.trendistra.trendistashop.enums.ProviderEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

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
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Address> addressList;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "auth_user_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id")
    )
    private List<RoleEntity> roles ;
    @Override
    public Collection<? extends  GrantedAuthority> getAuthorities() {
        String prefixRole = "ROLE_";
        List<GrantedAuthority> authorities = new ArrayList<>();
        // Add roles as authorities
        for (RoleEntity role : roles) {
            if (role.getName() != null) {
                authorities.add(new SimpleGrantedAuthority(prefixRole + role.getName().toUpperCase()));
//                System.out.println("Added role: " + prefixRole + role.getName().toUpperCase());
            }
            if (roles != null) {
                // Add permissions for each role
                if (role.getPermissions() != null) {
                    for (PermissionEntity permission : role.getPermissions()) {
                        if (permission.getName() != null) {
                            authorities.add(new SimpleGrantedAuthority(permission.getName()));
//                            System.out.println("Added permission: " + permission.getName());
                        } else {
                            System.out.println("Permission name is null for permission: " + permission);
                        }
                    }
                } else {
                    System.out.println("Role permissions are null for role: " + role.getName());
                }
            }else {
                System.out.println("Role permissions are null for role: " + role.getName());
            }
        }
        System.out.println("User authorities: " + authorities);
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
