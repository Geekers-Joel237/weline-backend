package com.geekersjoel237.weline.queue.infrastructure.models;

import com.geekersjoel237.weline.queue.domain.entities.Queue;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
import com.geekersjoel237.weline.shared.domain.vo.Id;
import com.geekersjoel237.weline.shared.infrastructure.persistence.BaseEntity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/

@Entity
@Table(name = "queues", indexes = @jakarta.persistence.Index(columnList = "service_id"))
@NoArgsConstructor
public class QueueEntity extends BaseEntity {
    @Column(nullable = false)
    private String serviceId;
    @Column(nullable = false)
    private int lastTicketNumber;

    @OneToMany(mappedBy = "queue", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TicketEntity> tickets = new ArrayList<>();

    public QueueEntity(String id, String serviceId, int lastTicketNumber) {
        this.setId(id);
        this.serviceId = serviceId;
        this.lastTicketNumber = lastTicketNumber;
    }

    public static QueueEntity fromDomain(Queue.Snapshot queue) {
        var entity = new QueueEntity(
                queue.id(),
                queue.serviceId(),
                queue.lastTicketNumber()
        );
        queue.tickets().forEach(ticket -> entity.addWaitingTicket(TicketEntity.fromDomain(ticket)));
        return entity;
    }


    public void addWaitingTicket(TicketEntity ticket) {
        this.tickets.add(ticket);
        ticket.setQueue(this);
    }


    public Queue toDomain() {
        try {
            return Queue.createFromAdapter(
                    Id.of(getId()),
                    Id.of(serviceId),
                    lastTicketNumber,
                    tickets.stream()
                            .map(TicketEntity::toDomain)
                            .collect(Collectors.toList())
            );
        } catch (CustomIllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
}
