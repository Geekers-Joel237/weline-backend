package com.geekersjoel237.weline.shared.domain.vo;

import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
public class Id {
    private final String value;

    private Id(String value) {
        this.value = value;
    }

    public static Id of(String value) throws CustomIllegalArgumentException {
        if (value == null || value.isBlank()) {
            throw new CustomIllegalArgumentException("Id cannot be null");
        }

        return new Id(value.trim());
    }

    public static Id generate() {
        return new Id(java.util.UUID.randomUUID().toString());
    }

    public String value() {
        return value;
    }
}
