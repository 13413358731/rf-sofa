package com.realfinance.sofa.flow.util;

import com.alipay.sofa.runtime.api.aware.ClientFactoryAware;
import com.alipay.sofa.runtime.api.client.ClientFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class SofaClientFactoryHolder implements ClientFactoryAware {

    private static AtomicReference<ClientFactory> CLIENT_FACTORY = new AtomicReference<>();

    @Override
    public void setClientFactory(ClientFactory clientFactory) {
        SofaClientFactoryHolder.CLIENT_FACTORY.set(clientFactory);
    }

    public static ClientFactory get() {
        ClientFactory clientFactory = CLIENT_FACTORY.get();
        if (clientFactory == null) {
            throw new RuntimeException("ClientFactory未注入");
        }
        return clientFactory;
    }
}
