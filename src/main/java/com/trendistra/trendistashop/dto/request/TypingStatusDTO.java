package com.trendistra.trendistashop.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypingStatusDTO {
    private UUID senderId;
    private UUID receiverId;
    @JsonProperty("isTyping")
    private boolean isTyping;
}
