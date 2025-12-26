package com.jishan.eventprocessing.config;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;

public class RedisTestContainerConfig {

    static final GenericContainer<?> REDIS =
            new GenericContainer<>("redis:7")
                    .withExposedPorts(6379);

    static {
        REDIS.start();
    }

    @DynamicPropertySource
    static void registerRedisProperties(DynamicPropertyRegistry registry) {
        registry.add(
                "spring.redis.host",
                () -> REDIS.getHost()
        );
        registry.add(
                "spring.redis.port",
                () -> REDIS.getMappedPort(6379)
        );
    }
}

