package org.ciencialabart.meower.api.rest;

import java.util.List;

import javax.inject.Inject;
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

import org.ciencialabart.meower.api.FollowedUserBean;
import org.ciencialabart.meower.exception.UserNotFoundException;
import org.ciencialabart.meower.service.annotation.Service;

@Path("/user/{user}")
@Consumes("application/json")
@Produces("application/json")
public class FollowedUserRESTController {

    @Inject
    @Service
    private FollowedUserBean followedUserBean;

    @POST
    @Path("/follow")
    public Response follow(@PathParam("user") String userName, String userNameToFollow) {
        ResponseBuilder responseBuilder;
        
        try {
            followedUserBean.follow(userName, userNameToFollow);
            responseBuilder = Response.ok();
        } catch (UserNotFoundException e) {
            if (e.getUserName().equals(userName)) {
                responseBuilder = Response.status(Status.NOT_FOUND);
            } else {
                responseBuilder = Response.status(Status.BAD_REQUEST);
            }
        }
        
        return responseBuilder.build();
    }

    @GET
    @Path("/followed-users")
    public Response getFollowedUsers(@PathParam("user") String userName) {
        ResponseBuilder responseBuilder;
        
        try {
            responseBuilder = Response.ok(
                    new GenericEntity<List<String>>(followedUserBean.getFollowedUsers(userName)) {});
        } catch (UserNotFoundException e) {
            responseBuilder = Response.status(Status.NOT_FOUND);
        }
        
        return responseBuilder.build();
    }

    @POST
    @Path("/unfollow")
    public Response unfollow(@PathParam("user") String userName, String followedUserName) {
        ResponseBuilder responseBuilder;
        
        try {
            followedUserBean.unfollow(userName, followedUserName);
            responseBuilder = Response.ok();
        } catch (UserNotFoundException e) {
            if (e.getUserName().equals(userName)) {
                responseBuilder = Response.status(Status.NOT_FOUND);
            } else {
                responseBuilder = Response.status(Status.BAD_REQUEST);
            }
        }
        
        return responseBuilder.build();
    }

}
