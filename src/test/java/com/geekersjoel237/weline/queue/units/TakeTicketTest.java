package com.geekersjoel237.weline.queue.units;

import com.geekersjoel237.weline.partners.domain.entities.Service;
import com.geekersjoel237.weline.partners.domain.repositories.ServiceRepository;
import com.geekersjoel237.weline.queue.appication.command.takeTicket.TakeTicketCommand;
import com.geekersjoel237.weline.queue.appication.command.takeTicket.TakeTicketHandler;
import com.geekersjoel237.weline.queue.appication.command.takeTicket.TakeTicketResponse;
import com.geekersjoel237.weline.queue.domain.entities.Queue;
import com.geekersjoel237.weline.queue.domain.repositories.QueueRepository;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
import com.geekersjoel237.weline.shared.domain.vo.Id;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/

@SpringBootTest
public class TakeTicketTest {

    private QueueRepository queueRepository;
    private ServiceRepository serviceRepository;

    @BeforeEach
    void setup() {
        queueRepository = new InMemoryQueueRepository();
        serviceRepository = new InMemoryServiceRepository();
    }

    @Test
    public void shouldTakeATicket() throws CustomIllegalArgumentException {
        var service = Service.create(
                Id.of("service-id-123"),
                Id.of("queue-id-456"),
                "Paiement Facture",
                "Payer vos factures d'électricité.",
                "PAY-F"
        );
        ((InMemoryServiceRepository) serviceRepository).services.put(service.snapshot().id(), service);

        var queue = Queue.create(
                Id.of(service.snapshot().queueId()),
                Id.of(service.snapshot().id())
        );
        ((InMemoryQueueRepository) queueRepository).queues.put(queue.snapshot().id(), queue.snapshot());

        var command = new TakeTicketCommand(
                "customer-id-123",
                "queue-id-456"
        );

        var res = takeTicket(command);

        assert res.isTook();
        assert res.ticketId() != null;
        assert res.ticketNumber().equals("PAY-F-001");

        var foundTicket = queueRepository.ofId(queue.snapshot().id()).get().currentTicket();
        assert res.ticketId().equals(foundTicket.snapshot().id());
    }

    @Test
    public void shouldTakeATicketOnANonEmptyQueue() throws CustomIllegalArgumentException {

        var service = Service.create(
                Id.of("service-id-123"),
                Id.of("queue-id-456"),
                "Paiement Facture",
                "Payer vos factures d'électricité.",
                "PAY-F"
        );
        ((InMemoryServiceRepository) serviceRepository).services.put(service.snapshot().id(), service);

        var queue = Queue.create(
                Id.of(service.snapshot().queueId()),
                Id.of(service.snapshot().id())
        );
        ((InMemoryQueueRepository) queueRepository).queues.put(queue.snapshot().id(), queue.snapshot());

        var command1 = new TakeTicketCommand(
                "customer-id-123",
                "queue-id-456"
        );
        takeTicket(command1);

        var command2 = new TakeTicketCommand(
                "customer-id-456",
                "queue-id-456"
        );
        var res = takeTicket(command2);

        assert res.isTook();
        assert res.ticketId() != null;
        assert res.ticketNumber().equals("PAY-F-002");

        var foundTicketOpt = queueRepository.ofId(queue.snapshot().id());
        assert foundTicketOpt.isPresent();
        var foundTicket = foundTicketOpt.get().currentTicket();
        assert res.ticketId().equals(foundTicket.snapshot().id());
        assert 2 == queueRepository.ofId(queue.snapshot().id()).get().snapshot().lastTicketNumber();
        assert 2 == queueRepository.ofId(queue.snapshot().id()).get().snapshot().tickets().size();
    }

    private TakeTicketResponse takeTicket(TakeTicketCommand command1) throws CustomIllegalArgumentException {
        var handler = new TakeTicketHandler(
                queueRepository,
                serviceRepository
        );

        return handler.handle(command1);
    }
}
