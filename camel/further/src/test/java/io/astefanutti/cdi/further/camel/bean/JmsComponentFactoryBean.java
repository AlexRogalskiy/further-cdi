package io.astefanutti.cdi.further.camel.bean;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.PropertyInject;
import org.apache.camel.component.sjms.SjmsComponent;
import org.apache.camel.impl.DefaultComponent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

public class JmsComponentFactoryBean {

    @PropertyInject(value = "jms.maxConnections", defaultValue = "10")
    private int maxConnections;

    @Produces
    @Named("sjms")
    @ApplicationScoped
    // Cannot return SjmsComponent as UriEndpointComponent is not proxyable
    DefaultComponent sjmsComponent() {
        SjmsComponent component = new SjmsComponent();
        component.setConnectionFactory(new ActiveMQConnectionFactory("vm://broker?broker.persistent=false&broker.useShutdownHook=false"));
        component.setConnectionCount(maxConnections);
        return component;
    }
}
