package com.geekersjoel237.weline.queue.appication.command.getStatus;

/**
 * Created on 06/10/2025
 *
 * @author Geekers_Joel237
 **/

public record TicketStatusResponse(
        String yourTicketNumber,
        String lastDeliveredTicketNumber,
        String currentTicketNumber,
        long peopleBeforeYou,
        String serviceName
) {
}
