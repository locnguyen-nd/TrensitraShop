package com.trendistashop.dto.response;
import com.trendistashop.enums.DiscountApplyFor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class DiscountApply {
    private UUID id ;
    private String code;
    private DiscountApplyFor applyType;
    private BigDecimal valueApply;
    private BigDecimal saved;
}
