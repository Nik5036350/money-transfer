package com.revolut.resources;

import com.revolut.BasicIntegrationTest;
import com.revolut.model.Account;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;

public class AccountResourceTest extends BasicIntegrationTest {

    @Test
    public void accountByIdTest() {
        JerseyClient client = new JerseyClientBuilder().build();

        Response response = client.target(
                String.format(BASE_URL + "/account/1", RULE.getLocalPort()))
                .request()
                .get();

        Assert.assertEquals(200, response.getStatus());

        Account account = response.readEntity(Account.class);
        Assert.assertEquals((Integer) 1, account.getId());
    }

    @Test
    public void allAccountsTest() {
        JerseyClient client = new JerseyClientBuilder().build();

        Response response = client.target(
                String.format(BASE_URL + "/account", RULE.getLocalPort()))
                .request()
                .get();

        Assert.assertEquals(200, response.getStatus());

        List<Account> accounts = response.readEntity(new GenericType<List<Account>>() {});
        Assert.assertEquals(6, accounts.size());
    }

    @Test
    public void addAccountTest() {
        JerseyClient client = new JerseyClientBuilder().build();

        Account account = new Account(null, 2, new BigDecimal(1000));

        Response response = client.target(
                String.format(BASE_URL + "/account", RULE.getLocalPort()))
                .request()
                .post(Entity.entity(account, MediaType.APPLICATION_JSON_TYPE));

        Assert.assertEquals(204, response.getStatus());

        Response allAccountsResponse = client.target(
                String.format(BASE_URL + "/account", RULE.getLocalPort()))
                .request()
                .get();

        List<Account> accounts = allAccountsResponse.readEntity(new GenericType<List<Account>>() {});
        Assert.assertEquals(7, accounts.size());
    }
}