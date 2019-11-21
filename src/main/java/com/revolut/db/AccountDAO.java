package com.revolut.db;

import com.revolut.core.mapper.AccountMapper;
import com.revolut.model.Account;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(AccountMapper.class)
public interface AccountDAO {

    @SqlQuery("select * from Account")
    List<Account> getAll();

    @SqlQuery("select * from Account where id = :id")
    Account findById(@Bind("id") int id);

    @SqlUpdate("update Account set Balance = :balance where ID = :id")
    int update(@BindBean Account account);

    @SqlUpdate("insert into Account (UserId, Balance) values (:userId, :balance)")
    int insert(@BindBean Account account);
}
