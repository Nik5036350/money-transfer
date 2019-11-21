package com.revolut.resources;

import com.revolut.BasicIntegrationTest;
import com.revolut.model.User;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

class UserResourceTest extends BasicIntegrationTest {

    @Test
    public void userByIdTest() {
        JerseyClient client = new JerseyClientBuilder().build();

        Response response = client.target(
                String.format(BASE_URL + "/user/1", RULE.getLocalPort()))
                .request()
                .get();

        Assert.assertEquals(200, response.getStatus());

        User user = response.readEntity(User.class);
        Assert.assertEquals((Integer) 1, user.getId());
    }

    @Test
    public void allUsersTest() {
        JerseyClient client = new JerseyClientBuilder().build();

        Response response = client.target(
                String.format(BASE_URL + "/user", RULE.getLocalPort()))
                .request()
                .get();

        Assert.assertEquals(200, response.getStatus());

        List<User> users = response.readEntity(new GenericType<List<User>>() {
        });
        Assert.assertEquals(3, users.size());
    }

    @Test
    public void addAccountTest() {
        JerseyClient client = new JerseyClientBuilder().build();

        User user = new User("user4", "user4@gmail.com");

        Response response = client.target(
                String.format(BASE_URL + "/user", RULE.getLocalPort()))
                .request()
                .post(Entity.entity(user, MediaType.APPLICATION_JSON_TYPE));

        Assert.assertEquals(204, response.getStatus());

        Response allAccountsResponse = client.target(
                String.format(BASE_URL + "/user", RULE.getLocalPort()))
                .request()
                .get();

        List<User> users = allAccountsResponse.readEntity(new GenericType<List<User>>() {
        });
        Assert.assertEquals(4, users.size());
    }
}