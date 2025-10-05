package com.geekersjoel237.weline.queue.infrastructure.models;

import com.geekersjoel237.weline.queue.domain.entities.Ticket;
import com.geekersjoel237.weline.queue.domain.vo.TicketCode;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
import com.geekersjoel237.weline.shared.domain.vo.Id;
import com.geekersjoel237.weline.shared.infrastructure.persistence.BaseEntity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/

@Entity
@Table(name = "tickets", indexes = {
        @jakarta.persistence.Index(columnList = "customer_id"),
        @jakarta.persistence.Index(columnList = "number", unique = true)
})
@NoArgsConstructor
public class TicketEntity extends BaseEntity {
    @Column(nullable = false)
    private String customerId;
    @Column(nullable = false)
    private String number;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "queue_id")
    private QueueEntity queue;


    public TicketEntity(String id, String customerId, String number, Instant createdAt) {
        this.setId(id);
        this.customerId = customerId;
        this.number = number;
        this.setCreatedAt(createdAt);
    }

    public static TicketEntity fromDomain(Ticket.Snapshot ticket) {
        return new TicketEntity(
                ticket.id(),
                ticket.customerId(),
                ticket.number(),
                ticket.createdAt()
        );
    }

    public Ticket toDomain() {
        try {
            return Ticket.createFromAdapter(
                    Id.of(getId()),
                    Id.of(queue.getId()),
                    Id.of(customerId),
                    new TicketCode(number),
                    getCreatedAt()
            );
        } catch (CustomIllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
}
