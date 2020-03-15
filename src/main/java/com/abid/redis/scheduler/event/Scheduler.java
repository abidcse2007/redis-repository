package com.abid.redis.scheduler.event;

import java.io.Serializable;
import java.util.Date;

import com.abid.redis.scheduler.task.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Scheduler implements Serializable {
    private String uuid;
    private String name;
    private String cronExpression;
    private String taskClass;
    private Date startDate;
    private Date endDate;
    private SchedulerOperation operation;
}
