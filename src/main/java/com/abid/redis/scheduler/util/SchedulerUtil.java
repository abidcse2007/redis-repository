package com.abid.redis.scheduler.util;

import java.util.Date;

import com.abid.redis.scheduler.event.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.stereotype.Component;

@Component
public class SchedulerUtil {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @param scheduler
     */
    public void createNextTrigger(Scheduler scheduler) {
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(scheduler.getCronExpression());
        Date nextTrigger = cronSequenceGenerator.next(new Date());
        redisTemplate.opsForValue().set(scheduler.getName() + SchedulerConstant.TRIGGER_SUFFIX, scheduler.getName());
        redisTemplate.expireAt(scheduler.getName() + SchedulerConstant.TRIGGER_SUFFIX, nextTrigger);
    }
}
