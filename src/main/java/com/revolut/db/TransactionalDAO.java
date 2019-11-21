package com.revolut.db;

import com.revolut.enums.TransactionStatus;
import com.revolut.model.Account;
import org.skife.jdbi.v2.TransactionIsolationLevel;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;
import org.skife.jdbi.v2.sqlobject.Transaction;

import java.math.BigDecimal;

public abstract class TransactionalDAO {

    private static final String INSUFFICIENT_FUNDS = "Insufficient funds";
    private static final String USER_DOES_NOT_EXIST = "User does not exist. User id = ";

    @CreateSqlObject
    abstract TransactionDAO transactionDAO();

    @CreateSqlObject
    abstract AccountDAO accountDAO();

    @Transaction(TransactionIsolationLevel.SERIALIZABLE)
    public void transferMoney(Integer transactionId, Integer fromUserId, Integer toUserId, BigDecimal amount) {

        Account fromUser = accountDAO().findById(fromUserId);
        Account toUser = accountDAO().findById(toUserId);

        if(fromUser == null){
            throw new IllegalArgumentException(USER_DOES_NOT_EXIST + transactionId.toString());
        }
        if(toUser == null){
            throw new IllegalArgumentException(USER_DOES_NOT_EXIST + toUserId.toString());
        }

        if (fromUser.getBalance().compareTo(amount) >= 0) {
            transactionDAO().update(new com.revolut.model.Transaction(transactionId, fromUserId, toUserId, amount, TransactionStatus.Pending));
        } else {
            throw new IllegalArgumentException(INSUFFICIENT_FUNDS);
        }

        fromUser.setBalance(fromUser.getBalance().subtract(amount));
        accountDAO().update(fromUser);

        toUser.setBalance(toUser.getBalance().add(amount));
        accountDAO().update(toUser);

        transactionDAO().update(new com.revolut.model.Transaction(transactionId, fromUserId, toUserId, amount, TransactionStatus.Committed));
    }
}
