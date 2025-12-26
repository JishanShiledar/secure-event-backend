package com.jishan.eventprocessing;

import com.jishan.eventprocessing.entity.User;
import com.jishan.eventprocessing.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SecureEventProcessingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecureEventProcessingBackendApplication.class, args);
    }
//    @Bean
//    public CommandLineRunner testPasswordEncoding(
//            UserRepository userRepository,
//            PasswordEncoder passwordEncoder) {
//        return args -> {
//            System.out.println("========== PASSWORD ENCODING TEST ==========");
//
//            // Test encoding
//            String rawPassword = "password123";
//            String encoded = passwordEncoder.encode(rawPassword);
//            System.out.println("Raw password: " + rawPassword);
//            System.out.println("Encoded: " + encoded);
//
//            // Test from database
//            User user = userRepository.findByUsername("companyA_service").orElse(null);
//            if (user != null) {
//                System.out.println("\nDatabase User Info:");
//                System.out.println("Username: " + user.getUsername());
//                System.out.println("Enabled: " + user.getEnabled());
//                System.out.println("Role: " + user.getRole());
//                System.out.println("Company ID: " + user.getCompanyId());
//                System.out.println("Password (first 20 chars): " + user.getPassword().substring(0, 20));
//                System.out.println("Password length: " + user.getPassword().length());
//
//                // Test password match
//                boolean matches = passwordEncoder.matches(rawPassword, user.getPassword());
//                System.out.println("\nPassword matches: " + matches);
//
//                if (!matches) {
//                    System.out.println("❌ PASSWORD MISMATCH!");
//                    System.out.println("Testing if password needs update...");
//
//                    // Try to update password
//                    String newEncoded = passwordEncoder.encode("password123");
//                    System.out.println("New encoded password: " + newEncoded);
//                    System.out.println("Copy this and run the UPDATE SQL manually");
//                }
//            } else {
//                System.out.println("❌ User not found!");
//            }
//
//            System.out.println("============================================");
//        };
//    }

    @Bean
    public CommandLineRunner testRedisConnection(StringRedisTemplate redisTemplate) {
        return args -> {
            try {
                redisTemplate.opsForValue().set("test:startup", "OK");
                String value = redisTemplate.opsForValue().get("test:startup");
                System.out.println("✅ Redis connection successful! Value: " + value);
                redisTemplate.delete("test:startup");
            } catch (Exception e) {
                System.err.println("❌ REDIS CONNECTION FAILED: " + e.getMessage());
                System.err.println("❌ Rate limiting will NOT work until Redis is running!");
            }
        };
    }

}
