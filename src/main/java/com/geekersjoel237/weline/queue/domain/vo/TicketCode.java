package com.geekersjoel237.weline.queue.domain.vo;

import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/
public record TicketCode(String value) {

    public static TicketCode of(String prefix, int lastTicketNumber) throws CustomIllegalArgumentException {
        if (prefix == null || prefix.isBlank()) {
            throw new CustomIllegalArgumentException("Prefix code cannot be null or empty");
        }
        String code = String.format("%s-%03d", prefix, lastTicketNumber);
        return new TicketCode(code);
    }
}
