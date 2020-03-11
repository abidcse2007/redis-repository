package com.abid.redis.scheduler.service;

import com.abid.redis.scheduler.bean.Scheduler;
import com.abid.redis.scheduler.util.SchedulerConstant;
import com.abid.redis.scheduler.util.SchedulerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class SchedulerServiceImpl implements SchedulerService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SchedulerUtil schedulerUtil;

    @Transactional
    @Override
    public boolean createSchedule(Scheduler scheduler) throws SchedulerServiceException {
        try {
            if (redisTemplate.opsForHash().hasKey(SchedulerConstant.SCHEDULER_KEY, scheduler.getName())) {
                throw new SchedulerServiceException("Conflict", "Scheduler already exists with same name");
            }

            redisTemplate.opsForHash().put(SchedulerConstant.SCHEDULER_KEY, scheduler.getName(), scheduler);
            schedulerUtil.createNextTrigger(scheduler);

            log.info("Successfully created schedule for name={} and expression={}", scheduler.getName(),
                    scheduler.getCronExpression());
            return true;
        } catch (Exception ex) {
            log.error("Failed to create scheduler due to {}", ex);
            return false;
        }
    }

    @Transactional
    @Override
    public boolean updateSchedule(Scheduler scheduler) throws SchedulerServiceException {
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
            return true;
        } catch (Exception ex) {
            log.error("Failed to update scheduler due to {}", ex);
            return false;
        }
    }

    @Transactional
    @Override
    public boolean deleteSchedule(Scheduler scheduler) throws SchedulerServiceException {
        try {
            if (!redisTemplate.opsForHash().hasKey(SchedulerConstant.SCHEDULER_KEY, scheduler.getName())) {
                throw new SchedulerServiceException("Not Found", "Scheduler does not exists");
            }

            redisTemplate.delete(scheduler.getName() + SchedulerConstant.TRIGGER_SUFFIX);
            redisTemplate.opsForHash().delete(SchedulerConstant.SCHEDULER_KEY, scheduler.getName());

            log.info("Successfully deleted schedule for name={} and expression={}", scheduler.getName(),
                    scheduler.getCronExpression());
            return true;
        } catch (Exception ex) {
            log.error("Failed to delete scheduler due to {}", ex);
            return false;
        }
    }
}
