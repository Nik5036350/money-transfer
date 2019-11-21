package com.revolut;

import com.revolut.core.service.TransactionService;
import com.revolut.db.*;
import com.revolut.resources.AccountResource;
import com.revolut.resources.TransactionResource;
import com.revolut.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.skife.jdbi.v2.DBI;

public class HwApplication extends Application<HwConfiguration> {

    public static void main(final String[] args) throws Exception {
        new HwApplication().run(args);
    }

    @Override
    public String getName() {
        return "hw";
    }

    @Override
    public void initialize(final Bootstrap<HwConfiguration> bootstrap) {
    }

    @Override
    public void run(final HwConfiguration configuration,
                    final Environment environment) {

        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "h2");

        final TransactionDAO transactionDAO = jdbi.onDemand(TransactionDAO.class);
        final TransactionalDAO transactionalDAO = jdbi.onDemand(TransactionalDAO.class);
        final AccountDAO accountDAO = jdbi.onDemand(AccountDAO.class);
        final UserDAO userDAO = jdbi.onDemand(UserDAO.class);

        JerseyEnvironment jersey = environment.jersey();
        jersey.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(DbHelpers.class).to(DbHelpers.class);
                bind(jdbi).to(DBI.class);
                bind(transactionDAO).to(TransactionDAO.class);
                bind(transactionalDAO).to(TransactionalDAO.class);
                bind(accountDAO).to(AccountDAO.class);
                bind(userDAO).to(UserDAO.class);
                bind(TransactionService.class).to(TransactionService.class);
            }
        });
//        jersey.register(DBI.class);
        jersey.register(UserResource.class);
        jersey.register(TransactionResource.class);
        jersey.register(AccountResource.class);
        InitDB(jdbi);

    }

    private void InitDB(DBI jdbi) {
        new DbHelpers().populateTestData(jdbi);
    }

}
