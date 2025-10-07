package com.geekersjoel237.weline.queue.infrastructure.persistence;

import com.geekersjoel237.weline.queue.infrastructure.models.QueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/
public interface JpaQueueRepository extends JpaRepository<QueueEntity, String> {
    @Query("""
            SELECT q FROM QueueEntity q
            LEFT JOIN FETCH q.tickets t
            WHERE q.id = :queueId
            """)
    Optional<QueueEntity> findByIdWithTickets(@Param("queueId") String queueId);
}
