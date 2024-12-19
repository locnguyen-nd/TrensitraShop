package com.trendistra.trendistashop;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(servers = { @Server(url = "https://trendista.up.railway.app/", description = "TRENDISTA API Server") })
@SpringBootApplication
public class TrendistaShopApplication {
	public static void main(String[] args) {
		SpringApplication.run(TrendistaShopApplication.class, args);
	}

}
