package com.ns.todo_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.ns.todo_backend.entity.User;
import com.ns.todo_backend.repository.UserRepository;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PatchMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody Map<String, String> updates) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));

        if (updates.containsKey("name")) {
            user.setName(updates.get("name"));
        }

        if (updates.containsKey("password")) {
            // Encode the new password before saving
            user.setPassword(passwordEncoder.encode(updates.get("password")));
        }

        if (updates.containsKey("avatar")) {
            user.setAvatar(updates.get("avatar"));
        }

        return userRepository.save(user);
    }
}
