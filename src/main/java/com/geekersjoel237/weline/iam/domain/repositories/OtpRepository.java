package com.geekersjoel237.weline.iam.domain.repositories;

import com.geekersjoel237.weline.iam.domain.entities.CustomerOtp;
import com.geekersjoel237.weline.shared.domain.exceptions.ErrorOnPersistEntityException;

import java.util.Optional;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
public interface OtpRepository {
    Optional<CustomerOtp> ofCustomerId(String customerId);

    void add(CustomerOtp.Snapshot customerOtp) throws ErrorOnPersistEntityException;

    void delete(String id) throws ErrorOnPersistEntityException;

    void update(CustomerOtp.Snapshot customerOtp) throws ErrorOnPersistEntityException;
}
