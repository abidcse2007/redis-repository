package com.abid.redis.scheduler.service;

import com.abid.redis.scheduler.event.Scheduler;

public interface SchedulerSink {
    void create(Scheduler scheduler) throws SchedulerServiceException;

    void update(Scheduler scheduler) throws SchedulerServiceException;

    void delete(Scheduler scheduler) throws SchedulerServiceException;
}
