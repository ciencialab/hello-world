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

import org.ciencialabart.meower.client.exception.PostTooLongException;
import org.ciencialabart.meower.client.rest.annotation.REST;
import org.ciencialabart.meower.data.PostDTO;
import org.ciencialabart.meower.exception.UserNotFoundException;

@REST
@Stateless
public class PostRESTClient implements PostClientBean {
    
    private static final String RESOURCE_POST = "/user/{user}/post";
    private static final String RESOURCE_POSTS = "/user/{user}/posts";
    private static final String RESOURCE_FOLLOWED_POSTS = "/user/{user}/followed-posts";

    @Inject
    RESTClient client;

    @Override
    public void createPost(String userName, String postContent) throws PostTooLongException {
        Response response = null;
        
        try {
            response = client.get()
                    .target(WEB_TARGET_URL)
                    .path(RESOURCE_POST)
                    .resolveTemplate(PARAMETER_USER, userName, true)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                        .post(Entity.json(postContent));
            
            if (response.getStatus() == Status.BAD_REQUEST.getStatusCode()) {
                throw new PostTooLongException();
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    @Override
    public List<PostDTO> getPosts(String userName) throws UserNotFoundException {
        try {
            return client.get()
                    .target(WEB_TARGET_URL)
                    .path(RESOURCE_POSTS)
                    .resolveTemplate(PARAMETER_USER, userName, true)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                        .get(new GenericType<List<PostDTO>>(){});
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
    public List<PostDTO> getFollowedPosts(String userName) throws UserNotFoundException {
        try {
            return client.get()
                    .target(WEB_TARGET_URL)
                    .path(RESOURCE_FOLLOWED_POSTS)
                    .resolveTemplate(PARAMETER_USER, userName, true)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                        .get(new GenericType<List<PostDTO>>(){});
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

}
