package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.CurrentUser;
import com.thoughtworks.ketsu.domain.Orders;
import com.thoughtworks.ketsu.domain.Payments;
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
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by zyongliu on 23/11/16.
 */
public class OrderApiTest extends JerseyTest {
    private CurrentUser currentUser;
    private Users users;
    private Orders orders;
    private Payments payments;

    @Override
    protected Application configure() {
        return new ResourceConfig(RoutesFeature.class)
                .packages("com.thoughtworks.ketsu")
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(currentUser).to(CurrentUser.class);
                        bind(users).to(Users.class);
                        bind(orders).to(Orders.class);
                        bind(payments).to(Payments.class);
                    }
                });
    }

    @Override
    @Before
    public void setUp() throws Exception {
        users = mock(Users.class);
        orders = mock(Orders.class);
        currentUser = mock(CurrentUser.class);
        payments = mock(Payments.class);
        when(currentUser.getCurrentUser()).thenReturn(Optional.of(user));
        when(users.findById(any())).thenReturn(Optional.of(user));
        when(orders.createOrder(any(),any())).thenReturn(Optional.of(order));
        when(orders.findByUidOid(eq(user.getUsername()), eq(order.getOid()))).thenReturn(Optional.of(order));
        when(orders.findAllByUid(eq(user.getUsername()))).thenReturn(orderList);
        when(payments.getPayment(anyInt())).thenReturn(payment);
        super.setUp();
    }

    @Test
    public void should_return_201_when_user_create_order_success() throws Exception {
        Response response = target(String.format("/users/%s/orders", user.getUsername())).request().post(Entity.json(order));

        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation().toString().contains(order.getOid() + ""), is(true));
    }

    @Test
    public void should_return_400_when_user_create_order_fail() throws Exception {
        when(orders.createOrder(any(),any())).thenReturn(Optional.empty());
        Response response = target(String.format("/users/%s/orders", user.getUsername())).request().post(Entity.json(order));

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_return_404_when_other_create_my_order() throws Exception {
        when(currentUser.getCurrentUser()).thenReturn(Optional.of(otherUser));
        Response response = target(String.format("/users/%s/orders", user.getUsername())).request().post(Entity.json(order));

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_return_200_when_user_get_his_order() throws Exception {
        Response response = target(String.format("/users/%s/orders/%s", user.getUsername(),order.getOid())).request().get();

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void should_return_404_when_can_not_find_order() throws Exception {
        when(orders.findByUidOid(eq(user.getUsername()), eq(order.getOid()))).thenReturn(Optional.empty());
        Response response = target(String.format("/users/%s/orders/%s", user.getUsername(),order.getOid())).request().get();

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_return_404_when_get_other_order() throws Exception {
        when(currentUser.getCurrentUser()).thenReturn(Optional.of(otherUser));
        Response response = target(String.format("/users/%s/orders/%s", user.getUsername(),order.getOid())).request().get();

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_return_200_when_user_get_all_orders() throws Exception {
        Response response = target(String.format("/users/%s/orders", user.getUsername())).request().get();

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void should_return_404_when_user_can_not_find_orders() throws Exception {
        when(orders.findAllByUid(any())).thenReturn(asList());
        Response response = target(String.format("/users/%s/orders", user.getUsername())).request().get();

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_return_404_when_user_get_other_orders() throws Exception {
        when(currentUser.getCurrentUser()).thenReturn(Optional.of(otherUser));
        Response response = target(String.format("/users/%s/orders", user.getUsername())).request().get();

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_return_200_when_user_get_payment() throws Exception {
        Response response = target(String.format("/users/%s/orders/%s/payment", user.getUsername(),order.getOid())).request().get();

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void should_return_404_when_can_not_find_payment() throws Exception {
        when(payments.getPayment(anyInt())).thenReturn(null);
        Response response = target(String.format("/users/%s/orders/%s/payment", user.getUsername(),order.getOid())).request().get();

        assertThat(response.getStatus(), is(404));

    }

    @Test
    public void should_return_404_when_user_get_other_users_payment() throws Exception {
        when(currentUser.getCurrentUser()).thenReturn(Optional.of(otherUser));
        Response response = target(String.format("/users/%s/orders/%s/payment", user.getUsername(),order.getOid())).request().get();

        assertThat(response.getStatus(), is(404));
    }
}

















