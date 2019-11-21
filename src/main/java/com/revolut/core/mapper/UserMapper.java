package com.revolut.core.mapper;

import com.revolut.model.User;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ResultSetMapper<User> {
    public User map(int index, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new User(
                resultSet.getInt("Id"),
                resultSet.getString("Name"),
                resultSet.getString("Email")
        );
    }
}