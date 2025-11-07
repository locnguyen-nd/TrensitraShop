package com.trendistashop.dto.chat;

import lombok.Data;

import java.util.UUID;

/**
 *
 * @author Locnd
 */
@Data
public class SenderDTO {
    private UUID id;
    private String fullName;
    private String email;
    private String phone;
    private String avatar;
    private String role;
}
