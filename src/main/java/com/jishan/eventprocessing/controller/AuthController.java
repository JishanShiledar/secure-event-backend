package com.jishan.eventprocessing.controller;

import com.jishan.eventprocessing.entity.User;
import com.jishan.eventprocessing.repository.UserRepository;
import com.jishan.eventprocessing.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;



import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @GetMapping("/api/auth/encode-password")
    public String encodePassword(@RequestParam String password) {
        return passwordEncoder.encode(password);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String rawPassword = request.get("password");

            System.out.println("Login attempt for username: " + username);

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> {
                        System.out.println("User not found: " + username);
                        return new RuntimeException("Invalid credentials");
                    });

            System.out.println("User found: " + user.getUsername());
            System.out.println("User enabled: " + user.getEnabled());
            System.out.println("User role: " + user.getRole());
            System.out.println("Password from DB (first 20 chars): " +
                    user.getPassword().substring(0, Math.min(20, user.getPassword().length())));
            System.out.println("Raw password length: " + rawPassword.length());

            if (!user.getEnabled()) {
                System.out.println("User is disabled");
                throw new RuntimeException("User disabled");
            }

            System.out.println("Checking password match...");
            boolean passwordMatches = passwordEncoder.matches(rawPassword, user.getPassword());
            System.out.println("Password matches: " + passwordMatches);

            if (!passwordMatches) {
                System.out.println("Password mismatch!");
                throw new RuntimeException("Invalid credentials");
            }

            System.out.println("Generating JWT token...");
            String token = jwtUtil.generateToken(
                    user.getUsername(),
                    user.getRole(),
                    user.getCompanyId()
            );

            System.out.println("Login successful!");
            return Map.of("token", token);

        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}

