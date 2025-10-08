package com.geekersjoel237.weline.queue.appication.command.callNextTicket;

/**
 * Created on 08/10/2025
 *
 * @author Geekers_Joel237
 **/
public record CallNextTicketResponse(
        String message,
        String ticketId,
        String ticketNumber,
        boolean isEmpty
) {
    public static CallNextTicketResponse ofFailure(String message) {
        return new CallNextTicketResponse(message, null, null, true);
    }

    public static CallNextTicketResponse ofSuccess(String ticketId, String ticketNumber) {
        return new CallNextTicketResponse("Next ticket called successfully.", ticketId, ticketNumber, false);
    }
}
