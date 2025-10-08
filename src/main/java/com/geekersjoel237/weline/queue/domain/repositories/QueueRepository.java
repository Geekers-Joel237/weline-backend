package com.geekersjoel237.weline.queue.domain.repositories;

import com.geekersjoel237.weline.queue.domain.entities.Queue;
import com.geekersjoel237.weline.shared.domain.exceptions.ErrorOnPersistEntityException;

import java.util.List;
import java.util.Optional;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/
public interface QueueRepository {
    Optional<Queue> ofId(String queueId);

    void add(Queue.Snapshot queue) throws ErrorOnPersistEntityException;

    void update(Queue.Snapshot queue) throws ErrorOnPersistEntityException;

    void addMany(List<Queue.Snapshot> queues) throws ErrorOnPersistEntityException;
}
