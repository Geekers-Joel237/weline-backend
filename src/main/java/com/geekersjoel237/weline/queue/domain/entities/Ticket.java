package com.geekersjoel237.weline.queue.domain.entities;

import com.geekersjoel237.weline.queue.domain.vo.TicketCode;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
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
    private final StatusEnum status;

    private Ticket(Id id, Id queueId, Id customerId, TicketCode number, Instant createdAt, StatusEnum status) {
        this.id = id;
        this.queueId = queueId;
        this.customerId = customerId;
        this.number = number;
        this.createdAt = createdAt;
        this.status = status;
    }

    public static Ticket create(Id customerId, Id queueId, TicketCode code) {
        return new Ticket(
                Id.generate(),
                queueId,
                customerId,
                code,
                Instant.now(),
                StatusEnum.PENDING
        );
    }

    public static Ticket createFromAdapter(
            Id ticketId, Id queueId, Id customerId, TicketCode ticketCode, Instant createdAt, StatusEnum status
    ) {
        return new Ticket(ticketId, queueId, customerId, ticketCode, createdAt, status);
    }

    public Snapshot snapshot() {
        return new Snapshot(
                id.value(),
                queueId.value(),
                customerId.value(),
                number.value(),
                createdAt,
                status.name()
        );
    }

    public enum StatusEnum {
        PENDING,
        CURRENT,
        ARCHIVED,
        CANCELLED;

        StatusEnum fromValue(String value) throws CustomIllegalArgumentException {
            for (StatusEnum statusEnum : StatusEnum.values()) {
                if (statusEnum.name().equalsIgnoreCase(value)) {
                    return statusEnum;
                }
            }
            throw new CustomIllegalArgumentException("Unknow enum value");
        }
    }

    public record Snapshot(
            String id,
            String queueId,
            String customerId,
            String number,
            Instant createdAt,
            String status
    ) {
    }
}
