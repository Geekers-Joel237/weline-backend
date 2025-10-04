package com.geekersjoel237.weline.iam.domain.entities;

import com.geekersjoel237.weline.iam.domain.vo.PhoneNumber;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
import com.geekersjoel237.weline.shared.domain.vo.Id;

import java.util.Objects;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
public class Customer {
    private final Id id;
    private final PhoneNumber phoneNumber;

    public Customer(Id id, PhoneNumber phoneNumber) {
        this.id = id;
        this.phoneNumber = phoneNumber;
    }

    public static Customer create(String customerId, PhoneNumber phoneNumber) throws CustomIllegalArgumentException {
        var id = Objects.requireNonNull(customerId, "CustomerId should not be null");

        return new Customer(
                Id.of(id),
                phoneNumber
        );
    }

    public Snapshot snapshot() {
        return new Snapshot(
                id.value(),
                phoneNumber.value()
        );
    }

    public record Snapshot(
            String id,
            String phoneNumber
    ) {

    }
}
