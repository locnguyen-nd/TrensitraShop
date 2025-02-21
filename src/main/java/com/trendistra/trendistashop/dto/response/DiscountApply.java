package com.trendistra.trendistashop.dto.response;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
@Data
@Builder
public class DiscountApply {
    private String code ;
    private BigDecimal valueApply;
    private BigDecimal saved;
}
