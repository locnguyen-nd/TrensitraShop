package com.trendistra.trendistashop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangedEvent {
    private UUID userId;
    private String userName;
    private boolean isAdminInitiated;
}
