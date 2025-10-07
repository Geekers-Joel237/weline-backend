package com.geekersjoel237.weline.queue.appication.command.getStatus;

/**
 * Created on 06/10/2025
 *
 * @author Geekers_Joel237
 **/

public record TicketStatusResponse(
        String yourTicketNumber,
        String lastDeliveredTicketNumber,
        long peopleBeforeYou,
        String serviceName
) {
    //TODO: introduce notion of current ticket , agent or service delivery point of view
}
