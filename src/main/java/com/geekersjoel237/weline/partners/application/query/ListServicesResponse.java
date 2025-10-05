package com.geekersjoel237.weline.partners.application.query;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/
public record ListServicesResponse(
        String id,
        String name,
        String description,
        String queueId,
        String partnerName,
        String partnerId
) {
}
