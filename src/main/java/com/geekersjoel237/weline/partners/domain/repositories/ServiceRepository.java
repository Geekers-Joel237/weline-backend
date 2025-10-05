package com.geekersjoel237.weline.partners.domain.repositories;

import com.geekersjoel237.weline.partners.domain.entities.Service;

import java.util.Optional;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/
public interface ServiceRepository {
    Optional<Service> ofId(String serviceId);
}
