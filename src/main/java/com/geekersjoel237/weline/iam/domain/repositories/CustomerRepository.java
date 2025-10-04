package com.geekersjoel237.weline.iam.domain.repositories;

import com.geekersjoel237.weline.iam.domain.entities.Customer;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
public interface CustomerRepository {
    void add(Customer.Snapshot customer);
}
