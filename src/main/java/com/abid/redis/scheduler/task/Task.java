package com.abid.redis.scheduler.task;

public interface Task {
    String getName();
    void execute();
}
