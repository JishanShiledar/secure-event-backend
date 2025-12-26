package com.jishan.eventprocessing.security;

import java.util.Map;

public class RateLimitConfig {

    public static final int LOGIN_LIMIT_USER = 20;
    public static final int LOGIN_LIMIT_ADMIN = 50;

    public static final int EVENTS_POST_USER = 100;
    public static final int EVENTS_POST_ADMIN = 500;

    public static final int EVENTS_GET_USER = 200;
    public static final int EVENTS_GET_ADMIN = 1000;

    public static final int ADMIN_EVENTS_GET = 1000;

    public static final int WINDOW_SECONDS = 60;
}

