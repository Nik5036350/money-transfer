package com.revolut.db;

import com.revolut.core.mapper.TransactionMapper;
import com.revolut.model.Transaction;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(TransactionMapper.class)
public interface TransactionDAO {

    @SqlQuery("select * from Transaction")
    List<Transaction> getAll();

    @SqlQuery("select * from Transaction where Id = :id")
    Transaction findById(@Bind("id") int id);

    @SqlUpdate("update Transaction set Status = :status where Id = :id")
    int update(@BindBean Transaction transaction);

    @SqlUpdate("insert into Transaction (FromUserId, ToUserId, Amount ) values (:fromUser, :toUser, :amount)")
    @GetGeneratedKeys
    int insert(@BindBean Transaction transaction);
}
