package com.abid.redis.scheduler.bean;

import java.io.Serializable;

import com.abid.redis.scheduler.task.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Scheduler implements Serializable {
    private String name;
    private String cronExpression;
    private Class<Task> taskClass;
}
