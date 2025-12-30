package com.jishan.eventprocessing.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisRateLimiterService {

    private final StringRedisTemplate redisTemplate;

    public boolean isAllowed(String key, int limit, int windowSeconds) {

        try {
            Long count = redisTemplate.opsForValue().increment(key);

            if (count == 1) {
                redisTemplate.expire(key, windowSeconds, TimeUnit.SECONDS);
            }

            log.info("Rate limit check - Key: {}, Count: {}, Limit: {}, Allowed: {}",
                    key, count, limit, count <= limit);

            return count <= limit;

        } catch (Exception ex) {
            log.error("❌ Redis connection failed! Allowing request to prevent blocking traffic. Error: {}",
                    ex.getMessage());
            return true;
        }
    }

}
