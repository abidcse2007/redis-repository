package com.abid.redis.scheduler.bindings;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface Output {
    String OUTPUT = "output";

    @Output(Output.OUTPUT)
    MessageChannel output();
}
