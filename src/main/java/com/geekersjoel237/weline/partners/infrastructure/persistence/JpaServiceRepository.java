package com.geekersjoel237.weline.partners.infrastructure.persistence;

import com.geekersjoel237.weline.partners.infrastructure.models.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/
public interface JpaServiceRepository extends JpaRepository<ServiceEntity, String> {
}
