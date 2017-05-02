package org.ciencialabart.meower.client.rest;

import static org.ciencialabart.meower.client.rest.RESTClient.WEB_TARGET_URL;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.ciencialabart.meower.api.UserBean;
import org.ciencialabart.meower.client.rest.annotation.REST;

@REST
@Stateless
public class UserRESTClient implements UserBean {
    
    private static final String RESOURCE_USER_NAMES = "/users";
    public static final String PARAMETER_USER = "user";

    @Inject
    RESTClient client;
    
    @Override
    public List<String> getUserNames() {
        return client.get()
                .target(WEB_TARGET_URL)
                .path(RESOURCE_USER_NAMES)
                .request(MediaType.APPLICATION_JSON_TYPE)
                    .get(new GenericType<List<String>>(){});
    }

}
