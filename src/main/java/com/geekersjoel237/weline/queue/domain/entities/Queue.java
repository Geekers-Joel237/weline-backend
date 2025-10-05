package com.geekersjoel237.weline.queue.domain.entities;

import com.geekersjoel237.weline.queue.domain.vo.TicketCode;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
import com.geekersjoel237.weline.shared.domain.vo.Id;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/
public class Queue {
    public static final int INITIAL_TICKET_NUMBER = 0;
    private final Id id;
    private final Id serviceId;
    private int lastTicketNumber;

    private Queue(Id id, Id serviceId, int lastTicketNumber) {
        this.id = id;
        this.serviceId = serviceId;
        this.lastTicketNumber = lastTicketNumber;
    }

    public static Queue create(Id id, Id serviceId) {
        return new Queue(
                id,
                serviceId,
                INITIAL_TICKET_NUMBER
        );
    }

    public Ticket takeTicket(Id customerId, String prefix) throws CustomIllegalArgumentException {
        ++lastTicketNumber;
        return Ticket.create(
                customerId,
                id,
                TicketCode.of(prefix, lastTicketNumber)
        );
    }

    public Snapshot snapshot() {
        return new Snapshot(id.value(), serviceId.value());
    }

    public record Snapshot(
            String id,
            String serviceId
    ) {
    }
}
