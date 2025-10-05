package com.geekersjoel237.weline.partners.infrastructure.models;

import com.geekersjoel237.weline.partners.domain.entities.Service;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
import com.geekersjoel237.weline.shared.domain.vo.Id;
import com.geekersjoel237.weline.shared.infrastructure.persistence.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/

@Entity
@Table(name = "services", indexes = @Index(columnList = "queue_id"))
@Getter
@NoArgsConstructor
public class ServiceEntity extends BaseEntity {
    @Column(nullable = false)
    private String queueId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String code;


    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_point_id")
    private ServicePointEntity servicePoint;

    public ServiceEntity(String id, String queueId, String name, String description, String code) {
        this.setId(id);
        this.queueId = queueId;
        this.name = name;
        this.description = description;
        this.code = code;
    }


    public static ServiceEntity fromDomain(Service.Snapshot service) {
        return new ServiceEntity(
                service.id(),
                service.queueId(),
                service.name(),
                service.description(),
                service.code()
        );
    }

    public Service toDomain() {
        try {
            return Service.create(
                    Id.of(getId()),
                    Id.of(queueId),
                    name,
                    description,
                    code
            );
        } catch (CustomIllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
}
