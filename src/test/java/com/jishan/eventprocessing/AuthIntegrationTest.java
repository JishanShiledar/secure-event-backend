package com.jishan.eventprocessing;

import com.jishan.eventprocessing.config.PostgresTestContainerConfig;
import com.jishan.eventprocessing.config.RedisTestContainerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class AuthIntegrationTest extends BaseIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void login_shouldReturnJwtToken() {

        Map<String, String> body = Map.of(
                "username", "companyA_service",
                "password", "password123"
        );

        ResponseEntity<Map> response =
                restTemplate.postForEntity(
                        "/api/auth/login",
                        body,
                        Map.class
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsKey("token");
    }
}

