package com.geekersjoel237.weline.iam.infrastructure.models;

import com.geekersjoel237.weline.iam.domain.entities.Agent;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
import com.geekersjoel237.weline.shared.domain.vo.Id;
import com.geekersjoel237.weline.shared.infrastructure.persistence.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

/**
 * Created on 05/11/2025
 *
 * @author Geekers_Joel237
 **/

@Entity
@Table(name = "agents", indexes = {
        @Index(columnList = "service_id")
})
public class AgentEntity extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String serviceId;
    @Column(nullable = false)
    private String role;

    public AgentEntity() {
    }

    public AgentEntity(
            String id,
            String email,
            String password,
            String serviceId,
            String role
    ) {
        this.setId(id);
        this.email = email;
        this.password = password;
        this.serviceId = serviceId;
        this.role = role;
    }

    public static AgentEntity fromDomain(Agent.Snapshot agent) {
        return new AgentEntity(
                agent.id(),
                agent.email(),
                agent.password(),
                agent.serviceId(),
                agent.role().name()
        );
    }

    public Agent toDomain() {
        try {
            return Agent.createFromDomain(
                    Id.of(getId()),
                    Id.of(serviceId),
                    email,
                    password,
                    Agent.Role.valueOf(role)
            );
        } catch (CustomIllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

}
