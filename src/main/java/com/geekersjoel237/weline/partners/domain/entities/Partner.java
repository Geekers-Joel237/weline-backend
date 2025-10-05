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
public class Partner {
    private final Id id;
    private final String name;
    private final List<ServicePoint> servicePoints;

    private Partner(Id id, String name) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "Partner name cannot be null");
        this.servicePoints = new ArrayList<>();
    }

    public static Partner create(Id id, String name) {
        return new Partner(id, name);
    }

    public ServicePoint addServicePoint(Id servicePointId, String name, String location) { // <-- Change le retour de void à ServicePoint
        var servicePoint = ServicePoint.create(servicePointId, name, location);
        this.servicePoints.add(servicePoint);
        return servicePoint;
    }

    public Snapshot snapshot() {
        return new Snapshot(
                id.value(),
                name,
                servicePoints.stream()
                        .map(ServicePoint::snapshot)
                        .toList()
        );
    }

    public record Snapshot(
            String id,
            String name,
            List<ServicePoint.Snapshot> servicePoints) {
    }
}
