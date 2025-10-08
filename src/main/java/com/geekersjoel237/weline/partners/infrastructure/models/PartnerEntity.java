package com.geekersjoel237.weline.partners.infrastructure.models;

import com.geekersjoel237.weline.partners.domain.entities.Partner;
import com.geekersjoel237.weline.shared.infrastructure.persistence.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/

@Entity
@Table(name = "partners")
@Getter
@NoArgsConstructor
public class PartnerEntity extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicePointEntity> servicePoints = new ArrayList<>();

    public PartnerEntity(String id, String name) {
        this.setId(id);
        this.name = name;
    }

    public static PartnerEntity fromDomain(Partner.Snapshot partner) {
        var entity = new PartnerEntity(partner.id(), partner.name());

        partner.servicePoints().stream()
                .map(ServicePointEntity::fromDomain)
                .forEach(entity::addServicePoint);

        return entity;

    }

    public void addServicePoint(ServicePointEntity servicePoint) {
        this.servicePoints.add(servicePoint);
        servicePoint.setPartner(this);
    }

    public void update(String name) {
        this.name = name;
    }
}
