package com.geekersjoel237.weline.queue.infrastructure.repositories;

import com.geekersjoel237.weline.queue.domain.entities.Ticket;
import com.geekersjoel237.weline.queue.domain.repositories.TicketRepository;
import com.geekersjoel237.weline.queue.infrastructure.models.TicketEntity;
import com.geekersjoel237.weline.queue.infrastructure.persistence.JpaTicketRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created on 06/10/2025
 *
 * @author Geekers_Joel237
 **/

@Repository
public class PostgresTicketRepository implements TicketRepository {
    private final JpaTicketRepository jpaTicketRepository;

    public PostgresTicketRepository(JpaTicketRepository jpaTicketRepository) {
        this.jpaTicketRepository = jpaTicketRepository;
    }

    @Override
    public Optional<Ticket> ofId(String ticketId) {
        return jpaTicketRepository.findById(ticketId)
                .map(TicketEntity::toDomain);

    }
}
