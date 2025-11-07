package com.trendistashop.dto.chat;

import lombok.Data;

import java.util.UUID;

/**
 *
 * @author Locnd
 */
@Data
public class AutoReplyConfigDTO {
    private UUID id;
    private String triggerKeyword;
    private String replyMessage;
    private boolean enabled;
    private String scope;
    private boolean autoChatEnabled;
}
