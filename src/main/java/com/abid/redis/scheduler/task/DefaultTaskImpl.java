package com.abid.redis.scheduler.task;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultTaskImpl implements Task {
    private static final String NAME = "DefaultTask";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void execute() {
        log.info("Default task is being executed");
    }
}
