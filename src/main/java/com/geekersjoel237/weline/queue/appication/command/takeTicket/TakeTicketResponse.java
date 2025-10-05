package com.geekersjoel237.weline.queue.appication.command.takeTicket;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/
public record TakeTicketResponse(
        String ticketId,
        String ticketNumber,
        boolean isTook
) {
    public static TakeTicketResponse ofSuccess(String ticketId, String ticketNumber) {
        return new TakeTicketResponse(ticketId, ticketNumber, true);
    }


}
