package com.abid.redis.scheduler.service;

import com.abid.redis.scheduler.bindings.SchedulerInput;
import com.abid.redis.scheduler.event.Scheduler;
import com.abid.redis.scheduler.util.SchedulerConstant;
import com.abid.redis.scheduler.util.SchedulerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class SchedulerSinkImpl implements SchedulerSink {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SchedulerUtil schedulerUtil;

    @StreamListener(value = SchedulerInput.INPUT, condition = "headers['eventType'] == 'CRUD' and " +
            "headers['operationType'] == 'CREATE'")
    @Transactional
    @Override
    public void create(Scheduler scheduler) throws SchedulerServiceException {
        try {
            if (redisTemplate.opsForHash().hasKey(SchedulerConstant.SCHEDULER_KEY, scheduler.getName())) {
                throw new SchedulerServiceException("Conflict", "Scheduler already exists with same name");
            }

            redisTemplate.opsForHash().put(SchedulerConstant.SCHEDULER_KEY, scheduler.getName(), scheduler);
            schedulerUtil.createNextTrigger(scheduler);

            log.info("Successfully created schedule for name={} and expression={}", scheduler.getName(),
                    scheduler.getCronExpression());
        } catch (Exception ex) {
            log.error("Failed to create scheduler due to {}", ex);
        }
    }

    @StreamListener(value = SchedulerInput.INPUT, condition = "headers['eventType'] == 'CRUD' and " +
            "headers['operationType'] == 'UPDATE'")
    @Transactional
    @Override
    public void update(Scheduler scheduler) throws SchedulerServiceException {
        try {
            if (!redisTemplate.opsForHash().hasKey(SchedulerConstant.SCHEDULER_KEY, scheduler.getName())) {
                throw new SchedulerServiceException("Not Found", "Scheduler does not exists");
            }

            Scheduler currentScheduler = (Scheduler) redisTemplate.opsForHash().get(SchedulerConstant.SCHEDULER_KEY,
                    scheduler.getName());
            redisTemplate.delete(scheduler.getName() + SchedulerConstant.TRIGGER_SUFFIX);

            redisTemplate.opsForHash().put(SchedulerConstant.SCHEDULER_KEY, scheduler.getName(), scheduler);

            schedulerUtil.createNextTrigger(currentScheduler);
            log.info("Successfully updated schedule for name={} and expression={}", scheduler.getName(),
                    scheduler.getCronExpression());
        } catch (Exception ex) {
            log.error("Failed to update scheduler due to {}", ex);
        }
    }

    @StreamListener(value = SchedulerInput.INPUT, condition = "headers['eventType'] == 'CRUD' and " +
            "headers['operationType'] == 'DELETE'")
    @Transactional
    @Override
    public void delete(Scheduler scheduler) throws SchedulerServiceException {
        try {
            if (!redisTemplate.opsForHash().hasKey(SchedulerConstant.SCHEDULER_KEY, scheduler.getName())) {
                throw new SchedulerServiceException("Not Found", "Scheduler does not exists");
            }

            redisTemplate.delete(scheduler.getName() + SchedulerConstant.TRIGGER_SUFFIX);
            redisTemplate.opsForHash().delete(SchedulerConstant.SCHEDULER_KEY, scheduler.getName());

            log.info("Successfully deleted schedule for name={} and expression={}", scheduler.getName(),
                    scheduler.getCronExpression());
        } catch (Exception ex) {
            log.error("Failed to delete scheduler due to {}", ex);
        }
    }
}
