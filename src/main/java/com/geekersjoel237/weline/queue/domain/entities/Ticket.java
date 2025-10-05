package com.geekersjoel237.weline.queue.domain.entities;

import com.geekersjoel237.weline.queue.domain.vo.TicketCode;
import com.geekersjoel237.weline.shared.domain.vo.Id;

import java.time.Instant;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/
public class Ticket {
    private final Id id;
    private final Id queueId;
    private final Id customerId;
    private final TicketCode number;
    private final Instant createdAt;

    private Ticket(Id id, Id queueId, Id customerId, TicketCode number, Instant createdAt) {
        this.id = id;
        this.queueId = queueId;
        this.customerId = customerId;
        this.number = number;
        this.createdAt = createdAt;
    }

    public static Ticket create(Id customerId, Id queueId, TicketCode code) {
        return new Ticket(
                Id.generate(),
                queueId,
                customerId,
                code,
                Instant.now()
        );
    }

    public static Ticket createFromAdapter(
            Id ticketId, Id queueId, Id customerId, TicketCode ticketCode, Instant createdAt
    ) {
        return new Ticket(ticketId, queueId, customerId, ticketCode, createdAt);
    }

    public Snapshot snapshot() {
        return new Snapshot(
                id.value(),
                queueId.value(),
                customerId.value(),
                number.value(),
                createdAt
        );
    }

    public record Snapshot(
            String id,
            String queueId,
            String customerId,
            String number,
            Instant createdAt
    ) {
    }

}
