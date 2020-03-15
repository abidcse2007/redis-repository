package com.abid.redis.scheduler;

import com.abid.redis.scheduler.bindings.SchedulerInput;
import com.abid.redis.scheduler.bindings.SchedulerOutput;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@EnableBinding({SchedulerOutput.class, SchedulerInput.class})
@EnableRedisRepositories
@SpringBootApplication
public class RedisSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisSchedulerApplication.class, args);
	}

}
