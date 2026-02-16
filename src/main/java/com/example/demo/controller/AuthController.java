package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.model.LoginRequest;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername());

        if (user != null && user.getPassword().equals(request.getPassword())) {

            String token = JwtUtil.generateToken(user.getUsername());

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return response;
        }

        throw new RuntimeException("Invalid credentials");
    }
}
