package com.geekersjoel237.weline.queue.appication.command.callNextTicket;

import com.geekersjoel237.weline.queue.domain.entities.Queue;
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
        Queue.CallNextResult result;
        try {
            result = queue.callNextTicket();
        } catch (CustomIllegalArgumentException e) {
            return CallNextTicketResponse.ofFailure(e.getMessage());
        }


        if (result.nowServing() == null) {
            return CallNextTicketResponse.ofFailure("No ticket to call !");
        }

        ticketRepository.update(result.previousTicket().snapshot());
        ticketRepository.update(result.nowServing().snapshot());
        queueRepository.update(queue.snapshot());

        return CallNextTicketResponse.ofSuccess(result.nowServing().snapshot().id(), result.nowServing().snapshot().number());
    }
}
