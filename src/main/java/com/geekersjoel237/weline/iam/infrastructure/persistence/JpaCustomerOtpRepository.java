package com.geekersjoel237.weline.iam.infrastructure.persistence;

import com.geekersjoel237.weline.iam.infrastructure.models.CustomerOtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
public interface JpaCustomerOtpRepository extends JpaRepository<CustomerOtpEntity, String> {
    Optional<CustomerOtpEntity> findByCustomerId(String customerId);
}
