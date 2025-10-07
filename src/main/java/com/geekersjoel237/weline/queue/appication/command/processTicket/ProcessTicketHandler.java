package com.geekersjoel237.weline.queue.appication.command.processTicket;

import com.geekersjoel237.weline.queue.domain.repositories.QueueRepository;
import com.geekersjoel237.weline.queue.domain.repositories.TicketRepository;
import com.geekersjoel237.weline.shared.domain.exceptions.NotFoundEntityException;

/**
 * Created on 07/10/2025
 *
 * @author Geekers_Joel237
 **/
public class ProcessTicketHandler {
    private final QueueRepository queueRepository;
    private final TicketRepository ticketRepository;

    public ProcessTicketHandler(QueueRepository queueRepository, TicketRepository ticketRepository) {
        this.queueRepository = queueRepository;
        this.ticketRepository = ticketRepository;
    }

    public ProcessTicketResponse handle(ProcessTicketCommand command) {
        var ticket = ticketRepository.ofId(command.ticketId()).orElseThrow(
                () -> new NotFoundEntityException("Ticket not found!")
        );

        var queue = queueRepository.ofId(ticket.snapshot().queueId()).orElseThrow(
                () -> new NotFoundEntityException("Queue not found!")
        );

        var resultSet = queue.process(ticket);


        if (!resultSet.isSuccess()) {
            return ProcessTicketResponse.ofFailure();
        }

        queueRepository.save(queue.snapshot());
        return ProcessTicketResponse.ofSuccess();
    }
}
