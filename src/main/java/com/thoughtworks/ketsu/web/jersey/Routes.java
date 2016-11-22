package com.thoughtworks.ketsu.web.jersey;

import com.thoughtworks.ketsu.domain.Order;
import com.thoughtworks.ketsu.domain.RefundOrder;
import com.thoughtworks.ketsu.domain.User;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Optional;

public class Routes {

    private final String baseUri;

    public Routes(UriInfo uriInfo) {
        baseUri = uriInfo.getBaseUri().toASCIIString();
    }

    public URI userUrl(Optional<User> user) {
        return URI.create(String.format("%susers/%s",baseUri,user.get().getUsername()));
    }

    public URI orderUrl(User user, Optional<Order> order) {
        return URI.create(String.format("%susers/%s/orders/%s",baseUri,user.getUsername(),order.get().getOid()));
    }

    public URI refundOrderUrl(User user, RefundOrder refundOrder) {
        return URI.create(String.format("%susers/%s/orders/%s",baseUri,user.getUsername(),refundOrder.getRoid()));
    }
}
