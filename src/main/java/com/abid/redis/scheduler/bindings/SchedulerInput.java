package com.abid.redis.scheduler.bindings;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface SchedulerInput {
    String INPUT = "scheduler-input";

    @Input(SchedulerInput.INPUT)
    SubscribableChannel input();
}
