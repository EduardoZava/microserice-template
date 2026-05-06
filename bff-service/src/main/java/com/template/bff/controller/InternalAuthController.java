package com.template.bff.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/internal/auth")
public class InternalAuthController {

    @GetMapping("/validate")
    public ResponseEntity<Map<String, String>> validate(Authentication authentication) {
        return ResponseEntity.ok(Map.of("principal", authentication.getName()));
    }
}

