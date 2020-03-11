package com.abid.redis.scheduler.bean;

import java.io.Serializable;

import com.abid.redis.scheduler.task.Task;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
@Builder(toBuilder = true)
public class Scheduler implements Serializable {
    private String name;
    private String cronExpression;
    private Class<Task> taskClass;
}
