package com.geekersjoel237.weline.iam.units;

import com.geekersjoel237.weline.iam.domain.entities.CustomerOtp;
import com.geekersjoel237.weline.iam.domain.repositories.OtpRepository;
import com.geekersjoel237.weline.iam.domain.vo.Otp;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
import com.geekersjoel237.weline.shared.domain.exceptions.ErrorOnPersistEntityException;
import com.geekersjoel237.weline.shared.domain.vo.Id;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
public class InMemoryOtpRepository implements OtpRepository {
    private final Map<String, CustomerOtp.Snapshot> customerOtps = new HashMap<>();

    @Override
    public Optional<CustomerOtp> ofCustomerId(String customerId) {
        return this.customerOtps.values().stream()
                .filter(otp -> otp.customerId().equals(customerId))
                .findFirst()
                .map(snapshot -> {
                    try {
                        return new CustomerOtp(
                                Id.of(snapshot.id()),
                                Id.of(snapshot.customerId()),
                                new Otp(snapshot.otpCode(), Instant.parse(snapshot.expiredIn()))
                        );
                    } catch (CustomIllegalArgumentException e) {
                        throw new RuntimeException(e);
                    }
                });

    }

    @Override
    public void add(CustomerOtp.Snapshot customerOtp) throws ErrorOnPersistEntityException {
        this.customerOtps.put(customerOtp.id(), customerOtp);
    }

    @Override
    public void delete(String id) throws ErrorOnPersistEntityException {
        this.customerOtps.remove(id);
    }
}
