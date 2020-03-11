package com.abid.redis.scheduler.service;

import lombok.Data;

@Data
public class SchedulerServiceException extends RuntimeException {
    private String code;

    public SchedulerServiceException(String message) {
        super(message);
    }

    public SchedulerServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

    public SchedulerServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
