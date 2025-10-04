package com.geekersjoel237.weline.iam.infrastructure.persistence;

import com.geekersjoel237.weline.iam.infrastructure.models.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, String> {
    Optional<CustomerEntity> findByPhoneNumber(String phoneNumber);
}
