package com.trendistra.trendistashop.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyResetPassword {
    @NotEmpty(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String  email;
    @NotEmpty(message = "Code không được để trống")
    @Size(min = 6, message = "Code phải 6 ký tự")
    private String code;
}
