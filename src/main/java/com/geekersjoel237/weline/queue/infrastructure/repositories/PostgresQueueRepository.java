package com.geekersjoel237.weline.queue.infrastructure.repositories;

import com.geekersjoel237.weline.queue.domain.entities.Queue;
import com.geekersjoel237.weline.queue.domain.repositories.QueueRepository;
import com.geekersjoel237.weline.queue.infrastructure.models.QueueEntity;
import com.geekersjoel237.weline.queue.infrastructure.persistence.JpaQueueRepository;
import com.geekersjoel237.weline.shared.domain.exceptions.ErrorOnPersistEntityException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/

@Repository
public class PostgresQueueRepository implements QueueRepository {
    private final JpaQueueRepository jpaQueueRepository;

    public PostgresQueueRepository(JpaQueueRepository jpaQueueRepository) {
        this.jpaQueueRepository = jpaQueueRepository;
    }

    @Override
    public Optional<Queue> ofId(String queueId) {
        return jpaQueueRepository.findByIdWithTickets(queueId)
                .map(QueueEntity::toDomain);

    }

    @Override
    public void save(Queue.Snapshot queue) throws ErrorOnPersistEntityException {
        try {
            var queueEntity = QueueEntity.fromDomain(queue);
            jpaQueueRepository.save(queueEntity);
        } catch (DataAccessException e) {
            throw new ErrorOnPersistEntityException("Error on saving queue: " + e.getMessage());
        }

    }

    @Override
    public void addMany(List<Queue.Snapshot> queues) throws ErrorOnPersistEntityException {
        try {
            var queueEntities = queues.stream()
                    .map(QueueEntity::fromDomain)
                    .toList();
            jpaQueueRepository.saveAll(queueEntities);
        } catch (DataAccessException e) {
            throw new ErrorOnPersistEntityException("Error on saving multiple queues: " + e.getMessage());
        }

    }
}
