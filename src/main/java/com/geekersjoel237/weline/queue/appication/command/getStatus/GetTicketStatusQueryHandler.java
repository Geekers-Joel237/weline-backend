package com.geekersjoel237.weline.queue.appication.command.getStatus;

import com.geekersjoel237.weline.partners.domain.repositories.ServiceRepository;
import com.geekersjoel237.weline.queue.domain.repositories.QueueRepository;
import com.geekersjoel237.weline.queue.domain.repositories.TicketRepository;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
import com.geekersjoel237.weline.shared.domain.exceptions.NotFoundEntityException;
import com.geekersjoel237.weline.shared.domain.vo.Id;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created on 06/10/2025
 *
 * @author Geekers_Joel237
 **/

@Service
public class GetTicketStatusQueryHandler {

    private final TicketRepository ticketRepository;
    private final QueueRepository queueRepository;
    private final ServiceRepository serviceRepository;

    public GetTicketStatusQueryHandler(TicketRepository ticketRepository, QueueRepository queueRepository, ServiceRepository serviceRepository) {
        this.ticketRepository = ticketRepository;
        this.queueRepository = queueRepository;
        this.serviceRepository = serviceRepository;
    }

    @Transactional(readOnly = true)
    public TicketStatusResponse handle(String ticketId) throws CustomIllegalArgumentException {
        var ticket = ticketRepository.ofId(ticketId)
                .orElseThrow(() -> new NotFoundEntityException("Ticket not found"));

        var queue = queueRepository.ofId(ticket.snapshot().queueId())
                .orElseThrow(() -> new NotFoundEntityException("Queue not found"));

        var service = serviceRepository.ofId(queue.snapshot().serviceId())
                .orElseThrow(() -> new NotFoundEntityException("Service not found"));

        var queueStatus = queue.getStatusForTicket(Id.of(ticket.snapshot().id()));

        return new TicketStatusResponse(
                ticket.snapshot().number(),
                queueStatus.lastDeliveredTicketNumber(),
                queueStatus.currentTicketNumber(),
                queueStatus.peopleBeforeYou(),
                service.snapshot().name()
        );
    }
}
