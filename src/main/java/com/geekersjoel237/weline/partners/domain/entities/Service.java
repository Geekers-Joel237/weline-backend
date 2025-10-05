package com.geekersjoel237.weline.partners.domain.entities;

import com.geekersjoel237.weline.shared.domain.vo.Id;

import java.util.Objects;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/
public class Service {
    private final Id id;
    private final Id queueId;
    private final String name;
    private final String description;
    private final String code;

    public Service(Id serviceId, Id queueId, String name, String description, String code) {
        this.id = serviceId;
        this.queueId = queueId;
        this.name = Objects.requireNonNull(name, "Service name cannot be null");
        this.description = Objects.requireNonNull(description, "Service description cannot be null");
        this.code = Objects.requireNonNull(code, "Service code should not be null");
    }

    public static Service create(Id serviceId, Id queueId, String name, String description, String code) {
        return new Service(
                serviceId,
                queueId,
                name,
                description,
                code
        );
    }

    public Snapshot snapshot() {
        return new Snapshot(
                id.value(),
                queueId.value(),
                name,
                description,
                code
        );
    }

    public record Snapshot(
            String id,
            String queueId,
            String name,
            String description,
            String code
    ) {
    }
}
