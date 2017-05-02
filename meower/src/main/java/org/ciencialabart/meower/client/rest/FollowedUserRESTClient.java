package org.ciencialabart.meower.client.rest;

import static org.ciencialabart.meower.client.rest.RESTClient.WEB_TARGET_URL;
import static org.ciencialabart.meower.client.rest.UserRESTClient.PARAMETER_USER;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.ciencialabart.meower.api.FollowedUserBean;
import org.ciencialabart.meower.client.rest.annotation.REST;
import org.ciencialabart.meower.exception.UserNotFoundException;

@REST
@Stateless
public class FollowedUserRESTClient implements FollowedUserBean {
    
    private static final String RESOURCE_USER = "/user/{user}";
    private static final String RESOURCE_FOLLOW = RESOURCE_USER + "/follow";
    private static final String RESOURCE_FOLLOWED_USERS = RESOURCE_USER + "/followed-users";
    private static final String RESOURCE_UNFOLLOW = RESOURCE_USER + "/unfollow";

    @Inject
    RESTClient client;

    @Override
    public void follow(String userName, String userNameToFollow) throws UserNotFoundException {
        Response response = null;
        
        try {
            response = client.get()
                    .target(WEB_TARGET_URL)
                    .path(RESOURCE_FOLLOW)
                    .resolveTemplate(PARAMETER_USER, userName, true)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                        .post(Entity.json(userNameToFollow));
            
            if (response.getStatus() == Status.NOT_FOUND.getStatusCode()) {
                throw new UserNotFoundException(userName);
            }
            if (response.getStatus() == Status.BAD_REQUEST.getStatusCode()) {
                throw new UserNotFoundException(userNameToFollow);
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    @Override
    public List<String> getFollowedUsers(String userName) throws UserNotFoundException {
        try {
            return client.get()
                    .target(WEB_TARGET_URL)
                    .path(RESOURCE_FOLLOWED_USERS)
                    .resolveTemplate(PARAMETER_USER, userName, true)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                        .get(new GenericType<List<String>>(){});
        } catch (WebApplicationException e) {
            int statusCode = e.getResponse().getStatusInfo().getStatusCode();
            
            if (statusCode == Status.NOT_FOUND.getStatusCode()) {
                e.getResponse().close();
                throw new UserNotFoundException(userName);
            } else {
                throw new WebApplicationException(e.getResponse());
            }
        }
    }

    @Override
    public void unfollow(String userName, String followedUserName) throws UserNotFoundException {
        Response response = null;
        
        try {
            response = client.get()
                    .target(WEB_TARGET_URL)
                    .path(RESOURCE_UNFOLLOW)
                    .resolveTemplate(PARAMETER_USER, userName, true)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                        .post(Entity.json(followedUserName));
            
            if (response.getStatus() == Status.NOT_FOUND.getStatusCode()) {
                throw new UserNotFoundException(userName);
            }
            if (response.getStatus() == Status.BAD_REQUEST.getStatusCode()) {
                throw new UserNotFoundException(followedUserName);
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

}
