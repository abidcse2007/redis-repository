# Scheduler using Redis

## Architecture


![Architecture](https://user-images.githubusercontent.com/59813114/76697657-b1714a80-66bf-11ea-9084-c37bd370ffe0.png)


### Model 

`Scheudle` is the model used in this.  It has an property called eventType and supported values are 
* `SUBMITTED` : Schedule request submitted 
* `TRIGERRED` : Key expired notified


In architecture,  Step 1, SUBMITTED message is published in [Kafka Stream](). Step 6 publishes `TRIGERRED` message in [Kafka Stream]().

```
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Scheduler implements Serializable {
    private String name;
    private String cronExpression;
    private Class<Task> taskClass;
    private Date startDate;
    private Date endDate;
    private Type eventType;
}
```

Attribute     | Description
--------------|------------
name          | TBD
cronExpression| TBD
taskClass     | TBD
startDate     | TBD
endDate       | TBD
eventType          | TBD


## Technologies
* [Spring Boot]()
* [Spring Cloud Stream](https://docs.spring.io/spring-cloud-stream/docs/current/reference/htmlsingle/)
* [Redis](https://redis.io/)


## Prerequisite
* Redis :  Basic understanding of [redis](https://redis.io/) is required for this solution. We are going to use mostly Hash and Set operations
* RabbitMQ


## Setup
* Install Redis ans start redis. Please follow step [here](https://redis.io/download). Please choose stable version.
* Enable key expiration notification as it is not enabled by default `config set notify-keyspace-events KEA`.  For more detail please refer [ Redis Keyspace Notifications](https://redis.io/topics/notifications)
* Create exchange of type `topic` in RabbitMQ
* Create two queue `scheduler-input`  and `scheduler-output`



## Code

### Redis Configuration

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
* Build: `./gradlew clean build`
* Run: `./gradlew bootRun`


## Operations
Operation| URL                                 |Type       |Payload
---------|-------------------------------------|-----------|--------------
Create   |http://localhost:8081/schedulers     | POST      |Create Payload
Update   |http://localhost:8081/schedulers     | PUT       | TBD
Dalete   |http://localhost:8081/schedulers     | DELETE    | TBD




### Create Payload
```
{
	"name": "test1",
	"cronExpression": "0 0/2 * * * *",
	"taskClass":"com.abid.redis.scheduler.task.DefaultTaskImpl"
}
```

### Update Payload
TBD

### DELETE Payload
TBD



## Open Item
- [] Start and End date support 
- [] Unit and integration tests
- [] Storing execution history
- [] API to fetch execution history
- [] Authentication and authorization
