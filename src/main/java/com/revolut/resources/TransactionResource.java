package com.revolut.resources;

import com.codahale.metrics.annotation.Timed;
import com.revolut.core.service.TransactionService;
import com.revolut.db.TransactionDAO;
import com.revolut.model.Transaction;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
public class TransactionResource {

    @Inject
    TransactionDAO transactionDAO;

    @Inject
    TransactionService transactionService;

    @GET
    @Timed
    public List<Transaction> getAll() {
        return transactionDAO.getAll();
    }

    @GET
    @Timed
    @Path("/{id}")
    public Transaction getAll(@PathParam("id") Integer id) {
        return transactionDAO.findById(id);
    }

    @POST
    public Response transfer(Transaction transaction) {
        try {
            transactionService.transferMoney(transaction.getFromUser(), transaction.getToUser(), transaction.getAmount());
        } catch (Exception e) {
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity(e.getMessage())
                    .build();
        }
        return Response.ok().build();
    }

}
