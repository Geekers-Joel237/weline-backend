package com.geekersjoel237.weline.queue.domain.entities;

import com.geekersjoel237.weline.queue.domain.vo.TicketCode;
import com.geekersjoel237.weline.shared.domain.vo.Id;

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

    private Ticket(Id id, Id queueId, Id customerId, TicketCode number) {
        this.id = id;
        this.queueId = queueId;
        this.customerId = customerId;
        this.number = number;
    }

    public static Ticket create(Id customerId, Id queueId, TicketCode code) {
        return new Ticket(
                Id.generate(),
                queueId,
                customerId,
                code
        );
    }

    public Snapshot snapshot() {
        return new Snapshot(
                id.value(),
                queueId.value(),
                customerId.value(),
                number.value()
        );
    }

    public record Snapshot(
            String id,
            String queueId,
            String customerId,
            String number
    ) {
    }

}
