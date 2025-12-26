package com.jishan.eventprocessing.service.impl;

import com.jishan.eventprocessing.entity.Event;
import com.jishan.eventprocessing.repository.EventRepository;
import com.jishan.eventprocessing.service.AdminEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminEventServiceImpl implements AdminEventService {

    private final EventRepository eventRepository;

    @Override
    public Page<Event> getAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    @Override
    public Page<Event> getEventsByCompany(String companyId, Pageable pageable) {
        return eventRepository.findByCompanyId(companyId, pageable);
    }

    @Override
    public Long getTotalEventCount() {
        return eventRepository.count();
    }

    @Override
    public Map<String, Long> getCompanyWiseCounts() {
        return eventRepository.countEventsByCompany();
    }
}

