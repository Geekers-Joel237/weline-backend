package com.geekersjoel237.weline.partners.infrastructure.repositories;

import com.geekersjoel237.weline.partners.domain.entities.Partner;
import com.geekersjoel237.weline.partners.domain.repositories.PartnerRepository;
import com.geekersjoel237.weline.partners.infrastructure.models.PartnerEntity;
import com.geekersjoel237.weline.partners.infrastructure.persistence.JpaPartnerRepository;
import com.geekersjoel237.weline.shared.domain.exceptions.ErrorOnPersistEntityException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/

@Repository
public class PostgresPartnerRepository implements PartnerRepository {
    private final JpaPartnerRepository jpaPartnerRepository;

    public PostgresPartnerRepository(JpaPartnerRepository jpaPartnerRepository) {
        this.jpaPartnerRepository = jpaPartnerRepository;
    }

    @Override
    public void addMany(List<Partner.Snapshot> partner) throws ErrorOnPersistEntityException {
        try {
            var entities = partner.stream()
                    .map(PartnerEntity::fromDomain)
                    .toList();
            jpaPartnerRepository.saveAll(entities);
        } catch (DataAccessException e) {
            throw new ErrorOnPersistEntityException("Error on persist partners", e);
        }

    }

    @Override
    public void update(Partner.Snapshot partner) throws ErrorOnPersistEntityException {
        try {
            var entity = jpaPartnerRepository.findById(partner.id()).orElseThrow();
            entity.update(partner.name());
            jpaPartnerRepository.save(entity);
        } catch (DataAccessException e) {
            throw new ErrorOnPersistEntityException("Error on update partner", e);
        }
    }

    @Override
    public boolean exists() {
        return jpaPartnerRepository.count() > 0;
    }
}
