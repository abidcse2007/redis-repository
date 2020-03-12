# Scheduler using Redis

## Prerequisite
* Redis :  Basic understanding of [redis](https://redis.io/) is required for this solution. We are going to use mostly Hash and Set operations
* Spring Cloud Stream :  We are using [Spring Cloud Stream](https://cloud.spring.io/spring-cloud-static/spring-cloud-stream/3.0.3.RELEASE/reference/html/spring-cloud-stream.html#spring-cloud-stream-reference) to have event driven communication between components


## Setup
* Install Redis ans start redis. Please follow step [here](https://redis.io/download). Please choose stable version.


## Architecture

### Overview

![Architecture](https://www.lucidchart.com/invitations/accept/d23815c0-2869-49c7-891a-11ed5bc68f62)


## Code

We are using `LettuceConnectionFactory`  

```
@Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory("localhost", 6379);
    }
```


`RedisTemplate` is configured as below 

```
@Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }
```

We are also having a listener configured to process expiration events.

```
@Bean
    RedisMessageListenerContainer keyExpirationListenerContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.addMessageListener(cronTriggerKeyExpirationListener(), new PatternTopic("__keyevent@*__" +
                ":expired"));
        return listenerContainer;
    }
```

## How to build
Go to terminal and run ```./gradlew clean build```

