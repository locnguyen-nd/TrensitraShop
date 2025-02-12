package com.trendistra.trendistashop.services.impl.notification;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.trendistra.trendistashop.config.JWTTokenHelper;
import com.trendistra.trendistashop.dto.response.ChatMessageDTO;
import com.trendistra.trendistashop.entities.notification.ChatMessage;
import com.trendistra.trendistashop.entities.user.UserEntity;
import com.trendistra.trendistashop.mapper.ChatMapper;
import com.trendistra.trendistashop.repositories.notification.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;


@Component
public class ChatEventHandler {
    private final SocketIOServer server;
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private ChatMapper chatMapper;
    @Autowired
    private JWTTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    private UserEntity user = new UserEntity();
    public ChatEventHandler(SocketIOServer server) {
        this.server = server;
        this.server.addConnectListener(this::onConnect);
        this.server.addDisconnectListener(this::onDisconnect);
        this.server.addEventListener("chat_message", ChatMessageDTO.class, this::onChatMessage);
    }

    // khi client ket noi
    @OnConnect
    public void onConnect(SocketIOClient client) {
     System.out.println("Client connected: " + client.getSessionId());
     String token = client.getHandshakeData().getSingleUrlParam("token");
     String authName = jwtTokenHelper.getUserNameFromToken(token);
        user = (UserEntity) userDetailsService.loadUserByUsername(authName);
    }
    // Khi client ngắt kết nối
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        System.out.println("Client disconnected: " + client.getSessionId());
    }
    // Lắng nghe sự kiện "chat_message"
    @OnEvent("chat_message")
    public void onChatMessage(SocketIOClient client, ChatMessageDTO data, AckRequest ackRequest) {
        System.out.println("Received message: " + data);
        System.out.println("Timestamp: " + data.getTimestamp());
        System.out.println("Content: " + data.getContent());
        System.out.println("From Admin: " + data.isFromAdmin());

        try {
            // Chuyển đổi timestamp từ long sang LocalDateTime
            LocalDateTime messageTime = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(data.getTimestamp()), ZoneId.systemDefault()
            );

            // Tạo entity lưu trữ lịch sử chat
            ChatMessage entity = ChatMessage.builder()
                    .sender(user.getFirstName() + " " + user.getLastName())
                    .content(data.getContent())
                    .timestamp(messageTime)
                    .userId(user.getId())
                    .user(user)
                    .isAdmin(data.isFromAdmin())
                    .build();

            // Lưu vào CSDL
            chatMessageRepository.save(entity);
            System.out.println("Message saved: " + entity);

            // Broadcast tin nhắn đến tất cả client
            String room = data.isFromAdmin() ? user.getId().toString() : "admin";
            server.getRoomOperations(room).sendEvent("chat_message", chatMapper.chatMessageDTO(entity));

        } catch (Exception ex) {
            System.err.println("Error handling chat message: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
