package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.CurrentUser;
import com.thoughtworks.ketsu.domain.User;
import com.thoughtworks.ketsu.domain.Users;
import com.thoughtworks.ketsu.web.jersey.Routes;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

/**
 * Created by zyongliu on 22/11/16.
 */
@Path("users")
public class UsersApi {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(String username,
                           @Context Users users,
                           @Context Routes routes,
                           @Context CurrentUser currentUser) {
        Optional<User> user = users.create(username);
        if (user.isPresent()) {
            return Response.status(201).location(routes.userUrl(user)).build();
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @Path("{uid}")
    public UserApi userApi(@PathParam("uid") String uid,
                           @Context Users users) {
        return users.findById(uid).map(UserApi::new).orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }
}
