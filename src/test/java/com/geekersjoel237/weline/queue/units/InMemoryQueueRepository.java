package com.geekersjoel237.weline.queue.units;

import com.geekersjoel237.weline.queue.domain.entities.Queue;
import com.geekersjoel237.weline.queue.domain.repositories.QueueRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/
public class InMemoryQueueRepository implements QueueRepository {
    public final Map<String, Queue> queues = new HashMap<>();

    @Override
    public Optional<Queue> ofId(String queueId) {
        return Optional.ofNullable(queues.get(queueId));
    }
}
