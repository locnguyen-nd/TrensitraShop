//package com.trendistra.trendistashop.services.impl.notification;
//
//import com.corundumstudio.socketio.AckRequest;
//import com.corundumstudio.socketio.SocketIOClient;
//import com.corundumstudio.socketio.SocketIOServer;
//import com.corundumstudio.socketio.listener.DataListener;
//import com.trendistra.trendistashop.dto.response.NotificationDTO;
//import com.trendistra.trendistashop.entities.notification.Notification;
//import com.trendistra.trendistashop.repositories.notification.NotificationRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class NotificationEventHandler {
//    private final SocketIOServer server;
//    @Autowired
//    private NotificationRepository notificationRepository;
//    public NotificationEventHandler(SocketIOServer server) {
//      this.server = server;
//
//        // Đăng ký event listener với kiểu dữ liệu cụ thể
//        server.addEventListener("notification", NotificationDTO.class,
//                new DataListener<NotificationDTO>() {
//                    @Override
//                    public void onData(SocketIOClient client, NotificationDTO data, AckRequest ackRequest) {
//                        handleNotification(client, data, ackRequest);
//                    }
//                });
//    }
//
//    public void onNotification(SocketIOServer server, NotificationDTO data, AckRequest ackRequest) {
//        Notification notification = Notification.builder()
//                .type(data.getType())
//                .recipient(data.getRecipient())
//                .message(data.getMessage())
//                .build();
//        // Lưu thông báo vào cơ sở dữ liệu
//        notificationRepository.save(notification);
//        // Gửi thông báo đến tất cả client đang kết nối
//        server.getRoomOperations(data.getRecipient().toString())
//                .sendEvent("notification", notification);
//    }
//}
