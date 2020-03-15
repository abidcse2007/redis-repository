package com.abid.redis.scheduler.mapper;

import java.util.UUID;

import com.abid.redis.scheduler.bean.SchedulerWS;
import com.abid.redis.scheduler.event.Scheduler;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class SchedulerWSMapper extends CustomMapper<SchedulerWS, Scheduler> {

    @Override
    public void mapAtoB(SchedulerWS schedulerWS, Scheduler scheduler, MappingContext context) {
        scheduler.setUuid(UUID.randomUUID().toString());
        scheduler.setName(schedulerWS.getName());
        scheduler.setCronExpression(schedulerWS.getCronExpression());
        scheduler.setTaskClass(schedulerWS.getTaskClass());
        scheduler.setStartDate(schedulerWS.getStartDate());
        scheduler.setEndDate(schedulerWS.getEndDate());
    }
}
