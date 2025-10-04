package com.geekersjoel237.weline.iam.domain.entities;

import com.geekersjoel237.weline.iam.domain.enums.CustomerStatusEnum;
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
    private CustomerStatusEnum status;

    private Customer(Id id, PhoneNumber phoneNumber, CustomerStatusEnum status) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public static Customer create(String customerId, PhoneNumber phoneNumber) throws CustomIllegalArgumentException {
        var id = Objects.requireNonNull(customerId, "CustomerId should not be null");

        return new Customer(
                Id.of(id),
                phoneNumber,
                CustomerStatusEnum.PENDING_VERIFICATION
        );
    }

    public static Customer createFromAdapter(Id id, PhoneNumber phoneNumber, CustomerStatusEnum status) {
        return new Customer(id, phoneNumber, status);
    }

    public Snapshot snapshot() {
        return new Snapshot(
                id.value(),
                phoneNumber.value(),
                status.name()
        );
    }

    public void verified() throws CustomIllegalArgumentException {
        if (this.status != CustomerStatusEnum.PENDING_VERIFICATION) {
            throw new CustomIllegalArgumentException("Only a customer with PENDING_VERIFICATION status can be activated.");
        }
        this.status = CustomerStatusEnum.VERIFIED;
    }

    public record Snapshot(
            String id,
            String phoneNumber,
            String status
    ) {
    }


}
