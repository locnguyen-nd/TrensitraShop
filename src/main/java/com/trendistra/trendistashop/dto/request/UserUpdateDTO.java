package com.trendistra.trendistashop.dto.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    @Length(min = 10, max = 15)
    private String phoneNumber;
    @URL(message = "Image URL must be a valid URL")
    private String avatar;
}
