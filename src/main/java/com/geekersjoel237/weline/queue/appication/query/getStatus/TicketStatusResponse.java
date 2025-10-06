package com.geekersjoel237.weline.queue.appication.query.getStatus;

/**
 * Created on 06/10/2025
 *
 * @author Geekers_Joel237
 **/

public record TicketStatusResponse(
        String yourTicketNumber,
        String currentTicketNumber,
        long peopleBeforeYou,
        String serviceName
) {
}
