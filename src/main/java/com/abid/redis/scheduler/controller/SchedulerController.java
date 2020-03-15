package com.abid.redis.scheduler.controller;

import javax.servlet.http.HttpServletRequest;

import com.abid.redis.scheduler.bean.SchedulerWS;
import com.abid.redis.scheduler.bindings.SchedulerOutput;
import com.abid.redis.scheduler.event.EventType;
import com.abid.redis.scheduler.event.Scheduler;
import com.abid.redis.scheduler.event.SchedulerOperation;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/schedulers")
public class SchedulerController {
    @Autowired
    @Qualifier("beanMapper")
    private MapperFacade mapperFacade;

    @Autowired
    private SchedulerOutput schedulerOutput;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Boolean> create(@RequestBody SchedulerWS schedulerWS, HttpServletRequest request) {
        log.info("Schedule request received for={}", schedulerWS.toString());
        Scheduler scheduler = mapperFacade.map(schedulerWS, Scheduler.class);
        scheduler.setOperation(SchedulerOperation.CREATE);
        schedulerOutput.output().send(MessageBuilder.withPayload(scheduler).setHeader("eventType", EventType.CRUD).setHeader("operationType", SchedulerOperation.CREATE).build());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Boolean> update(@RequestBody SchedulerWS schedulerWS) {
        Scheduler scheduler = mapperFacade.map(schedulerWS, Scheduler.class);
        scheduler.setOperation(SchedulerOperation.UPDATE);
        schedulerOutput.output().send(MessageBuilder.withPayload(scheduler).setHeader("eventType", EventType.CRUD).setHeader("operationType", SchedulerOperation.UPDATE).build());
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Boolean> delete(@RequestBody SchedulerWS schedulerWS) {
        Scheduler scheduler = mapperFacade.map(schedulerWS, Scheduler.class);
        scheduler.setOperation(SchedulerOperation.DELETE);
        schedulerOutput.output().send(MessageBuilder.withPayload(scheduler).setHeader("eventType", EventType.CRUD).setHeader("operationType", SchedulerOperation.DELETE).build());
        return ResponseEntity.accepted().build();
    }
}
