package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.*;
import com.thoughtworks.ketsu.web.jersey.Routes;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Created by zyongliu on 23/11/16.
 */
public class RefundOrdersApi {
    private User user;

    public RefundOrdersApi(User user) {
        this.user = user;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(HashMap<String, Object> refundOrderInfo,
                           @Context RefundOrders refundOrders,
                           @Context Routes routes,
                           @Context CurrentUser currentUser) {
        Optional<User> current = currentUser.getCurrentUser();
        RefundOrder refundOrder = refundOrders.create(refundOrderInfo);
        if (refundOrder != null && current.isPresent() && current.get().equals(user)) {
            return Response.status(201).location(URI.create(routes.refundOrderUrl(user, refundOrder).toString())).build();
        } else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<RefundOrder> getByUidRoid(@Context RefundOrders refundOrders,
                                          @Context CurrentUser currentUser) {
        Optional<User> current = currentUser.getCurrentUser();
        List<RefundOrder> refundOrderList = refundOrders.findAllRefundOrder();
        if (refundOrderList.size() == 0 || !current.isPresent() || !current.get().equals(user)) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } else {
            return refundOrderList;
        }
    }

    @Path("{roid}")
    public RefundOrderApi refundOrderApi(@PathParam("roid") long roid,
                                         @Context RefundOrders refundOrders) {
        return refundOrders.findByUidRoid(user.getUsername(), roid).map(RefundOrderApi::new).orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }
}
