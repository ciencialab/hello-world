package org.ciencialabart.meower.api.rest;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.ciencialabart.meower.api.PostBean;
import org.ciencialabart.meower.data.PostDTO;
import org.ciencialabart.meower.exception.UserNotFoundException;
import org.ciencialabart.meower.service.annotation.Service;

@Path("/user/{user}")
@Consumes("application/json")
@Produces("application/json")
public class PostRESTController {

    @Inject
    @Service
    private PostBean postBean;

    @POST
    @Path("/post")
    public Response createPost(
            @PathParam("user") String userName,
            @Valid @Size(max=140, message="{postTooLong}") String postContent) {
        ResponseBuilder responseBuilder;
        
        postBean.createPost(userName, postContent);
        responseBuilder = Response.ok();
        
        return responseBuilder.build();
    }

    @GET
    @Path("/posts")
    public Response getPosts(@PathParam("user") String userName) {
        ResponseBuilder responseBuilder;
        
        try {
            responseBuilder = Response.ok(
                    new GenericEntity<List<PostDTO>>(postBean.getPosts(userName)) {});
        } catch (UserNotFoundException e) {
            responseBuilder = Response.status(Status.NOT_FOUND);
        }
        
        return responseBuilder.build();
    }

    @GET
    @Path("/followed-posts")
    public Response getFollowedPosts(@PathParam("user") String userName) throws UserNotFoundException {
        ResponseBuilder responseBuilder;
        
        try {
            responseBuilder = Response.ok(
                    new GenericEntity<List<PostDTO>>(postBean.getFollowedPosts(userName)) {});
        } catch (UserNotFoundException e) {
            responseBuilder = Response.status(Status.NOT_FOUND);
        }
        
        return responseBuilder.build();
    }

}
