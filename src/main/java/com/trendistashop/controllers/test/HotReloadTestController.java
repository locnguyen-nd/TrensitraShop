package com.trendistashop.controllers.test;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/test")
public class HotReloadTestController {

    @GetMapping("/reload")
    public ResponseEntity<String> testHotReload() {
        return ResponseEntity.ok("🚀 HOT RELOAD FULLY FIXED TEST! Time: " + LocalDateTime.now() + " ✅✨");
    }

}
