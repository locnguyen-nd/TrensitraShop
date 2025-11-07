package com.trendistashop.services.impl.notification;

import com.trendistashop.dto.chat.AutoReplyConfigDTO;
import com.trendistashop.entities.notification.AutoReplyConfig;
import com.trendistashop.repositories.notification.AutoReplyConfigRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author Locnd
 */
@Service
@RequiredArgsConstructor
public class AutoReplyService {
    private final AutoReplyConfigRepository autoReplyRepo;
    private final ModelMapper modelMapper;
    public List<AutoReplyConfigDTO> getAllConfigs() {
        return autoReplyRepo.findAll().stream()
                .map(cfg -> modelMapper.map(cfg, AutoReplyConfigDTO.class))
                .collect(Collectors.toList());
    }
    public AutoReplyConfigDTO createConfig(AutoReplyConfigDTO dto) {
        AutoReplyConfig entity = modelMapper.map(dto, AutoReplyConfig.class);
        entity = autoReplyRepo.save(entity);
        return modelMapper.map(entity, AutoReplyConfigDTO.class);
    }

    public AutoReplyConfigDTO updateConfig(UUID id, AutoReplyConfigDTO dto) {
        AutoReplyConfig entity = autoReplyRepo.findById(id).get();
        modelMapper.map(dto, entity);
        entity = autoReplyRepo.save(entity);
        return modelMapper.map(entity, AutoReplyConfigDTO.class);
    }

    public void deleteConfig(UUID id) {
        autoReplyRepo.deleteById(id);
    }

    // Bật/tắt toàn bộ AutoChat
    public void toggleAutoChat(boolean enabled) {
        List<AutoReplyConfig> configs = autoReplyRepo.findAll();
        configs.forEach(cfg -> cfg.setAutoChatEnabled(enabled));
        autoReplyRepo.saveAll(configs);
    }

    public boolean isAutoChatEnabled() {
        return autoReplyRepo.findAll().stream()
                .findFirst()
                .map(AutoReplyConfig::isAutoChatEnabled)
                .orElse(true);
    }
    /** Khởi tạo rule nếu DB chưa có*/
    @PostConstruct
    public void initDefaultConfig() {
        if (autoReplyRepo.count() == 0) {
            autoReplyRepo.save(AutoReplyConfig.builder()
                    .triggerKeyword("hello")
                    .replyMessage("Xin chào! Mình là trợ lý ảo")
                    .enabled(true)
                    .scope("GENERAL")
                    .autoChatEnabled(true)
                    .build());
        }
    }
}
