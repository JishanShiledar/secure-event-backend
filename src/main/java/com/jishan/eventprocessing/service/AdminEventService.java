package com.jishan.eventprocessing.service;


import com.jishan.eventprocessing.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface AdminEventService {

    Page<Event> getAllEvents(Pageable pageable);

    Page<Event> getEventsByCompany(String companyId, Pageable pageable);

    Long getTotalEventCount();

    Map<String, Long> getCompanyWiseCounts();
}
