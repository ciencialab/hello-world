package org.ciencialabart.meower.client.rest;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

@Stateless
public class RESTClientImpl implements RESTClient {
    
    private Client client;

    @PostConstruct
    private void init() {
        client = ClientBuilder.newClient();
    }

    @Override
    public Client get() {
        return client;
    }
    
    @PreDestroy
    private void clean() {
        client.close();
    }

}
