package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.CurrentUser;
import com.thoughtworks.ketsu.domain.RefundOrder;
import com.thoughtworks.ketsu.domain.User;
import com.thoughtworks.ketsu.domain.Users;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

/**
 * Created by zyongliu on 23/11/16.
 */
public class RefundOrderApi {
    private RefundOrder refundOrder;

    public RefundOrderApi(RefundOrder refundOrder) {

        this.refundOrder = refundOrder;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public RefundOrder getRefundOrder(@Context Users users,
                                      @Context CurrentUser currentUser) {
        Optional<User> user = users.findById(refundOrder.getUsername());
        Optional<User> current = currentUser.getCurrentUser();
        if (user.isPresent() && current.isPresent() && current.get().equals(user.get())) {
            return refundOrder;
        } else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
}
