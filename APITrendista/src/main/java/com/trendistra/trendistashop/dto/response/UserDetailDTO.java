package com.trendistra.trendistashop.dto.response;

import com.trendistra.trendistashop.enums.ProviderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDTO {
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private String email;
        private boolean enabled;
        private ProviderEnum provider;
        private Object authorityList;
}
