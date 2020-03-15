package com.abid.redis.scheduler.mapper;

import java.util.Base64;

import com.appdirect.appconnector.api.ws.ConfigurationRequestWS;
import com.appdirect.appconnector.core.constants.EntityType;
import com.appdirect.appconnector.core.constants.OAuthType;
import com.appdirect.appconnector.service.configuration.bean.ConfigurationBean;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationRequestWSMapper extends CustomMapper<ConfigurationRequestWS, ConfigurationBean> {

    @Override
    public void mapAtoB(ConfigurationRequestWS configurationRequestWS, ConfigurationBean configurationBean, MappingContext context) {
        configurationBean.setTenant(configurationRequestWS.getTenant());
        configurationBean.setEntityType(EntityType.valueOf(configurationRequestWS.getEntityType()));
        configurationBean.setType(OAuthType.valueOf(configurationRequestWS.getType()));
        configurationBean.setKeyId(configurationRequestWS.getKey());
        configurationBean.setSecretId(Base64.getEncoder().encodeToString(configurationRequestWS.getSecret().getBytes()));
        configurationBean.setBaseUrl(configurationRequestWS.getBaseUrl());
        configurationBean.setTokenUrl(configurationRequestWS.getTokenUrl());
    }
}
