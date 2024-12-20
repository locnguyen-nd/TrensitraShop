package com.trendistra.trendistashop.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


import java.util.List;
import java.util.Set;
import java.util.UUID;
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
        private UUID id;
        @NotBlank(message = "Role name is required")
        private  String name;
        private  String description;
        private List<UUID> permissionIds ;
}



