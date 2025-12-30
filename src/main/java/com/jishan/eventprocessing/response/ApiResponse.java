package com.jishan.eventprocessing.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    private final LocalDateTime timestamp;
    private final int status;
    private final boolean success;
    private final String message;
    private final String path;
    private final T data;

    public static <T> ApiResponse<T> success(
            int status,
            String message,
            String path,
            T data) {

        return new ApiResponse<>(
                LocalDateTime.now(),
                status,
                true,
                message,
                path,
                data
        );
    }
   
    public static <T> ApiResponse<T> failure(
            int status,
            String message,
            String path) {

        return new ApiResponse<>(
                LocalDateTime.now(),
                status,
                false,
                message,
                path,
                null
        );
    }
}

