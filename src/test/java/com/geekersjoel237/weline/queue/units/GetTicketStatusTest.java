package com.geekersjoel237.weline.queue.units;

import com.geekersjoel237.weline.partners.domain.entities.Service;
import com.geekersjoel237.weline.partners.domain.repositories.ServiceRepository;
import com.geekersjoel237.weline.queue.appication.command.getStatus.GetTicketStatusQueryHandler;
import com.geekersjoel237.weline.queue.domain.entities.Queue;
import com.geekersjoel237.weline.queue.domain.repositories.QueueRepository;
import com.geekersjoel237.weline.queue.domain.repositories.TicketRepository;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
import com.geekersjoel237.weline.shared.domain.vo.Id;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

/**
 * Created on 07/10/2025
 *
 * @author Geekers_Joel237
 **/

@SpringBootTest
public class GetTicketStatusTest {
    private TicketRepository ticketRepository;
    private QueueRepository queueRepository;
    private ServiceRepository serviceRepository;
    private GetTicketStatusQueryHandler handler;

    @BeforeEach
    void setup() {
        ticketRepository = new InMemoryTicketRepository();
        queueRepository = new InMemoryQueueRepository();
        serviceRepository = new InMemoryServiceRepository();
        handler = new GetTicketStatusQueryHandler(ticketRepository, queueRepository, serviceRepository);
    }

    @Test
    public void shouldGetTicketStatusOnEmptyQueue() throws CustomIllegalArgumentException {
        var service = Service.create(
                Id.of("service-id-001"),
                Id.of("queue-id-123"),
                "Paiement Factures Eneo",
                "Paiement Facture Eneo",
                "PAY-F"
        );
        ((InMemoryServiceRepository) serviceRepository).services.put(service.snapshot().id(), service);
        var queue = Queue.create(
                Id.of(service.snapshot().queueId()),
                Id.of(service.snapshot().id())
        );
        queueRepository.save(queue.snapshot());
        var ticket = queue.takeTicket(
                Id.of("customer-id-123"),
                service.snapshot().code()
        );
        ((InMemoryTicketRepository) ticketRepository).tickets.put(ticket.snapshot().id(), ticket);
        queueRepository.save(queue.snapshot());

        var res = handler.handle(ticket.snapshot().id());

        assert Objects.equals(ticket.snapshot().number(), res.lastDeliveredTicketNumber());
        assert ticket.snapshot().number().equals(res.yourTicketNumber());
        assert 0 == res.peopleBeforeYou();
        assert service.snapshot().name().equals(res.serviceName());

    }

    @Test
    public void shouldGetTicketStatusOnNotEmptyQueue() throws CustomIllegalArgumentException {
        var service = Service.create(
                Id.of("service-id-001"),
                Id.of("queue-id-123"),
                "Paiement Factures Eneo",
                "Paiement Facture Eneo",
                "PAY-F"
        );
        ((InMemoryServiceRepository) serviceRepository).services.put(service.snapshot().id(), service);
        var queue = Queue.create(
                Id.of(service.snapshot().queueId()),
                Id.of(service.snapshot().id())
        );
        queueRepository.save(queue.snapshot());
        var ticket1 = queue.takeTicket(
                Id.of("customer-id-123"),
                service.snapshot().code()
        );
        ((InMemoryTicketRepository) ticketRepository).tickets.put(ticket1.snapshot().id(), ticket1);
        queueRepository.save(queue.snapshot());

        var ticket2 = queue.takeTicket(
                Id.of("customer-id-459"),
                service.snapshot().code()
        );
        ((InMemoryTicketRepository) ticketRepository).tickets.put(ticket2.snapshot().id(), ticket2);
        queueRepository.save(queue.snapshot());

        var res = handler.handle(ticket2.snapshot().id());

        assert Objects.equals(ticket2.snapshot().number(), res.lastDeliveredTicketNumber());
        assert !ticket1.snapshot().number().equals(res.yourTicketNumber());
        assert 1 == res.peopleBeforeYou();
        assert service.snapshot().name().equals(res.serviceName());

    }

    @Test
    public void shouldGetTicketStatusOnNotEmptyQueueWithFiveExistingTickets() throws CustomIllegalArgumentException {
        var service = Service.create(
                Id.of("service-id-001"),
                Id.of("queue-id-123"),
                "Paiement Factures Eneo",
                "Paiement Facture Eneo",
                "PAY-F"
        );
        ((InMemoryServiceRepository) serviceRepository).services.put(service.snapshot().id(), service);
        var queue = Queue.create(
                Id.of(service.snapshot().queueId()),
                Id.of(service.snapshot().id())
        );
        queueRepository.save(queue.snapshot());
        var ticket1 = queue.takeTicket(
                Id.of("customer-id-123"),
                service.snapshot().code()
        );
        ((InMemoryTicketRepository) ticketRepository).tickets.put(ticket1.snapshot().id(), ticket1);
        queueRepository.save(queue.snapshot());

        var ticket2 = queue.takeTicket(
                Id.of("customer-id-459"),
                service.snapshot().code()
        );
        ((InMemoryTicketRepository) ticketRepository).tickets.put(ticket2.snapshot().id(), ticket2);
        queueRepository.save(queue.snapshot());

        var ticket3 = queue.takeTicket(
                Id.of("customer-id-789"),
                service.snapshot().code()
        );
        ((InMemoryTicketRepository) ticketRepository).tickets.put(ticket3.snapshot().id(), ticket3);
        queueRepository.save(queue.snapshot());

        var ticket4 = queue.takeTicket(
                Id.of("customer-id-243"),
                service.snapshot().code()
        );
        ((InMemoryTicketRepository) ticketRepository).tickets.put(ticket4.snapshot().id(), ticket4);
        queueRepository.save(queue.snapshot());

        var ticket5 = queue.takeTicket(
                Id.of("customer-id-584"),
                service.snapshot().code()
        );
        ((InMemoryTicketRepository) ticketRepository).tickets.put(ticket5.snapshot().id(), ticket5);
        queueRepository.save(queue.snapshot());


        var res1 = handler.handle(ticket5.snapshot().id());

        assert Objects.equals(ticket5.snapshot().number(), res1.lastDeliveredTicketNumber());
        assert !ticket1.snapshot().number().equals(res1.yourTicketNumber());
        assert 4 == res1.peopleBeforeYou();

        var res2 = handler.handle(ticket3.snapshot().id());

        assert Objects.equals(ticket5.snapshot().number(), res2.lastDeliveredTicketNumber());
        assert ticket3.snapshot().number().equals(res2.yourTicketNumber());
        assert 2 == res2.peopleBeforeYou();

    }
}
