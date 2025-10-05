package com.geekersjoel237.weline.partners.infrastructure.persistence;

import com.geekersjoel237.weline.partners.infrastructure.models.PartnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/
public interface JpaPartnerRepository extends JpaRepository<PartnerEntity, String> {
}
