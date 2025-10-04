package com.geekersjoel237.weline.iam.infrastructure.repositories;

import com.geekersjoel237.weline.iam.domain.entities.Customer;
import com.geekersjoel237.weline.iam.domain.repositories.CustomerRepository;
import com.geekersjoel237.weline.iam.infrastructure.models.CustomerEntity;
import com.geekersjoel237.weline.iam.infrastructure.persistence.JpaCustomerRepository;
import com.geekersjoel237.weline.shared.domain.exceptions.ErrorOnPersistEntityException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/

@Repository
public class PostgresCustomerRepository implements CustomerRepository {
    private final JpaCustomerRepository jpaRepository;

    public PostgresCustomerRepository(JpaCustomerRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void add(Customer.Snapshot customer) throws ErrorOnPersistEntityException {
        try {
            CustomerEntity customerEntity = CustomerEntity.fromDomain(customer);
            jpaRepository.save(customerEntity);
        } catch (DataAccessException e) {
            throw new ErrorOnPersistEntityException("Failed to persist new customer", e);
        }
    }

    @Override
    public Optional<Customer> ofId(String customerId) {
        return jpaRepository.findById(customerId)
                .map(CustomerEntity::toDomain);
    }

    @Override
    public Optional<Customer> ofPhoneNumber(String phoneNumber) {
        return jpaRepository.findByPhoneNumber(phoneNumber)
                .map(CustomerEntity::toDomain);
    }

    @Override
    public void update(Customer.Snapshot customer) throws ErrorOnPersistEntityException {
        try {
            CustomerEntity customerEntity = jpaRepository.findById(customer.id()).orElseThrow();
            customerEntity.update(customer.status());
            jpaRepository.save(customerEntity);
        } catch (DataAccessException e) {
            throw new ErrorOnPersistEntityException("Failed to update customer with id: " + customer.id(), e);
        }
    }
}
