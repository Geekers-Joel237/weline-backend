package com.geekersjoel237.weline.queue.units;

import com.geekersjoel237.weline.queue.domain.entities.Ticket;
import com.geekersjoel237.weline.queue.domain.repositories.TicketRepository;

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
}
