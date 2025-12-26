package com.jishan.eventprocessing.security;

import com.jishan.eventprocessing.config.RateLimitProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class RateLimitResolver {

    //private RateLimitResolver() {}
    private final RateLimitProperties properties;

    public static int resolveLimit(String uri, String method, String role) {

        // Auth login
        if (uri.startsWith("/api/auth/login")) {
            return role.equals("ROLE_ADMIN")
                    ? RateLimitConfig.LOGIN_LIMIT_ADMIN
                    : RateLimitConfig.LOGIN_LIMIT_USER;
        }

        // Admin APIs
        if (uri.startsWith("/api/admin")) {
            return RateLimitConfig.ADMIN_EVENTS_GET;
        }

        // Event APIs
        if (uri.startsWith("/api/events")) {

            if ("POST".equalsIgnoreCase(method)) {
                return role.equals("ROLE_ADMIN")
                        ? RateLimitConfig.EVENTS_POST_ADMIN
                        : RateLimitConfig.EVENTS_POST_USER;
            }

            if ("GET".equalsIgnoreCase(method)) {
                return role.equals("ROLE_ADMIN")
                        ? RateLimitConfig.EVENTS_GET_ADMIN
                        : RateLimitConfig.EVENTS_GET_USER;
            }
        }

        // Default fallback (safe)
        return 100;
    }
}

