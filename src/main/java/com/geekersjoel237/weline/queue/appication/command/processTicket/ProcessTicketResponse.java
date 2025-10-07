package com.geekersjoel237.weline.queue.appication.command.processTicket;

/**
 * Created on 07/10/2025
 *
 * @author Geekers_Joel237
 **/
public record ProcessTicketResponse(
        boolean isSuccess
) {
    public static ProcessTicketResponse ofSuccess() {
        return new ProcessTicketResponse(true);
    }

    public static ProcessTicketResponse ofFailure() {
        return new ProcessTicketResponse(false);
    }
}
