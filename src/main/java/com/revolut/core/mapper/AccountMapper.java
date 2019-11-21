package com.revolut.core.mapper;

import com.revolut.model.Account;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper implements ResultSetMapper<Account> {
    public Account map(int index, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new Account(
                resultSet.getInt("Id"),
                resultSet.getInt("UserId"),
                resultSet.getBigDecimal("Balance")
        );
    }
}