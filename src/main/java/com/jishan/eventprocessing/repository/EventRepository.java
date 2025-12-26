package com.jishan.eventprocessing.repository;

import com.jishan.eventprocessing.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {



//    @Query("SELECT e.companyId, COUNT(e) FROM Event e GROUP BY e.companyId")



    @Query("""
        SELECT e.companyId, COUNT(e)
        FROM Event e
        GROUP BY e.companyId
    """)
    List<Object[]> countGroupedByCompany();

    default Map<String, Long> countEventsByCompany() {
        return countGroupedByCompany().stream()
                .collect(Collectors.toMap(
                        r -> (String) r[0],
                        r -> (Long) r[1]
                ));
    }




    // Find all events for a user
    List<Event> findByUserId(String userId);

    // Find all events of a specific type
    List<Event> findByEventType(String eventType);

    // Find events by user and event type
    List<Event> findByUserIdAndEventType(String userId, String eventType);

    Page<Event> findByUserId(String userId, Pageable pageable);

    Page<Event> findByUserIdAndEventType(String userId, String eventType, Pageable pageable);

    List<Event> findByCompanyId(String companyId);

    Page<Event> findByCompanyId(String companyId, Pageable pageable);

    List<Event> findByCompanyIdAndUserId(String companyId, String userId);

    Page<Event> findByCompanyIdAndUserId(String companyId, String userId, Pageable pageable);

    List<Event> findByUserIdAndCompanyId(String userId, String companyId);

    List<Event> findByUserIdAndEventTypeAndCompanyId(
            String userId,
            String eventType,
            String companyId
    );

    Page<Event> findByUserIdAndCompanyId(
            String userId,
            String companyId,
            Pageable pageable
    );

    Page<Event> findByUserIdAndEventTypeAndCompanyId(
            String userId,
            String eventType,
            String companyId,
            Pageable pageable
    );

}

