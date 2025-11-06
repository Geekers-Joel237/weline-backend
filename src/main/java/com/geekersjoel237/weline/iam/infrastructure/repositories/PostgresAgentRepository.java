package com.geekersjoel237.weline.iam.infrastructure.repositories;

import com.geekersjoel237.weline.iam.domain.entities.Agent;
import com.geekersjoel237.weline.iam.domain.repositories.AgentRepository;
import com.geekersjoel237.weline.iam.infrastructure.models.AgentEntity;
import com.geekersjoel237.weline.iam.infrastructure.persistence.JpaAgentRepository;
import com.geekersjoel237.weline.shared.domain.exceptions.ErrorOnPersistEntityException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created on 05/11/2025
 *
 * @author Geekers_Joel237
 **/

@Repository
public class PostgresAgentRepository implements AgentRepository {
    private final JpaAgentRepository jpaAgentRepository;

    public PostgresAgentRepository(JpaAgentRepository jpaAgentRepository) {
        this.jpaAgentRepository = jpaAgentRepository;
    }

    @Override
    public void save(Agent.Snapshot agent) throws ErrorOnPersistEntityException {
        try {
            jpaAgentRepository.save(AgentEntity.fromDomain(agent));
        } catch (DataAccessException e) {
            throw new ErrorOnPersistEntityException("Error on save agent", e);
        }
    }

    @Override
    public Optional<Agent> ofEmail(String email) {
        return jpaAgentRepository.findByEmail(email).map(AgentEntity::toDomain);
    }

    @Override
    public boolean exists() {
        return jpaAgentRepository.exists();
    }
}
