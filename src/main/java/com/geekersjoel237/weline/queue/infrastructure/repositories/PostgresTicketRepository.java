package com.geekersjoel237.weline.queue.infrastructure.repositories;

import com.geekersjoel237.weline.queue.domain.entities.Ticket;
import com.geekersjoel237.weline.queue.domain.repositories.TicketRepository;
import com.geekersjoel237.weline.queue.infrastructure.models.TicketEntity;
import com.geekersjoel237.weline.queue.infrastructure.persistence.JpaTicketRepository;
import com.geekersjoel237.weline.shared.domain.exceptions.ErrorOnPersistEntityException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
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

    @Override
    public void update(Ticket.Snapshot ticket) throws ErrorOnPersistEntityException {
        try {
            var entity = jpaTicketRepository.findById(ticket.id()).orElseThrow();
            entity.update(ticket.status());
            jpaTicketRepository.save(entity);
        } catch (DataAccessException | NoSuchElementException e) {
            throw new ErrorOnPersistEntityException("Error on persist ticket with id " + ticket.id());
        }
    }

    @Override
    public void remove(Ticket.Snapshot ticket) throws ErrorOnPersistEntityException {
        try {
            update(ticket);
            jpaTicketRepository.deleteById(ticket.id());
        } catch (DataAccessException e) {
            throw new ErrorOnPersistEntityException("Error on deleting ticket with id " + ticket.id());
        }

    }
}
