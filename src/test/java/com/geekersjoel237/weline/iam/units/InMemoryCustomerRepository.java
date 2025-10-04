package com.geekersjoel237.weline.iam.units;

import com.geekersjoel237.weline.iam.domain.entities.Customer;
import com.geekersjoel237.weline.iam.domain.repositories.CustomerRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
public class InMemoryCustomerRepository implements CustomerRepository {
    public Map<String, Customer.Snapshot> customers = new HashMap<>();

    @Override
    public void add(Customer.Snapshot customer) {
        this.customers.put(customer.id(), customer);
    }
}
