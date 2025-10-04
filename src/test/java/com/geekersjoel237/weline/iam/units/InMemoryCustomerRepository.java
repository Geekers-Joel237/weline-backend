package com.geekersjoel237.weline.iam.units;

import com.geekersjoel237.weline.iam.domain.entities.Customer;
import com.geekersjoel237.weline.iam.domain.enums.CustomerStatusEnum;
import com.geekersjoel237.weline.iam.domain.repositories.CustomerRepository;
import com.geekersjoel237.weline.iam.domain.vo.PhoneNumber;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
import com.geekersjoel237.weline.shared.domain.exceptions.ErrorOnPersistEntityException;
import com.geekersjoel237.weline.shared.domain.vo.Id;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    @Override
    public Optional<Customer> ofId(String customerId) {
        return Optional.ofNullable(this.customers.get(customerId)).map(this::toDomain);
    }

    @Override
    public Optional<Customer> ofPhoneNumber(String phoneNumber) {
        return this.customers.values().stream()
                .filter(c -> c.phoneNumber().equals(phoneNumber))
                .findFirst()
                .map(this::toDomain);
    }

    @Override
    public void update(Customer.Snapshot customer) throws ErrorOnPersistEntityException {
        this.customers.put(customer.id(), customer);
    }

    private Customer toDomain(Customer.Snapshot snapshot) {
        try {
            return  Customer.createFromAdapter(Id.of(snapshot.id()), new PhoneNumber(snapshot.phoneNumber()), CustomerStatusEnum.valueOf(snapshot.status()));
        } catch (CustomIllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
}
