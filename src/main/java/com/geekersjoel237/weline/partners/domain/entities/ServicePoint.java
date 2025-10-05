package com.geekersjoel237.weline.partners.domain.entities;

import com.geekersjoel237.weline.shared.domain.vo.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/
public class ServicePoint {
    private final Id id;
    private final String name;
    private final String location;
    private final List<Service> services;

    private ServicePoint(Id id, String name, String location) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "ServicePoint name cannot be null");
        this.location = Objects.requireNonNull(location, "ServicePoint location cannot be null");
        this.services = new ArrayList<>();
    }

    public static ServicePoint create(Id id, String name, String location) {
        return new ServicePoint(id, name, location);
    }

    public Service addService(Id serviceId, Id queueId, String name, String description) { // <-- Change le retour de void à Service
        var service = Service.create(serviceId, queueId, name, description);
        this.services.add(service);
        return service;
    }

    public Snapshot snapshot() {
        return new Snapshot(
                id.value(),
                name,
                location,
                services.stream()
                        .map(Service::snapshot)
                        .toList()
        );
    }

    public record Snapshot(
            String id,
            String name,
            String location,
            List<Service.Snapshot> services) {

    }
}
