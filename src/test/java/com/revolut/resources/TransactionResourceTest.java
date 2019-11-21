package com.revolut.resources;

import com.revolut.BasicIntegrationTest;
import com.revolut.model.Transaction;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

class TransactionResourceTest extends BasicIntegrationTest {

    private static final String DEFAULT_TRANSACTION = "[{\"id\":1,\"fromUser\":1,\"toUser\":2,\"amount\":100.0000,\"status\":\"Pending\"}]";
    private static final String SUCCESSFUL_TRANSFER_TRANSACTION = "[{\"id\":1,\"fromUser\":1,\"toUser\":2,\"amount\":100.0000,\"status\":\"Pending\"},{\"id\":2,\"fromUser\":2,\"toUser\":1,\"amount\":10.0000,\"status\":\"Committed\"}]";
    private static final String UNSUCCESSFUL_TRANSFER_TRANSACTION = "[{\"id\":1,\"fromUser\":1,\"toUser\":2,\"amount\":100.0000,\"status\":\"Pending\"},{\"id\":2,\"fromUser\":2,\"toUser\":1,\"amount\":10000.0000,\"status\":\"Failed\"}]";
    private static final String ACCOUNT1_SUCCESSFUL_TRANSFER = "{\"id\":1,\"userId\":2,\"balance\":310.0000}";
    private static final String ACCOUNT2_SUCCESSFUL_TRANSFER = "{\"id\":2,\"userId\":1,\"balance\":290.0000}";
    private static final String ACCOUNT1_UNSUCCESSFUL_TRANSFER = "{\"id\":1,\"userId\":2,\"balance\":300.0000}";
    private static final String ACCOUNT2_UNSUCCESSFUL_TRANSFER = "{\"id\":2,\"userId\":1,\"balance\":300.0000}";

    @Test
    public void getAllTransaction() {
        JerseyClient client = new JerseyClientBuilder().build();

        Response response = client.target(
                String.format(BASE_URL + "/transaction", RULE.getLocalPort()))
                .request()
                .get();

        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals(DEFAULT_TRANSACTION, response.readEntity(String.class));
    }

    @Test
    public void getById() {
        JerseyClient client = new JerseyClientBuilder().build();

        Response response = client.target(
                String.format(BASE_URL + "/transaction/1", RULE.getLocalPort()))
                .request()
                .get();

        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals((Integer) 1, response.readEntity(Transaction.class).getId());
    }

    @Test
    public void addSuccessfulTransaction() {
        JerseyClient client = new JerseyClientBuilder().build();

        Transaction transaction = new Transaction(null, 2, 1, new BigDecimal(10), null);

        Response response = client.target(
                String.format(BASE_URL + "/transaction", RULE.getLocalPort()))
                .request()
                .post(Entity.entity(transaction, MediaType.APPLICATION_JSON_TYPE));

        Assert.assertEquals(200, response.getStatus());

        Response allTransactionsResponse = client.target(
                String.format(BASE_URL + "/transaction", RULE.getLocalPort()))
                .request()
                .get();

        Assert.assertEquals(200, allTransactionsResponse.getStatus());
        Assert.assertEquals(SUCCESSFUL_TRANSFER_TRANSACTION, allTransactionsResponse.readEntity(String.class));

        Response account2Response = client.target(
                String.format(BASE_URL + "/account/2", RULE.getLocalPort()))
                .request()
                .get();

        Assert.assertEquals(ACCOUNT2_SUCCESSFUL_TRANSFER, account2Response.readEntity(String.class));

        Response account1Response = client.target(
                String.format(BASE_URL + "/account/1", RULE.getLocalPort()))
                .request()
                .get();

        Assert.assertEquals(ACCOUNT1_SUCCESSFUL_TRANSFER, account1Response.readEntity(String.class));
    }

    @Test
    public void addUnsuccessfulTransaction() {


        JerseyClient client = new JerseyClientBuilder().build();

        Transaction transaction = new Transaction(null, 2, 1, new BigDecimal(10000), null);

        Response response = client.target(
                String.format(BASE_URL + "/transaction", RULE.getLocalPort()))
                .request()
                .post(Entity.entity(transaction, MediaType.APPLICATION_JSON_TYPE));

        Assert.assertEquals(409, response.getStatus());

        Response allTransactionsResponse = client.target(
                String.format(BASE_URL + "/transaction", RULE.getLocalPort()))
                .request()
                .get();

        Assert.assertEquals(200, allTransactionsResponse.getStatus());
        Assert.assertEquals(UNSUCCESSFUL_TRANSFER_TRANSACTION, allTransactionsResponse.readEntity(String.class));

        Response account2Response = client.target(
                String.format(BASE_URL + "/account/2", RULE.getLocalPort()))
                .request()
                .get();

        Assert.assertEquals(ACCOUNT2_UNSUCCESSFUL_TRANSFER, account2Response.readEntity(String.class));

        Response account1Response = client.target(
                String.format(BASE_URL + "/account/1", RULE.getLocalPort()))
                .request()
                .get();

        Assert.assertEquals(ACCOUNT1_UNSUCCESSFUL_TRANSFER, account1Response.readEntity(String.class));
    }
}