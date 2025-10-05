package com.geekersjoel237.weline.partners.domain.repositories;

import com.geekersjoel237.weline.partners.domain.entities.Partner;
import com.geekersjoel237.weline.shared.domain.exceptions.ErrorOnPersistEntityException;

import java.util.List;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/
public interface PartnerRepository {
    void addMany(List<Partner.Snapshot> partner) throws ErrorOnPersistEntityException;
    void update(Partner.Snapshot partner) throws ErrorOnPersistEntityException;
    boolean exists();
}
