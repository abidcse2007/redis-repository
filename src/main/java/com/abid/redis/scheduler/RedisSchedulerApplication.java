package com.abid.redis.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@EnableRedisRepositories
@SpringBootApplication
public class RedisSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisSchedulerApplication.class, args);
	}

}
