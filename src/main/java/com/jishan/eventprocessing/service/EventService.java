package com.jishan.eventprocessing.service;

import com.jishan.eventprocessing.entity.Event;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface EventService {

    Event saveEvent(Event event);

    List<Event> getEventsByUserId(String userId);

    List<Event> getEventsByUserIdAndType(String userId, String eventType);

    Page<Event> getEventsByUserId(String userId, Pageable pageable);

    Page<Event> getEventsByUserIdAndType(String userId, String eventType, Pageable pageable);

    List<Event> getAllEvents();

    Page<Event> getAllEvents(Pageable pageable);
}

