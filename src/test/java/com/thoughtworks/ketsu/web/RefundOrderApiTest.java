package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.CurrentUser;
import com.thoughtworks.ketsu.domain.RefundOrders;
import com.thoughtworks.ketsu.domain.Users;
import com.thoughtworks.ketsu.web.jersey.RoutesFeature;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.Optional;

import static com.thoughtworks.ketsu.support.TestHelper.*;
import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by zyongliu on 23/11/16.
 */
public class RefundOrderApiTest extends JerseyTest {
    private CurrentUser currentUser;
    private Users users;
    private RefundOrders refundOrders;

    @Override
    protected Application configure() {
        return new ResourceConfig(RoutesFeature.class)
                .packages("com.thoughtworks.ketsu")
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(currentUser).to(CurrentUser.class);
                        bind(users).to(Users.class);
                        bind(refundOrders).to(RefundOrders.class);
                    }
                });
    }

    @Override
    @Before
    public void setUp() throws Exception {
        users = mock(Users.class);
        currentUser = mock(CurrentUser.class);
        refundOrders = mock(RefundOrders.class);
        when(currentUser.getCurrentUser()).thenReturn(Optional.of(user));
        when(users.findById(any())).thenReturn(Optional.of(user));
        when(refundOrders.create(any())).thenReturn(refundOrder);
        when(refundOrders.findAllRefundOrder()).thenReturn(refundOrderList);
        when(refundOrders.findByUidRoid(eq(user.getUsername()), eq(refundOrder.getRoid()))).thenReturn(Optional.of(refundOrder));
        super.setUp();
    }

    @Test
    public void should_return_201_when_create_refund_order_success() throws Exception {
        Response response = target(String.format("/users/%s/refundOrders", user.getUsername())).request().post(Entity.json(refundOrder));

        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation().toString().contains(refundOrder.getRoid() + ""), is(true));
    }

    @Test
    public void should_return_404_when_create_refund_order_fail() throws Exception {
        when(refundOrders.create(any())).thenReturn(null);
        Response response = target(String.format("/users/%s/refundOrders", user.getUsername())).request().post(Entity.json(refundOrder));

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_return_404_when_other_create_my_refund_order() throws Exception {
        when(currentUser.getCurrentUser()).thenReturn(Optional.of(otherUser));
        Response response = target(String.format("/users/%s/refundOrders", user.getUsername())).request().post(Entity.json(refundOrder));

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_return_200_when_user_get_all_refund_orders() throws Exception {
        Response response = target(String.format("/users/%s/refundOrders", user.getUsername())).request().get();

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void should_return_404_when_user_can_not_find_refund_orders() throws Exception {
        when(refundOrders.findAllRefundOrder()).thenReturn(asList());
        Response response = target(String.format("/users/%s/refundOrders", user.getUsername())).request().get();

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_return_404_when_other_get_user_refund_orders() throws Exception {
        when(currentUser.getCurrentUser()).thenReturn(Optional.of(otherUser));
        Response response = target(String.format("/users/%s/refundOrders", user.getUsername())).request().get();

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_return_200_when_user_get_refund_order() throws Exception {
        Response response = target(String.format("/users/%s/refundOrders/%s", user.getUsername(),refundOrder.getRoid())).request().get();

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void should_return_404_when_user_can_not_get_refund_order() throws Exception {
        when(refundOrders.findByUidRoid(eq(user.getUsername()), eq(refundOrder.getRoid()))).thenReturn(Optional.empty());
        Response response = target(String.format("/users/%s/refundOrders/%s", user.getUsername(),refundOrder.getRoid())).request().get();

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_return_404_when_other_get_user_refund_order() throws Exception {
        when(currentUser.getCurrentUser()).thenReturn(Optional.of(otherUser));
        Response response = target(String.format("/users/%s/refundOrders/%s", user.getUsername(),refundOrder.getRoid())).request().get();

        assertThat(response.getStatus(), is(404));

    }
}
