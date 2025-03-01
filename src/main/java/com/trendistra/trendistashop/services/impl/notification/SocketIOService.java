package com.trendistra.trendistashop.services.impl.notification;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.trendistra.trendistashop.dto.request.TypingStatusDTO;
import com.trendistra.trendistashop.dto.response.ChatMessageDTO;
import com.trendistra.trendistashop.dto.response.NotificationDTO;
import com.trendistra.trendistashop.entities.notification.ChatMessage;
import com.trendistra.trendistashop.repositories.notification.ChatMessageRepository;
import com.trendistra.trendistashop.services.MessageBroadcaster;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@Service
@Slf4j
public class SocketIOService implements MessageBroadcaster {
    private final SocketIOServer server;
    private final ChatMessageRepository chatMessageRepository;
    private final ConcurrentMap<String, SocketIOClient> userSocketMap = new ConcurrentHashMap<>();

    @Autowired
    public SocketIOService(SocketIOServer server, ChatMessageRepository chatMessageRepository) {
        this.server = server;
        this.chatMessageRepository = chatMessageRepository;
    }

    @PostConstruct
    public void init() {
        try {
            // Configure event listeners for connection and disconnection
            this.server.addConnectListener(new ConnectListener() {
                @Override
                public void onConnect(SocketIOClient client) {
                    SocketIOService.this.onConnect(client);
                }
            });

            this.server.addDisconnectListener(new DisconnectListener() {
                @Override
                public void onDisconnect(SocketIOClient client) {
                    SocketIOService.this.onDisconnect(client);
                }
            });

            // Chat event listeners
            this.server.addEventListener("/chat/message", ChatMessageDTO.class, new DataListener<ChatMessageDTO>() {
                @Override
                public void onData(SocketIOClient client, ChatMessageDTO data, AckRequest ackSender) {
                    SocketIOService.this.onChatMessage(client, data, ackSender);
                }
            });

            this.server.addEventListener("/chat/typing", TypingStatusDTO.class, new DataListener<TypingStatusDTO>() {
                @Override
                public void onData(SocketIOClient client, TypingStatusDTO data, AckRequest ackSender) {
                    SocketIOService.this.onTyping(client, data, ackSender);
                }
            });

            this.server.addEventListener("/chat/seen", UUID.class, new DataListener<UUID>() {
                @Override
                public void onData(SocketIOClient client, UUID data, AckRequest ackSender) {
                    SocketIOService.this.onSeen(client, data, ackSender);
                }
            });

            // Start the server
            this.server.start();
            log.info("SocketIO server started successfully on port: {}", server.getConfiguration().getPort());
        } catch (Exception e) {
            log.error("Failed to start SocketIO server: {}", e.getMessage());
            throw new IllegalStateException("SocketIO server failed to start", e);
        }
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        String userId = client.get("userId");
        if (userId != null) {
            userSocketMap.remove(userId);
            log.info("Client disconnected: {} (User ID: {})", client.getSessionId(), userId);
        } else {
            log.info("Client disconnected: {}", client.getSessionId());
        }
    }
    @OnConnect
    public void onConnect(SocketIOClient client) {
        String userId = client.getHandshakeData().getSingleUrlParam("userId");
        if (userId != null) {
            client.set("userId", userId);
            userSocketMap.put(userId, client);
            log.info("User connected: {} (Session ID: {})", userId, client.getSessionId());
        } else {
            log.warn("Client connected without userId: {}", client.getSessionId());
        }
    }

    public void onChatMessage(SocketIOClient client, ChatMessageDTO data, AckRequest ackRequest) {
        log.info("Received chat message: {}", data);
        broadcastMessage(data);
        // Acknowledge message receipt if requested
        if (ackRequest.isAckRequested()) {
            ackRequest.sendAckData("Message received");
        }
    }


    public void onTyping(SocketIOClient client, TypingStatusDTO data, AckRequest ackRequest) {
        log.debug("Typing status update: {}", data);
        SocketIOClient receiverClient = userSocketMap.get(data.getReceiverId().toString());
        if (receiverClient != null) {
            receiverClient.sendEvent("/chat/typing", data);
        }
    }

    public void onSeen(SocketIOClient client, UUID messageId, AckRequest ackRequest) {
        log.debug("Message seen event for message ID: {}", messageId);
        Optional<ChatMessage> messageOpt = chatMessageRepository.findById(messageId);
        messageOpt.ifPresent(message -> {
            if (!message.isRead()) {
                message.setRead(true);
                chatMessageRepository.save(message);
                // Notify the sender that their message was seen
                SocketIOClient senderClient = userSocketMap.get(message.getSender().getId().toString());
                if (senderClient != null) {
                    senderClient.sendEvent("/chat/seen", messageId);
                }
            }
        });
    }

    @Override
    public void broadcastMessage(ChatMessageDTO message) {
        log.debug("Broadcasting message to receiver: {}", message.getReceiverId());
        SocketIOClient receiverClient = userSocketMap.get(message.getReceiverId().toString());
        if (receiverClient != null) {
            receiverClient.sendEvent("/chat/message", message);
        }
    }

    @Override
    public void broadcastNotification(NotificationDTO notification) {
        log.debug("Broadcasting notification to user: {}", notification.getUserId());
        SocketIOClient userClient = userSocketMap.get(notification.getUserId().toString());
        if (userClient != null) {
            userClient.sendEvent("/notification", notification);
        }
    }


}
