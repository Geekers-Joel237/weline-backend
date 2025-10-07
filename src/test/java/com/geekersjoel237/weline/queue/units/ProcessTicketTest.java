package com.geekersjoel237.weline.queue.units;

import com.geekersjoel237.weline.queue.appication.command.processTicket.ProcessTicketCommand;
import com.geekersjoel237.weline.queue.appication.command.processTicket.ProcessTicketHandler;
import com.geekersjoel237.weline.queue.domain.entities.Queue;
import com.geekersjoel237.weline.queue.domain.repositories.QueueRepository;
import com.geekersjoel237.weline.queue.domain.repositories.TicketRepository;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
import com.geekersjoel237.weline.shared.domain.vo.Id;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created on 07/10/2025
 *
 * @author Geekers_Joel237
 **/

@SpringBootTest
public class ProcessTicketTest {
    private QueueRepository queueRepository;
    private TicketRepository ticketRepository;

    @BeforeEach
    void setup() {
        queueRepository = new InMemoryQueueRepository();
        ticketRepository = new InMemoryTicketRepository();
    }

    @Test
    public void shouldProcessATicket() throws CustomIllegalArgumentException {
        var queue = Queue.create(
                Id.of("queue-id-123"),
                Id.of("service-id-001")
        );
        queueRepository.save(queue.snapshot());

        var ticket = queue.takeTicket(
                Id.of("customer-id-123"),
                "PAY-F"
        );
        ((InMemoryTicketRepository) ticketRepository).tickets.put(ticket.snapshot().id(), ticket);

        var command = new ProcessTicketCommand(
                ticket.snapshot().id()
        );

        var handler = new ProcessTicketHandler(queueRepository, ticketRepository);
        var res = handler.handle(command);

        assert res.isSuccess();
        assert queue.currentTicket().snapshot().id().equals(ticket.snapshot().id());
        assert queue.currentTicket().snapshot().number().equals(ticket.snapshot().number());
    }
}
