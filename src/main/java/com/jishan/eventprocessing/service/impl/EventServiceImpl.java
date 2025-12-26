package com.jishan.eventprocessing.service.impl;

import com.jishan.eventprocessing.entity.Event;
import com.jishan.eventprocessing.repository.EventRepository;
import com.jishan.eventprocessing.security.TenantContext;
import com.jishan.eventprocessing.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public Event saveEvent(Event event) {

        // Always set createdAt if missing
        if (event.getCreatedAt() == null) {
            event.setCreatedAt(LocalDateTime.now());
        }

        // 🔐 ALWAYS enforce tenant ownership
        event.setCompanyId(TenantContext.getCompanyId());

        return eventRepository.save(event);
    }

    @Override
    public List<Event> getEventsByUserId(String userId) {
        return eventRepository.findByUserIdAndCompanyId(
                userId,
                TenantContext.getCompanyId()
        );
    }

    @Override
    public List<Event> getEventsByUserIdAndType(String userId, String eventType) {
        return eventRepository.findByUserIdAndEventTypeAndCompanyId(
                userId,
                eventType,
                TenantContext.getCompanyId()
        );
    }

    @Override
    public Page<Event> getEventsByUserId(String userId, Pageable pageable) {
        return eventRepository.findByUserIdAndCompanyId(
                userId,
                TenantContext.getCompanyId(),
                pageable
        );
    }


    @Override
    public Page<Event> getEventsByUserIdAndType(
            String userId,
            String eventType,
            Pageable pageable) {

        return eventRepository.findByUserIdAndEventTypeAndCompanyId(
                userId,
                eventType,
                TenantContext.getCompanyId(),
                pageable
        );
    }

    @Override
    public List<Event> getAllEvents() {
        String companyId = TenantContext.getCompanyId();
        return eventRepository.findByCompanyId(companyId);
    }

    @Override
    public Page<Event> getAllEvents(Pageable pageable) {
        String companyId = TenantContext.getCompanyId();
        return eventRepository.findByCompanyId(companyId, pageable);
    }


}
