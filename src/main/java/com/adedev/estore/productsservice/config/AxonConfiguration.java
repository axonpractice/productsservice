package com.adedev.estore.productsservice.config;

import com.thoughtworks.xstream.XStream;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.eventhandling.tokenstore.jpa.JpaTokenStore;
import org.axonframework.serialization.Serializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfiguration {

    @Bean
    public JpaTokenStore tokenStore(EntityManagerProvider entityManagerProvider, Serializer serializer) {
        return JpaTokenStore.builder()
                .entityManagerProvider(entityManagerProvider)
                .serializer(serializer)
                .build();
    }

    @Bean
    public XStream xStream() {
        XStream xStream = new XStream();
        xStream.allowTypesByWildcard(new String[] {"com.adedev.core.command**",
                "com.adedev.estore.productsservice.core.event.**" });
        return xStream;
    }
}
