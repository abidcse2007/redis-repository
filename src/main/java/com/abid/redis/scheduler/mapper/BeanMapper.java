package com.abid.redis.scheduler.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component("beanMapper")
public class BeanMapper extends ConfigurableMapper {
    @Override
    protected void configure(MapperFactory factory) {
        factory.registerMapper(new ConfigurationRequestWSMapper());
        factory.registerMapper(new ConfigurationResponseWSMapper());
    }
}
