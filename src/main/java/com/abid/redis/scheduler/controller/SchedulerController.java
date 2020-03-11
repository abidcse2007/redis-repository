package com.abid.redis.scheduler.controller;

import com.abid.redis.scheduler.bean.Scheduler;
import com.abid.redis.scheduler.service.SchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/scheduler")
public class SchedulerController {
    @Autowired
    private SchedulerService schedulerService;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public boolean create(@RequestBody Scheduler scheduler) {
        return schedulerService.createSchedule(scheduler);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public boolean update(@RequestBody Scheduler scheduler) {
        return schedulerService.updateSchedule(scheduler);
    }

    @RequestMapping(method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
    public boolean delete(@RequestBody Scheduler scheduler) {
        return schedulerService.deleteSchedule(scheduler);
    }
}
