package com.trendistashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// Nếu chạy scheduler thì bỏ comment dòng dưới
//@EnableScheduling
public class TrendistaShopApplication  {
	public static void main(String[] args) {
		SpringApplication.run(TrendistaShopApplication.class, args);
	}
}
