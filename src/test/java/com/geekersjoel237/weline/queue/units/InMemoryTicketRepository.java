package com.geekersjoel237.weline.queue.units;

import com.geekersjoel237.weline.queue.domain.entities.Ticket;
import com.geekersjoel237.weline.queue.domain.repositories.TicketRepository;
import com.geekersjoel237.weline.queue.domain.vo.TicketCode;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
import com.geekersjoel237.weline.shared.domain.exceptions.ErrorOnPersistEntityException;
import com.geekersjoel237.weline.shared.domain.vo.Id;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created on 07/10/2025
 *
 * @author Geekers_Joel237
 **/
public class InMemoryTicketRepository implements TicketRepository {
    public Map<String, Ticket> tickets = new HashMap<>();
    @Override
    public Optional<Ticket> ofId(String ticketId) {
        return Optional.ofNullable(tickets.get(ticketId));
    }

    @Override
    public void update(Ticket.Snapshot ticket) throws ErrorOnPersistEntityException {
        tickets.computeIfPresent(ticket.id(), (s, existingTicket) -> {
            try {
                return Ticket.createFromAdapter(
                        Id.of(ticket.id()),
                        Id.of(ticket.queueId()),
                        Id.of(ticket.customerId()),
                        new TicketCode(ticket.number()),
                        ticket.createdAt(),
                        Ticket.StatusEnum.valueOf(ticket.status())
                );
            } catch (CustomIllegalArgumentException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
