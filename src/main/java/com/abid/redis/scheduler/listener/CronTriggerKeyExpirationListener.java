package com.abid.redis.scheduler.listener;

import com.abid.redis.scheduler.bean.Scheduler;
import com.abid.redis.scheduler.util.SchedulerConstant;
import com.abid.redis.scheduler.util.SchedulerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CronTriggerKeyExpirationListener implements MessageListener {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SchedulerUtil schedulerUtil;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String key = new String(message.getBody());
        log.info("Expiration message received for key={}", key);

        Scheduler scheduler = (Scheduler) redisTemplate.opsForHash().get(SchedulerConstant.SCHEDULER_KEY,
                key.substring(0, key.indexOf(":")));

        log.info("Creating next trigger for schedule name={}", scheduler.getName());
        schedulerUtil.createNextTrigger(scheduler);

    }
}
