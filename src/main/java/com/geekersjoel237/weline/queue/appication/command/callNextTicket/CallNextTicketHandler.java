package com.geekersjoel237.weline.queue.appication.command.callNextTicket;

import com.geekersjoel237.weline.queue.domain.entities.Ticket;
import com.geekersjoel237.weline.queue.domain.repositories.QueueRepository;
import com.geekersjoel237.weline.queue.domain.repositories.TicketRepository;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
import com.geekersjoel237.weline.shared.domain.exceptions.NotFoundEntityException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created on 08/10/2025
 *
 * @author Geekers_Joel237
 **/

@Service
public class CallNextTicketHandler {

    private final QueueRepository queueRepository;
    private final TicketRepository ticketRepository;

    public CallNextTicketHandler(QueueRepository queueRepository, TicketRepository ticketRepository) {
        this.queueRepository = queueRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public CallNextTicketResponse handle(CallNextTicketCommand command) {
        var queue = queueRepository.ofId(command.queueId())
                .orElseThrow(() -> new NotFoundEntityException("Queue with id " + command.queueId() + " not found."));

        Ticket ticket;
        try {
            ticket = queue.callNextTicket();
        } catch (CustomIllegalArgumentException e) {
            return CallNextTicketResponse.ofFailure(e.getMessage());
        }

        queueRepository.update(queue.snapshot());

        if (ticket == null) {
            return CallNextTicketResponse.ofFailure("No ticket to call !");
        }

        ticketRepository.update(ticket.snapshot());
        return CallNextTicketResponse.ofSuccess(ticket.snapshot().id(), ticket.snapshot().number());
    }
}
