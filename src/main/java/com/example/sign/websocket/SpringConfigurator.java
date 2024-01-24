package com.example.sign.websocket;

import org.springframework.context.ApplicationContext;
import jakarta.websocket.server.ServerEndpointConfig.Configurator;

public class SpringConfigurator extends Configurator {
    @Override
    public <T> T getEndpointInstance(Class<T> clazz) throws InstantiationException {
        ApplicationContext context = SpringApplicationContext.getContext();
        return context.getBean(clazz);
    }
}

