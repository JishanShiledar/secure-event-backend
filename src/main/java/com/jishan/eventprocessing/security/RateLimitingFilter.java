package com.jishan.eventprocessing.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jishan.eventprocessing.response.ApiResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
public class RateLimitingFilter extends OncePerRequestFilter {

    private final RedisRateLimiterService rateLimiterService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RateLimitResolver rateLimitResolver;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Skip rate limiting for auth endpoints
        return request.getRequestURI().startsWith("/api/auth");
    }
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String companyId = TenantContext.getCompanyId();
        String role = SecurityUtils.getCurrentUserRole();

        String uri = request.getRequestURI();

        if (companyId != null && role != null && !uri.startsWith("/api/auth")) {

            String key = "rate:" + companyId + ":" +
                    request.getMethod() + ":" +
                    request.getRequestURI();

            int limit = rateLimitResolver.resolveLimit(
                    request.getRequestURI(),
                    request.getMethod(),
                    role
            );

            boolean allowed = rateLimiterService.isAllowed(
                    key,
                    limit,
                    RateLimitConfig.WINDOW_SECONDS
            );

            if (!allowed) {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.setContentType("application/json");

                // Create error response
                ApiResponse<Void> apiResponse = new ApiResponse<>(
                        LocalDateTime.now(),
                        HttpStatus.TOO_MANY_REQUESTS.value(),
                        false,
                        "Rate limit exceeded. Please try again later.",
                        request.getRequestURI(),
                        null
                );

                response.getWriter().write(
                        objectMapper.writeValueAsString(apiResponse)
                );
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
