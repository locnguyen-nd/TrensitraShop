package com.trendistashop.services.impl.notification;

import com.trendistashop.constants.ResponseMessage;
import com.trendistashop.dto.chat.AutoReplyConfigDTO;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.entities.notification.AutoReplyConfig;
import com.trendistashop.entities.notification.TriggerKeyword;
import com.trendistashop.repositories.notification.AutoReplyConfigRepository;
import com.trendistashop.utils.ResponseHelper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service xử lý cấu hình Auto Reply
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AutoReplyService {

    private final AutoReplyConfigRepository autoReplyRepo;
    private final ModelMapper modelMapper;

    public TypeResponse<List<AutoReplyConfigDTO>> getAllConfigs() {
        try {
            List<AutoReplyConfigDTO> response = autoReplyRepo.findAll().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            log.info("Found {} reply configs", response.size());
            return  ResponseHelper.ok(response, ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseHelper.badRequest(ResponseMessage.FETCH_FAILED);
        }
    }

    public TypeResponse<AutoReplyConfigDTO> createConfig(AutoReplyConfigDTO dto) {
        try {
            AutoReplyConfig entity = convertToEntity(dto);
            entity = autoReplyRepo.save(entity);
            return ResponseHelper.created(convertToDTO(entity), ResponseMessage.CREATE_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.badRequest(ResponseMessage.CREATE_FAILED);
        }
    }

    public TypeResponse<AutoReplyConfigDTO> updateConfig(UUID id, AutoReplyConfigDTO dto) {
        try {
            AutoReplyConfig entity = autoReplyRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("AutoReplyRule not found with id: " + id));
            modelMapper.map(dto, entity);
            updateTriggerKeywords(entity, dto.getTriggerKeywords());
            entity = autoReplyRepo.save(entity);
            return ResponseHelper.ok(convertToDTO(entity), ResponseMessage.UPDATE_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.badRequest(ResponseMessage.UPDATE_FAILED);
        }
    }

    public TypeResponse<Void> deleteConfig(UUID id) {
        try {
            autoReplyRepo.deleteById(id);
            return ResponseHelper.ok(null, ResponseMessage.DELETE_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.badRequest(ResponseMessage.DELETE_FAILED);
        }
    }
    public TypeResponse<Boolean> toggleAutoChat(boolean enabled) {
        try {
            List<AutoReplyConfig> rules = autoReplyRepo.findAll();
            rules.forEach(rule -> rule.setAutoChatEnabled(enabled));
            autoReplyRepo.saveAll(rules);
            log.info("Auto chat enabled set to " + enabled);
            return ResponseHelper.ok(enabled, ResponseMessage.UPDATE_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseHelper.badRequest(ResponseMessage.UPDATE_FAILED);
        }
    }
    public TypeResponse<Boolean> isAutoChatEnabled() {
        try {
            Boolean isEnable = autoReplyRepo.findAll().stream()
                    .findFirst()
                    .map(AutoReplyConfig::isAutoChatEnabled)
                    .orElse(true);
            log.info("Auto chat enabled set to  " + isEnable);
            return ResponseHelper.ok(isEnable, ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseHelper.badRequest(ResponseMessage.FETCH_FAILED);
        }
    }
    @PostConstruct
    public void initDefaultConfig() {
        if (autoReplyRepo.count() == 0) {
            AutoReplyConfig defaultRule = AutoReplyConfig.builder()
                    .replyMessage("Xin chào! Mình là trợ lý ảo của Trendista Shop.")
                    .enabled(true)
                    .scope("GENERAL")
                    .autoChatEnabled(true)
                    .build();
            // Thêm nhiều trigger keyword
            if (defaultRule.getTriggerKeywords() == null) {
                defaultRule.setTriggerKeywords(new HashSet<>());
            }
            Set<TriggerKeyword> keywords = Set.of(
                    createKeyword("hello"),
                    createKeyword("xin chào"),
                    createKeyword("hi"),
                    createKeyword("chào"),
                    createKeyword("hế lô")
            );
            keywords.forEach(defaultRule::addTriggerKeyword);
            autoReplyRepo.save(defaultRule);
        }
    }
    private TriggerKeyword createKeyword(String keyword) {
        TriggerKeyword tk = new TriggerKeyword();
        tk.setKeyword(keyword.trim().toLowerCase());
        return tk;
    }
    private void updateTriggerKeywords(AutoReplyConfig entity, Set<String> keywordStrings) {
        entity.getTriggerKeywords().removeIf(tk ->
                keywordStrings == null || !keywordStrings.contains(tk.getKeyword())
        );
        if (keywordStrings != null) {
            Set<String> existingKeywords = entity.getTriggerKeywords().stream()
                    .map(TriggerKeyword::getKeyword)
                    .collect(Collectors.toSet());

            keywordStrings.stream()
                    .map(String::trim)
                    .map(String::toLowerCase)
                    .filter(k -> !existingKeywords.contains(k))
                    .map(k -> {
                        TriggerKeyword tk = new TriggerKeyword();
                        tk.setKeyword(k);
                        return tk;
                    })
                    .forEach(entity::addTriggerKeyword);
        }
    }
    private AutoReplyConfigDTO convertToDTO(AutoReplyConfig entity) {
        AutoReplyConfigDTO dto = modelMapper.map(entity, AutoReplyConfigDTO.class);
        Set<String> keywords = entity.getTriggerKeywords().stream()
                .map(TriggerKeyword::getKeyword)
                .collect(Collectors.toSet());
        dto.setTriggerKeywords(keywords);
        return dto;
    }
    private AutoReplyConfig convertToEntity(AutoReplyConfigDTO dto) {
        AutoReplyConfig entity = modelMapper.map(dto, AutoReplyConfig.class);
        Set<TriggerKeyword> keywords = dto.getTriggerKeywords().stream()
                .map(k -> {
                    TriggerKeyword tk = new TriggerKeyword();
                    tk.setKeyword(k.trim().toLowerCase());
                    tk.setRule(entity);
                    return tk;
                })
                .collect(Collectors.toSet());
        entity.setTriggerKeywords(keywords);
        return entity;
    }
}