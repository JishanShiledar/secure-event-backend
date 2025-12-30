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
