package com.geekersjoel237.weline.iam.infrastructure.persistence;

import com.geekersjoel237.weline.iam.infrastructure.models.AgentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Created on 05/11/2025
 *
 * @author Geekers_Joel237
 **/
public interface JpaAgentRepository extends JpaRepository<AgentEntity, String> {

    Optional<AgentEntity> findByEmail(String email);

    @Query("""
                    SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END FROM AgentEntity a
            
            """)
    boolean exists();
}
