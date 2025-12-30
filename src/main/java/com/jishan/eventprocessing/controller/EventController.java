package com.jishan.eventprocessing.controller;
import com.jishan.eventprocessing.response.ApiResponse;


import com.jishan.eventprocessing.dto.EventRequest;
import com.jishan.eventprocessing.entity.Event;
import com.jishan.eventprocessing.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;


import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

   
    @PostMapping
    public ResponseEntity<ApiResponse<Event>> createEvent( @Valid @RequestBody EventRequest request,
                                                           HttpServletRequest httpRequest) {

        Event event = Event.builder()
                .eventType(request.getEventType())
                .userId(request.getUserId())
                .eventTimestamp(request.getEventTimestamp())
                .metadata(request.getMetadata())
                .createdAt(LocalDateTime.now())
                .build();

        Event savedEvent = eventService.saveEvent(event);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                HttpStatus.CREATED.value(),
                                "Event created successfully",
                                httpRequest.getRequestURI(),
                                savedEvent
                        )
                );
    }

    
    @GetMapping
    public ResponseEntity<ApiResponse<Page<Event>>> getAllEvents(
            @PageableDefault(size = 10, sort = "eventTimestamp") Pageable pageable,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Events fetched successfully",
                        request.getRequestURI(),
                        eventService.getAllEvents(pageable)
                )
        );
    }

    
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<Page<Event>>> getEventsByUser(
            @PathVariable String userId,
            @PageableDefault(size = 10, sort = "eventTimestamp") Pageable pageable,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Events fetched successfully",
                        request.getRequestURI(),
                        eventService.getEventsByUserId(userId, pageable)
                )
        );
    }


    
    @GetMapping("/user/{userId}/type/{eventType}")
    public ResponseEntity<ApiResponse<Page<Event>>> getEventsByUserAndType(
            @PathVariable String userId,
            @PathVariable String eventType,
            @PageableDefault(size = 10, sort = "eventTimestamp") Pageable pageable,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Events fetched successfully",
                        request.getRequestURI(),
                        eventService.getEventsByUserIdAndType(userId, eventType, pageable)
                )
        );
    }

}


