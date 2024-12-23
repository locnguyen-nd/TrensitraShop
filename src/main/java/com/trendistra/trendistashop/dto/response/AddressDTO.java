package com.trendistra.trendistashop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {
    private UUID id;
    private String name;
    private String city;
    private String district;
    private String ward;
    private String specAddress;
    private String phoneNumber;
    private Boolean isDefaultAddress;

}
