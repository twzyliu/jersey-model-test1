package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.CurrentUser;
import com.thoughtworks.ketsu.domain.Order;
import com.thoughtworks.ketsu.domain.Orders;
import com.thoughtworks.ketsu.domain.User;
import com.thoughtworks.ketsu.web.jersey.Routes;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Created by zyongliu on 23/11/16.
 */
public class OrdersApi {

    private User user;

    public OrdersApi(User user) {
        this.user = user;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOrder(HashMap<String, Object> orderInfo,
                                @Context Orders orders,
                                @Context Routes routes,
                                @Context CurrentUser currentUser) {
        Optional<User> current = currentUser.getCurrentUser();
        if (!current.isPresent() || !current.get().equals(user)) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        Optional<Order> order = orders.createOrder(user, orderInfo);
        if (order.isPresent()) {
            return Response.status(201).location(routes.orderUrl(user, order)).build();
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @Path("{oid}")
    public OrderApi orderApi(@PathParam("oid") long oid,
                             @Context Orders orders) {
        return orders.findByUidOid(user.getUsername(), oid).map(OrderApi::new).orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> getOrders(@Context Orders orders,
                                 @Context CurrentUser currentUser) {
        Optional<User> current = currentUser.getCurrentUser();
        List<Order> orderList = orders.findAllByUid(user.getUsername());
        if (orderList.size() == 0 || !current.isPresent() || !current.get().equals(user)) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } else {
            return orderList;
        }
    }

}
