package com.abid.redis.scheduler.bindings;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface SchedulerOutput {
    String OUTPUT = "scheduler-output";

    @Output(SchedulerOutput.OUTPUT)
    MessageChannel output();
}
