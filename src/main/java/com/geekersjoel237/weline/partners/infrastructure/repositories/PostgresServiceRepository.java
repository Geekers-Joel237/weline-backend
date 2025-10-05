package com.geekersjoel237.weline.partners.infrastructure.repositories;

import com.geekersjoel237.weline.partners.domain.entities.Service;
import com.geekersjoel237.weline.partners.domain.repositories.ServiceRepository;
import com.geekersjoel237.weline.partners.infrastructure.models.ServiceEntity;
import com.geekersjoel237.weline.partners.infrastructure.persistence.JpaServiceRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/

@Repository
public class PostgresServiceRepository implements ServiceRepository {
    private final JpaServiceRepository jpaServiceRepository;

    public PostgresServiceRepository(JpaServiceRepository jpaServiceRepository) {
        this.jpaServiceRepository = jpaServiceRepository;
    }

    @Override
    public Optional<Service> ofId(String serviceId) {
        return jpaServiceRepository.findById(serviceId)
                .map(ServiceEntity::toDomain);
    }
}
