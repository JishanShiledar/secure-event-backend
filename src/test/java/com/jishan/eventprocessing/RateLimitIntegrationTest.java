package com.jishan.eventprocessing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RateLimitIntegrationTest extends BaseIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    private HttpHeaders authHeaders() {
        Map<String, String> body = Map.of(
                "username", "companyA_service",
                "password", "password123"
        );

        String token = restTemplate
                .postForEntity("/api/auth/login", body, Map.class)
                .getBody()
                .get("token")
                .toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return headers;
    }

    @Test
    void shouldReturn429WhenRateLimitExceeded() {

        HttpHeaders headers = authHeaders();

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<?> lastResponse = null;

        for (int i = 0; i < 20; i++) {
            lastResponse = restTemplate.exchange(
                    "/api/events",
                    HttpMethod.GET,
                    request,
                    Object.class
            );
        }

        assertThat(lastResponse.getStatusCode())
                .isEqualTo(HttpStatus.TOO_MANY_REQUESTS);
    }
}

