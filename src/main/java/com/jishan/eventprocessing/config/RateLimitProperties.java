package com.jishan.eventprocessing.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "rate-limit")
public class RateLimitProperties {

    private User user = new User();
    private Admin admin = new Admin();

    @Getter @Setter
    public static class User {
        private int get = 200;
        private int post = 100;
    }

    @Getter @Setter
    public static class Admin {
        private int get = 500;
        private int post = 300;
    }
}
