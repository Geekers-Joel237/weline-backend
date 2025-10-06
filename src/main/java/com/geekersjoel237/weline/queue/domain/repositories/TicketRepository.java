package com.geekersjoel237.weline.queue.domain.repositories;

import com.geekersjoel237.weline.queue.domain.entities.Ticket;

import java.util.Optional;

/**
 * Created on 06/10/2025
 *
 * @author Geekers_Joel237
 **/

public interface TicketRepository {
    Optional<Ticket> ofId(String ticketId);
}
