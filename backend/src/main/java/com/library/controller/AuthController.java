package com.library.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody Map<String,String> body) {
        String email = body.getOrDefault("email","");
        String password = body.getOrDefault("password","");
        Map<String,Object> result = new HashMap<>();

        if ("admin@library.com".equalsIgnoreCase(email) && "admin123".equals(password)) {
            result.put("success", true);
            result.put("role", "ADMIN");
            result.put("name", "Library Admin");
            result.put("email", email);
            return result;
        }
        if ("student@library.com".equalsIgnoreCase(email) && "student123".equals(password)) {
            result.put("success", true);
            result.put("role", "STUDENT");
            result.put("name", "Akshara Student");
            result.put("email", email);
            result.put("memberId", 1);
            return result;
        }
        result.put("success", false);
        result.put("message", "Invalid email or password");
        return result;
    }
}
