package org.ciencialabart.meower.client.rest;

import javax.ws.rs.client.Client;

public interface RESTClient {

    public static final String WEB_TARGET_URL = "http://localhost:8080/meower/webapi";

    Client get();

}
