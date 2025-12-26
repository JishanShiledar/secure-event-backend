package com.jishan.eventprocessing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class EventIntegrationTest extends BaseIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    private String loginAndGetToken() {
        Map<String, String> body = Map.of(
                "username", "companyA_service",
                "password", "password123"
        );

        ResponseEntity<Map> response =
                restTemplate.postForEntity("/api/auth/login", body, Map.class);

        return (String) response.getBody().get("token");
    }

    @Test
    void createEvent_shouldPersistWithCompanyId() {

        String token = loginAndGetToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String payload = """
                {
                  "eventType": "LOGIN",
                  "userId": "testUser",
                  "eventTimestamp": "2025-01-01T10:00:00",
                  "metadata": "integration-test"
                }
                """;

        HttpEntity<String> request =
                new HttpEntity<>(payload, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(
                        "/api/events",
                        request,
                        Map.class
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}

