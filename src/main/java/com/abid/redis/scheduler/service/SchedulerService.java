package com.abid.redis.scheduler.service;

import com.abid.redis.scheduler.bean.Scheduler;

public interface SchedulerService {
    boolean createSchedule(Scheduler scheduler) throws SchedulerServiceException;

    boolean updateSchedule(Scheduler scheduler) throws SchedulerServiceException;

    boolean deleteSchedule(Scheduler scheduler) throws SchedulerServiceException;
}
