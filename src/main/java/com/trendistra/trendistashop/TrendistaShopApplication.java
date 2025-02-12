package com.trendistra.trendistashop;

import com.corundumstudio.socketio.SocketIOServer;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class TrendistaShopApplication  {
	public static void main(String[] args) {
		SpringApplication.run(TrendistaShopApplication.class, args);
	}
}
