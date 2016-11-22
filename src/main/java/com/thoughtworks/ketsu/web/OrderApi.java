package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

/**
 * Created by zyongliu on 23/11/16.
 */
public class OrderApi {
    private Order order;

    public OrderApi(Order order) {
        this.order = order;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Order findByUidOid(@Context Users users,
                              @Context CurrentUser currentUser) {
        Optional<User> user = users.findById(order.getUsername());
        Optional<User> current = currentUser.getCurrentUser();
        if (user.isPresent() && current.isPresent() && current.get().equals(user.get())) {
            return order;
        } else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @Path("payment")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Payment getPayment(@Context Payments payments,
                              @Context Users users,
                              @Context CurrentUser currentUser) {
        Optional<User> user = users.findById(order.getUsername());
        Optional<User> current = currentUser.getCurrentUser();

        Payment payment = payments.getPayment(order.getOid());
        if (payment != null && user.isPresent() && current.isPresent() && current.get().equals(user.get())) {
            return payment;
        } else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

}
