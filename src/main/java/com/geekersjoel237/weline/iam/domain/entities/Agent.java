package com.geekersjoel237.weline.iam.domain.entities;

import com.geekersjoel237.weline.shared.domain.vo.Id;

/**
 * Created on 05/11/2025
 *
 * @author Geekers_Joel237
 **/
public class Agent {
    private final Id id;
    private final String email;
    private final String password;
    private final Id serviceId;
    private final Role role;

    private Agent(Id id, String email, String password, Id serviceId, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.serviceId = serviceId;
        this.role = role;
    }

    public static Agent create(String email, String passwordHash, Id serviceId) {
        return new Agent(
                Id.generate(),
                email,
                passwordHash,
                serviceId,
                Role.ROLE_AGENT
        );
    }

    public static Agent createFromDomain(
            Id agentId,
            Id serviceId,
            String email,
            String password,
            Role role
    ) {
        return new Agent(
                agentId,
                email,
                password,
                serviceId,
                role
        );
    }


    public Snapshot snapshot() {
        return new Snapshot(
                id.value(),
                email,
                password,
                serviceId.value(),
                role
        );
    }

    public enum Role {
        ROLE_AGENT,
        ROLE_ADMIN
    }

    public record Snapshot(
            String id,
            String email,
            String password,
            String serviceId,
            Role role
    ) {
    }
}
