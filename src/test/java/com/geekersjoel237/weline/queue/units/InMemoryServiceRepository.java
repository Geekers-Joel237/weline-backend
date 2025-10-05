package com.geekersjoel237.weline.queue.units;

import com.geekersjoel237.weline.partners.domain.entities.Service;
import com.geekersjoel237.weline.partners.domain.repositories.ServiceRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/
public class InMemoryServiceRepository implements ServiceRepository {
    public final Map<String, Service> services = new HashMap<>();
    @Override
    public Optional<Service> ofId(String serviceId) {
        return Optional.ofNullable(services.get(serviceId));
    }
}
