package com.trendistashop.dto.chat;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author Locnd
 */
@Data
public class AutoReplyConfigDTO {
    private UUID id;
    private Set<String> triggerKeywords = new HashSet<>();
    private String replyMessage;
    private boolean enabled = true;
    private String scope;
    private boolean autoChatEnabled = true;
}
