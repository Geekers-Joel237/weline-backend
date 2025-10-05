package com.geekersjoel237.weline.queue.appication.command.takeTicket;

import com.geekersjoel237.weline.partners.domain.repositories.ServiceRepository;
import com.geekersjoel237.weline.queue.domain.repositories.QueueRepository;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
import com.geekersjoel237.weline.shared.domain.exceptions.NotFoundEntityException;
import com.geekersjoel237.weline.shared.domain.vo.Id;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/
public class TakeTicketHandler {
    private final QueueRepository queueRepository;
    private final ServiceRepository serviceRepository;

    public TakeTicketHandler(QueueRepository queueRepository, ServiceRepository serviceRepository) {
        this.queueRepository = queueRepository;
        this.serviceRepository = serviceRepository;
    }

    public TakeTicketResponse handle(TakeTicketCommand command) throws CustomIllegalArgumentException {
        var queue = queueRepository.ofId(command.queueId()).orElseThrow(
                () -> new NotFoundEntityException("Queue with id " + command.queueId() + " not found.")
        );

        var service = serviceRepository.ofId(queue.snapshot().serviceId()).orElseThrow(
                () -> new NotFoundEntityException("Service with id " + queue.snapshot().serviceId() + " not found.")
        );

        var ticket = queue.takeTicket(Id.of(command.customerId()), service.snapshot().code());
        return TakeTicketResponse.ofSuccess(ticket.snapshot().id(), ticket.snapshot().number());
    }
}
