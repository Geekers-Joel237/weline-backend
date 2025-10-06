package com.geekersjoel237.weline.queue.domain.entities;

import com.geekersjoel237.weline.queue.domain.vo.TicketCode;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
import com.geekersjoel237.weline.shared.domain.vo.Id;

import java.util.LinkedList;
import java.util.List;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/
public class Queue {
    public static final int INITIAL_TICKET_NUMBER = 0;
    private final Id id;
    private final Id serviceId;
    private List<Ticket> waitingTickets;
    private int lastTicketNumber;

    private Queue(Id id, Id serviceId, int lastTicketNumber) {
        this.id = id;
        this.serviceId = serviceId;
        this.lastTicketNumber = lastTicketNumber;
        waitingTickets = new LinkedList<>();
    }

    public static Queue create(Id id, Id serviceId) {
        return new Queue(
                id,
                serviceId,
                INITIAL_TICKET_NUMBER
        );
    }

    public static Queue createFromAdapter(Id queueId, Id serviceId, int lastTicketNumber, List<Ticket> tickets) {
        var queue = new Queue(queueId, serviceId, lastTicketNumber);
        queue.waitingTickets = tickets;
        return queue;
    }

    public Ticket takeTicket(Id customerId, String prefix) throws CustomIllegalArgumentException {
        checkIfCustomerHasTicket(customerId);
        ++lastTicketNumber;
        waitingTickets.add(
                Ticket.create(
                        customerId,
                        id,
                        TicketCode.of(prefix, lastTicketNumber)
                ));
        return waitingTickets.getLast();
    }

    private void checkIfCustomerHasTicket(Id customerId) {
        if (waitingTickets.stream().anyMatch(t -> t.snapshot().customerId().equals(customerId.value()))) {
            //TODO: manage same customer want another ticket
        }
    }

    public Snapshot snapshot() {
        return new Snapshot(
                id.value(),
                serviceId.value(),
                lastTicketNumber,
                waitingTickets.stream().map(Ticket::snapshot).toList()
        );
    }

    public Ticket currentTicket() {
        return waitingTickets.getLast();
    }

    public QueueStatus getStatusForTicket(Id ticketId) {
        String currentTicketNumber = (currentTicket() != null)
                ? currentTicket().snapshot().number()
                : "---";

        long peopleBeforeYou = waitingTickets.stream()
                .takeWhile(ticket -> !ticket.snapshot().id().equals(ticketId.value()))
                .count();

        return new QueueStatus(currentTicketNumber, peopleBeforeYou);
    }

    public record Snapshot(
            String id,
            String serviceId,
            int lastTicketNumber,
            List<Ticket.Snapshot> waitingTickets
    ) {
    }

    public record QueueStatus(String currentTicketNumber, long peopleBeforeYou) {
    }

}
