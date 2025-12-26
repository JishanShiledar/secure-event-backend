package com.jishan.eventprocessing.controller;

import com.jishan.eventprocessing.entity.Event;
import com.jishan.eventprocessing.response.ApiResponse;
import com.jishan.eventprocessing.service.AdminEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/events")
@RequiredArgsConstructor
public class AdminEventController {

    private final AdminEventService adminEventService;

    // 1️⃣ All events (cross-tenant)
    @GetMapping
    public ApiResponse<Page<Event>> getAllEvents(Pageable pageable) {
        return ApiResponse.success(
                HttpStatus.OK.value(),                    // status (200)
                "All events fetched successfully",        // message
                "/api/admin/events",                      // path
                adminEventService.getAllEvents(pageable)  // data
        );
    }

    // 2️⃣ Events by company
    @GetMapping("/company/{companyId}")
    public ApiResponse<Page<Event>> getEventsByCompany(
            @PathVariable String companyId,
            Pageable pageable
    ) {
        return ApiResponse.success(
                HttpStatus.OK.value(),                                      // status (200)
                "Company events fetched successfully",                      // message
                "/api/admin/events/company/" + companyId,                  // path
                adminEventService.getEventsByCompany(companyId, pageable)  // data
        );
    }

    // 3️⃣ Total event count
    @GetMapping("/count")
    public ApiResponse<Long> getTotalEventCount() {
        return ApiResponse.success(
                HttpStatus.OK.value(),                         // status (200)
                "Total event count fetched successfully",      // message
                "/api/admin/events/count",                     // path
                adminEventService.getTotalEventCount()         // data
        );
    }

    // 4️⃣ Company-wise counts
    @GetMapping("/count/company")
    public ApiResponse<Map<String, Long>> getCompanyWiseCounts() {
        return ApiResponse.success(
                HttpStatus.OK.value(),                              // status (200)
                "Company-wise event count fetched successfully",    // message
                "/api/admin/events/count/company",                  // path
                adminEventService.getCompanyWiseCounts()            // data
        );
    }
}