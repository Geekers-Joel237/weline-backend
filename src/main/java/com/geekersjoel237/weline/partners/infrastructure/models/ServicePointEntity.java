package com.geekersjoel237.weline.partners.infrastructure.models;

import com.geekersjoel237.weline.partners.domain.entities.ServicePoint;
import com.geekersjoel237.weline.shared.infrastructure.persistence.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/

@Entity
@Table(name = "service_points")
@Getter
@NoArgsConstructor
public class ServicePointEntity extends BaseEntity {
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    @Setter
    private PartnerEntity partner;

    @OneToMany(mappedBy = "servicePoint", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceEntity> offeredServices = new ArrayList<>();

    public ServicePointEntity(String id, String name, String location) {
        this.setId(id);
        this.name = name;
        this.location = location;
    }

    public static ServicePointEntity fromDomain(ServicePoint.Snapshot servicePoint) {
        var entity = new ServicePointEntity(
                servicePoint.id(),
                servicePoint.name(),
                servicePoint.location()
        );
        servicePoint.services().stream()
                .map(ServiceEntity::fromDomain)
                .forEach(entity::addService);

        return entity;
    }

    public void addService(ServiceEntity service) {
        this.offeredServices.add(service);
        service.setServicePoint(this);
    }
}
