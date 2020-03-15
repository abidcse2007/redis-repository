package com.abid.redis.scheduler.bindings;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface Input {
    String INPUT = "input";


    @Input(Input.INPUT)
    SubscribableChannel input();

}
