package com.jishan.eventprocessing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventRequest {

    @NotBlank(message = "Event type is required")
    private String eventType;

    @NotBlank(message = "User id is required")
    private String userId;

    @NotNull(message = "Event timestamp is required")
    private LocalDateTime eventTimestamp;

    private String metadata;
}

