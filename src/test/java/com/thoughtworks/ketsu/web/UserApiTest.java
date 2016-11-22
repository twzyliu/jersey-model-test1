package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.CurrentUser;
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
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by zyongliu on 22/11/16.
 */
public class UserApiTest extends JerseyTest {

    private Users users;
    private CurrentUser currentUser;


    @Override
    protected Application configure() {
        return new ResourceConfig(RoutesFeature.class)
                .packages("com.thoughtworks.ketsu")
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(users).to(Users.class);
                        bind(currentUser).to(CurrentUser.class);
                    }
                });
    }

    @Override
    @Before
    public void setUp() throws Exception {
        users = mock(Users.class);
        currentUser = mock(CurrentUser.class);
        when(currentUser.getCurrentUser()).thenReturn(Optional.of(user));
        when(users.create(any())).thenReturn(Optional.of(user));
        when(users.findById(any())).thenReturn(Optional.of(user));
        super.setUp();
    }

    @Test
    public void should_return_201_when_creat_user_success() throws Exception {
        Response post = target("/users").request().post(Entity.json(user));

        assertThat(post.getStatus(), is(201));
        assertThat(post.getLocation().toString().contains(USERNAME_A), is(true));
    }

    @Test
    public void should_return_400_when_user_is_exist() throws Exception {
        when(users.create(any())).thenReturn(Optional.empty());
        Response post = target("/users").request().post(Entity.json(user));

        assertThat(post.getStatus(), is(400));
    }

    @Test
    public void should_return_200_when_user_get_his_info() throws Exception {
        Response response = target("/users/" + USERNAME_A).request().get();

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void should_return_404_when_user_get_info_fail() throws Exception {
        when(users.findById(any())).thenReturn(Optional.empty());
        Response response = target("/users/" + USERNAME_A).request().get();

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_return_404_when_user_get_other_info() throws Exception {
        when(currentUser.getCurrentUser()).thenReturn(Optional.of(otherUser));
        Response response = target("/users/" + USERNAME_A).request().get();

        assertThat(response.getStatus(), is(404));
    }
}

















