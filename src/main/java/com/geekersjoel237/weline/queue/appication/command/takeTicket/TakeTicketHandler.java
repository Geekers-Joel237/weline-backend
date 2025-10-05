package com.geekersjoel237.weline.queue.appication.command.takeTicket;

import com.geekersjoel237.weline.partners.domain.repositories.ServiceRepository;
import com.geekersjoel237.weline.queue.domain.entities.Ticket;
import com.geekersjoel237.weline.queue.domain.repositories.QueueRepository;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
import com.geekersjoel237.weline.shared.domain.exceptions.NotFoundEntityException;
import com.geekersjoel237.weline.shared.domain.vo.Id;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/

@Service
public class TakeTicketHandler {
    private final QueueRepository queueRepository;
    private final ServiceRepository serviceRepository;

    public TakeTicketHandler(
            QueueRepository queueRepository,
            ServiceRepository serviceRepository
    ) {
        this.queueRepository = queueRepository;
        this.serviceRepository = serviceRepository;
    }

    @Transactional
    public TakeTicketResponse handle(TakeTicketCommand command) {
        var queue = queueRepository.ofId(command.queueId()).orElseThrow(
                () -> new NotFoundEntityException("Queue with id " + command.queueId() + " not found.")
        );

        var service = serviceRepository.ofId(queue.snapshot().serviceId()).orElseThrow(
                () -> new NotFoundEntityException("Service with id " + queue.snapshot().serviceId() + " not found.")
        );

        Ticket ticket;
        try {
            ticket = queue.takeTicket(Id.of(command.customerId()), service.snapshot().code());
        } catch (CustomIllegalArgumentException e) {
            return TakeTicketResponse.ofFailure(e.getMessage());
        }
        queueRepository.save(queue.snapshot());

        return TakeTicketResponse.ofSuccess(ticket.snapshot().id(), ticket.snapshot().number());
    }
}
