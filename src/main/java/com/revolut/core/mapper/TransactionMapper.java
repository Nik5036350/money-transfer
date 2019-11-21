package com.revolut.core.mapper;

import com.revolut.enums.TransactionStatus;
import com.revolut.model.Transaction;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionMapper implements ResultSetMapper<Transaction>
{
    public Transaction map(int index, ResultSet resultSet, StatementContext statementContext) throws SQLException
    {
        return new Transaction(
                resultSet.getInt("id"),
                resultSet.getInt("FromUserId"),
                resultSet.getInt("ToUserId"),
                resultSet.getBigDecimal("Amount"),
                TransactionStatus.valueOf(resultSet.getString("Status"))
        );
    }
}