package com.geekersjoel237.weline.queue.domain.repositories;

import com.geekersjoel237.weline.queue.domain.entities.Queue;

import java.util.Optional;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/
public interface QueueRepository {
    Optional<Queue> ofId(String queueId);
}
