package com.revolut.db;

import com.revolut.core.mapper.UserMapper;
import com.revolut.model.User;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(UserMapper.class)
public interface UserDAO {

    @SqlQuery("select * from User")
    List<User> getAll();

    @SqlQuery("select * from User where id = :id")
    User findById(@Bind("id") int id);

    @SqlUpdate("insert into User (Name, Email ) values (:name, :email)")
    int insert(@BindBean User user);
}
