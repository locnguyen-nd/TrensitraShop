package com.trendistra.trendistashop.dto.request;
import com.trendistra.trendistashop.enums.ProviderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String phoneNumber;

}
