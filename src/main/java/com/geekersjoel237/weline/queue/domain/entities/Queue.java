package com.geekersjoel237.weline.queue.domain.entities;

import com.geekersjoel237.weline.queue.domain.vo.TicketCode;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
import com.geekersjoel237.weline.shared.domain.vo.Id;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
    private Ticket currentTicket;
    private int lastTicketNumber;

    private Queue(Id id, Id serviceId, int lastTicketNumber) {
        this.id = id;
        this.serviceId = serviceId;
        this.lastTicketNumber = lastTicketNumber;
        this.currentTicket = null;
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
        queue.currentTicket = tickets.stream()
                .filter(t -> Ticket.StatusEnum.CURRENT.name().equals(t.snapshot().status()))
                .findFirst().orElse(null);
        queue.waitingTickets = tickets.stream()
                .filter(t -> Ticket.StatusEnum.PENDING.name().equals(t.snapshot().status())).collect(Collectors.toList());
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
                waitingTickets.stream().map(Ticket::snapshot).toList(),
                currentTicket != null ? currentTicket.snapshot() : null
        );
    }

    public Ticket lastTicket() {
        return waitingTickets.getLast();
    }

    public QueueStatus getStatusForTicket(Id ticketId) {
        String lastDeliveredTicketNumber = !waitingTickets.isEmpty() ?
                waitingTickets.getLast().snapshot().number() : RAS_SYMBOL;

        long peopleBeforeYou = waitingTickets.stream()
                .takeWhile(ticket -> !ticket.snapshot().id().equals(ticketId.value()))
                .count();

        var currentTicketNumber = currentTicket() != null ?
                currentTicket().snapshot().number() :
                RAS_SYMBOL;

        return new QueueStatus(lastDeliveredTicketNumber, peopleBeforeYou, currentTicketNumber);
    }


    public Ticket currentTicket() {
        return this.currentTicket;
    }


    public CallNextResult callNextTicket() throws CustomIllegalArgumentException {
        Ticket previousTicket = this.currentTicket;

        if (previousTicket != null) {
            previousTicket.archive();
        }

        if (waitingTickets.isEmpty()) {
            this.currentTicket = null;
            return new CallNextResult(previousTicket, null);
        }

        this.currentTicket = this.waitingTickets.removeFirst();
        this.currentTicket.markCurrent();
        return new CallNextResult(previousTicket, this.currentTicket);
    }


    public record CallNextResult(Ticket previousTicket, Ticket nowServing) {
    }

    public record Snapshot(
            String id,
            String serviceId,
            int lastTicketNumber,
            List<Ticket.Snapshot> waitingTickets,
            Ticket.Snapshot currentTicket
    ) {
    }

    public record QueueStatus(String lastDeliveredTicketNumber, long peopleBeforeYou, String currentTicketNumber) {
    }

}
