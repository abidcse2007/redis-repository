package com.abid.redis.scheduler.service;

import java.lang.reflect.Method;

import com.abid.redis.scheduler.bindings.SchedulerInput;
import com.abid.redis.scheduler.event.Scheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class TaskSinkImpl implements TaskSink {

    @StreamListener(value = SchedulerInput.INPUT, condition = "headers['eventType'] == 'TRIGGERED' and " +
            "headers['operationType'] == 'EXECUTE'")
    @Transactional
    @Override
    public void execute(Scheduler scheduler) {
        try {
            log.info("Executing task={} for schedule={}", scheduler.getTaskClass(), scheduler.getName());
            Class aClass = Class.forName(scheduler.getTaskClass());
            Object object = aClass.newInstance();
            Method method = aClass.getDeclaredMethod("execute");
            method.invoke(object, null);
            //TODO store execution history
        } catch (Exception ex) {
            log.error("Failed to execute task={} for scheduler={} due to {}", scheduler.getTaskClass(),
                    scheduler.getName(), ex);
        }
    }
}
