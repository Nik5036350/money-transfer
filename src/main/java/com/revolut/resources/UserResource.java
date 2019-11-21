package com.revolut.resources;

import com.codahale.metrics.annotation.Timed;
import com.revolut.db.UserDAO;
import com.revolut.model.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserDAO userDAO;

    @GET
    @Timed
    public List<User> getAll() {
        return userDAO.getAll();
    }

    @POST
    @Timed
    public void add(User user) {
        userDAO.insert(user);
    }

    @GET
    @Timed
    @Path("/{id}")
    public User getById(@PathParam("id") Integer id) {
        return userDAO.findById(id);
    }

}
