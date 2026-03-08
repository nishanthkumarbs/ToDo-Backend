package com.ns.todo_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.ns.todo_backend.entity.User;
import com.ns.todo_backend.repository.UserRepository;
import com.ns.todo_backend.security.JwtUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")

public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public User register(@RequestBody User request) {

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("EMAIL_EXISTS");
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(request);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("INVALID_PASSWORD");
        }

        String token = JwtUtil.generateToken(user.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", user);

        return response;
    }

}