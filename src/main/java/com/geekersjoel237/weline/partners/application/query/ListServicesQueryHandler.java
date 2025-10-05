package com.geekersjoel237.weline.partners.application.query;

import com.geekersjoel237.weline.partners.infrastructure.persistence.JpaServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/

@Service
public class ListServicesQueryHandler {
    private final JpaServiceRepository serviceRepository;

    public ListServicesQueryHandler(JpaServiceRepository jpaServiceRepository) {
        this.serviceRepository = jpaServiceRepository;
    }

    @Transactional(readOnly = true)
    public List<ListServicesResponse> handle() {
        return serviceRepository.findAll().stream()
                .map(entity -> new ListServicesResponse(
                        entity.getId(),
                        entity.getName(),
                        entity.getDescription(),
                        entity.getQueueId(),
                        entity.getServicePoint().getPartner().getName(),
                        entity.getServicePoint().getPartner().getId()
                ))
                .collect(Collectors.toList());

    }
}
