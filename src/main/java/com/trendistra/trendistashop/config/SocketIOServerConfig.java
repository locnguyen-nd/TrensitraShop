package com.trendistra.trendistashop.config;

import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.trendistra.trendistashop.services.impl.notification.ChatEventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketIOServerConfig {
    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname("localhost");
        // Chọn port riêng cho Socket.IO
        config.setPort(9092);

        // Cấu hình bổ sung cho socket
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        config.setSocketConfig(socketConfig);

        return new SocketIOServer(config);
    }
    @Bean
    public ChatEventHandler chatEventHandler(SocketIOServer server) {
        return new ChatEventHandler(server);
    }
}