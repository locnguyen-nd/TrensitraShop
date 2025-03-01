package com.trendistra.trendistashop.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPassword {
    @NotEmpty(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String  email;
    @NotEmpty(message = "Password không được để trống")
    @Size(min = 8, message = "Password phải có ít nhất 8 ký tự")
    private String  password;
    @NotEmpty(message = "Code không được để trống")
    @Size(min = 6, message = "Code phải 6 ký tự")
    private String code;

}
