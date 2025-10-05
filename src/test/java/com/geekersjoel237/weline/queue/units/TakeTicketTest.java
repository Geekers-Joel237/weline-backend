package com.geekersjoel237.weline.queue.units;

import com.geekersjoel237.weline.partners.domain.entities.Service;
import com.geekersjoel237.weline.partners.domain.repositories.ServiceRepository;
import com.geekersjoel237.weline.queue.appication.command.takeTicket.TakeTicketCommand;
import com.geekersjoel237.weline.queue.appication.command.takeTicket.TakeTicketHandler;
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
        ((InMemoryQueueRepository) queueRepository).queues.put(queue.snapshot().id(), queue);

        var command = new TakeTicketCommand(
                "customer-id-123",
                "queue-id-456"
        );

        var handler = new TakeTicketHandler(
                queueRepository,
                serviceRepository
        );

        var res = handler.handle(command);

        assert res.isTook();
        assert res.ticketId() != null;
        assert res.ticketNumber().equals("PAY-F-001");
    }
}
