package com.revolut.resources;

import com.codahale.metrics.annotation.Timed;
import com.revolut.db.AccountDAO;
import com.revolut.model.Account;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

    @Inject
    AccountDAO accountDAO;

    @GET
    @Timed
    public List<Account> getAll() {
        return accountDAO.getAll();
    }

    @POST
    @Timed
    public void add(Account account) {
        accountDAO.insert(account);
    }

    @GET
    @Timed
    @Path("/{id}")
    public Account getById(@PathParam("id") Integer id) {
        return accountDAO.findById(id);
    }
}
