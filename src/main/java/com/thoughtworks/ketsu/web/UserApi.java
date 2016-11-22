package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.CurrentUser;
import com.thoughtworks.ketsu.domain.User;
import com.thoughtworks.ketsu.domain.Users;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

/**
 * Created by zyongliu on 22/11/16.
 */
public class UserApi {
    private User user;

    public UserApi(User user) {
        this.user = user;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@Context Users users,
                        @Context CurrentUser currentUser) {
        Optional<User> current = currentUser.getCurrentUser();
        if (current.isPresent() && current.get().equals(user)) {
            return user;
        } else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @Path("orders")
    public OrdersApi ordersApi() {
        return new OrdersApi(user);
    }

    @Path("refundOrders")
    public RefundOrdersApi refundOrdersApi() {
        return new RefundOrdersApi(user);
    }

}
