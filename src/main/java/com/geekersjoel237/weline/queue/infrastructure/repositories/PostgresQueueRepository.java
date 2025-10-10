package com.geekersjoel237.weline.queue.infrastructure.repositories;

import com.geekersjoel237.weline.queue.domain.entities.Queue;
import com.geekersjoel237.weline.queue.domain.repositories.QueueRepository;
import com.geekersjoel237.weline.queue.infrastructure.models.QueueEntity;
import com.geekersjoel237.weline.queue.infrastructure.models.TicketEntity;
import com.geekersjoel237.weline.queue.infrastructure.persistence.JpaQueueRepository;
import com.geekersjoel237.weline.shared.domain.exceptions.ErrorOnPersistEntityException;
import com.geekersjoel237.weline.shared.domain.exceptions.NotFoundEntityException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

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
    public void add(Queue.Snapshot queue) throws ErrorOnPersistEntityException {
        try {
            var entity = QueueEntity.fromDomain(queue);
            jpaQueueRepository.save(entity);
        } catch (DataAccessException e) {
            throw new ErrorOnPersistEntityException(e.getMessage());
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


    @Override
    public void update(Queue.Snapshot queueSnapshot) throws ErrorOnPersistEntityException {
        try {
            QueueEntity queueEntity = jpaQueueRepository.findByIdWithTickets(queueSnapshot.id())
                    .orElseThrow(() -> new NotFoundEntityException("Queue not found for update: " + queueSnapshot.id()));

            queueEntity.update(queueSnapshot.lastTicketNumber());
            queueEntity.getTickets().clear();
            Stream.concat(
                    queueSnapshot.waitingTickets().stream(),
                    Stream.ofNullable(queueSnapshot.currentTicket())
            ).forEach(ticketSnapshot -> queueEntity.addTicket(TicketEntity.fromDomain(ticketSnapshot)));
            jpaQueueRepository.save(queueEntity);

        } catch (DataAccessException e) {
            throw new ErrorOnPersistEntityException("Error on updating queue: " + e.getMessage(), e);
        } catch (NoSuchElementException e) {
            throw new ErrorOnPersistEntityException("Cannot update a queue that does not exist.", e);
        }
    }


}
