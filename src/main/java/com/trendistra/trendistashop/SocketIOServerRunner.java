package com.trendistra.trendistashop;

import com.corundumstudio.socketio.SocketIOServer;
import jakarta.annotation.PreDestroy;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class SocketIOServerRunner implements ApplicationRunner {
    @Autowired
    private SocketIOServer server;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        server.start();
        log.info("Socket.IO server started on port " + server.getConfiguration().getPort());
    }
    @PreDestroy
    public void stop() {
        server.stop();
        log.info("Socket.IO server stopped");
    }
}
