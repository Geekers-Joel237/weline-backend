package com.geekersjoel237.weline.iam.infrastructure.repositories;

import com.geekersjoel237.weline.iam.domain.entities.CustomerOtp;
import com.geekersjoel237.weline.iam.domain.repositories.OtpRepository;
import com.geekersjoel237.weline.iam.infrastructure.models.CustomerOtpEntity;
import com.geekersjoel237.weline.iam.infrastructure.persistence.JpaCustomerOtpRepository;
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
public class PostgresCustomerOtpRepository implements OtpRepository {
    private final JpaCustomerOtpRepository jpaRepository;

    public PostgresCustomerOtpRepository(JpaCustomerOtpRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<CustomerOtp> ofCustomerId(String customerId) {
        return jpaRepository.findByCustomerId(customerId)
                .map(CustomerOtpEntity::toDomain);
    }

    @Override
    public void add(CustomerOtp.Snapshot customerOtp) throws ErrorOnPersistEntityException {
        try {
            CustomerOtpEntity entity = CustomerOtpEntity.fromDomain(customerOtp);
            jpaRepository.save(entity);
        } catch (DataAccessException e) {
            throw new ErrorOnPersistEntityException("Failed to persist new customer OTP", e);
        }
    }

    @Override
    public void delete(String id) throws ErrorOnPersistEntityException {
        try {
            jpaRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new ErrorOnPersistEntityException("Failed to soft-delete customer OTP with id: " + id, e);
        }
    }
}
