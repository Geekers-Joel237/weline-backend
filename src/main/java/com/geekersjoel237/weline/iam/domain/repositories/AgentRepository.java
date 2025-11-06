package com.geekersjoel237.weline.iam.domain.repositories;

import com.geekersjoel237.weline.iam.domain.entities.Agent;
import com.geekersjoel237.weline.shared.domain.exceptions.ErrorOnPersistEntityException;

import java.util.Optional;

/**
 * Created on 05/11/2025
 *
 * @author Geekers_Joel237
 **/
public interface AgentRepository {
    void save(Agent.Snapshot snapshot) throws ErrorOnPersistEntityException;

    Optional<Agent> ofEmail(String email);

    boolean exists();
}
