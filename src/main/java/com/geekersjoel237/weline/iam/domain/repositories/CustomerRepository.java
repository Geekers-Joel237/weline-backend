package com.geekersjoel237.weline.iam.domain.repositories;

import com.geekersjoel237.weline.iam.domain.entities.Customer;
import com.geekersjoel237.weline.shared.domain.exceptions.ErrorOnPersistEntityException;

import java.util.Optional;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
public interface CustomerRepository {
    void add(Customer.Snapshot customer) throws ErrorOnPersistEntityException;

    Optional<Customer> ofId(String customerId);

    Optional<Customer> ofPhoneNumber(String phoneNumber);

    void update(Customer.Snapshot customer) throws ErrorOnPersistEntityException;
}
