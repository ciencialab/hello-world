package org.ciencialabart.meower.api.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.ciencialabart.meower.api.UserBean;
import org.ciencialabart.meower.service.annotation.Service;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserRESTController {

    @Inject
    @Service
    private UserBean userBean;

    @GET
    public List<String> getUserNames() {
        return userBean.getUserNames();
    }

}
