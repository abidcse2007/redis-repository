package com.abid.redis.scheduler.service;

import com.abid.redis.scheduler.event.Scheduler;

public interface TaskSink {
    void execute(Scheduler scheduler);
}
