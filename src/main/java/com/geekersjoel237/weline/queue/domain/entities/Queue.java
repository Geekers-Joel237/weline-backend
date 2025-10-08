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
    public static final String RAS_SYMBOL = "---";
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
        if (checkIfCustomerHasTicket(customerId)) {
            //TODO: manage same customer want another ticket
        }

        ++lastTicketNumber;
        waitingTickets.add(
                Ticket.create(
                        customerId,
                        id,
                        TicketCode.of(prefix, lastTicketNumber)
                ));
        return waitingTickets.getLast();
    }

    private boolean checkIfCustomerHasTicket(Id customerId) {
        return waitingTickets.stream().anyMatch(t -> t.snapshot().customerId().equals(customerId.value()));
    }

    public Snapshot snapshot() {
        return new Snapshot(
                id.value(),
                serviceId.value(),
                lastTicketNumber,
                waitingTickets.stream().map(Ticket::snapshot).toList()
        );
    }

    public Ticket lastTicket() {
        return waitingTickets.getLast();
    }

    public QueueStatus getStatusForTicket(Id ticketId) {
        String lastDeliveredTicketNumber = (lastTicket() != null)
                ? lastTicket().snapshot().number()
                : RAS_SYMBOL;

        long peopleBeforeYou = waitingTickets.stream()
                .takeWhile(ticket -> !ticket.snapshot().id().equals(ticketId.value()))
                .count();

        var currentTicketNumber = currentTicket() != null ?
                currentTicket().snapshot().number() :
                RAS_SYMBOL;

        return new QueueStatus(lastDeliveredTicketNumber, peopleBeforeYou, currentTicketNumber);
    }

    public Ticket currentTicket() {
        return waitingTickets.stream()
                .filter(t -> Ticket.StatusEnum.CURRENT.name().equals(t.snapshot().status()))
                .findFirst()
                .orElse(null);
    }


    public CallNextResult callNextTicket() throws CustomIllegalArgumentException {
        Ticket previousTicket = currentTicket();

        if (previousTicket != null) {
            previousTicket.archive();
        }

        if (this.waitingTickets.isEmpty()) {
            return new CallNextResult(previousTicket, null);
        }

        Ticket nowServing = this.waitingTickets.removeFirst();
        nowServing.markCurrent();

        return new CallNextResult(previousTicket, currentTicket());
    }


    public record CallNextResult(Ticket previousTicket, Ticket nowServing) {
    }

    public record Snapshot(
            String id,
            String serviceId,
            int lastTicketNumber,
            List<Ticket.Snapshot> waitingTickets
    ) {
    }

    public record QueueStatus(String lastDeliveredTicketNumber, long peopleBeforeYou, String currentTicketNumber) {
    }

}
