package com.geekersjoel237.weline.queue.units;

import com.geekersjoel237.weline.queue.domain.entities.Queue;
import com.geekersjoel237.weline.queue.domain.entities.Ticket;
import com.geekersjoel237.weline.queue.domain.repositories.QueueRepository;
import com.geekersjoel237.weline.queue.domain.vo.TicketCode;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
import com.geekersjoel237.weline.shared.domain.exceptions.ErrorOnPersistEntityException;
import com.geekersjoel237.weline.shared.domain.vo.Id;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/
public class InMemoryQueueRepository implements QueueRepository {
    public final Map<String, Queue.Snapshot> queues = new HashMap<>();

    private Queue toDomain(Queue.Snapshot snapshot) {
        try {

            return Queue.createFromAdapter(
                    Id.of(snapshot.id()),
                    Id.of(snapshot.serviceId()),
                    snapshot.lastTicketNumber(),
                    snapshot.waitingTickets().stream().map(t -> {
                        try {
                            return Ticket.createFromAdapter(
                                    Id.of(t.id()),
                                    Id.of(t.queueId()),
                                    Id.of(t.customerId()),
                                    new TicketCode(t.number()),
                                    t.createdAt(),
                                    Ticket.StatusEnum.valueOf(t.status())
                            );
                        } catch (CustomIllegalArgumentException e) {
                            throw new RuntimeException(e);
                        }
                    }).collect(Collectors.toList())
            );
        } catch (CustomIllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Optional<Queue> ofId(String queueId) {
        return Optional.ofNullable(queues.get(queueId)).map(this::toDomain);
    }

    @Override
    public void add(Queue.Snapshot queue) throws ErrorOnPersistEntityException {
        queues.put(queue.id(), queue);
    }

    @Override
    public void update(Queue.Snapshot queue) throws ErrorOnPersistEntityException {
        queues.put(queue.id(), queue);
    }

    @Override
    public void addMany(List<Queue.Snapshot> queues) throws ErrorOnPersistEntityException {

    }
}
