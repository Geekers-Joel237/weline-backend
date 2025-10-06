package com.geekersjoel237.weline.queue.infrastructure.persistence;

import com.geekersjoel237.weline.queue.infrastructure.models.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created on 06/10/2025
 *
 * @author Geekers_Joel237
 **/
public interface JpaTicketRepository extends JpaRepository<TicketEntity, String>
 {
}
