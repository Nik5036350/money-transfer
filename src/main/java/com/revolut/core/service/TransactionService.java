package com.revolut.core.service;

import com.revolut.db.AccountDAO;
import com.revolut.db.TransactionDAO;
import com.revolut.db.TransactionalDAO;
import com.revolut.enums.TransactionStatus;
import com.revolut.model.Account;
import com.revolut.model.Transaction;

import javax.inject.Inject;
import java.math.BigDecimal;

public class TransactionService {

    private static final String USER_DOES_NOT_EXIST = "User does not exist. User id = ";

    @Inject
    TransactionDAO transactionDAO;

    @Inject
    TransactionalDAO transactionalDAO;

    @Inject
    AccountDAO accountDAO;

    public void transferMoney(Integer fromUserId, Integer toUserId, BigDecimal amount) {

        checkAccount(fromUserId);
        checkAccount(toUserId);

        Integer transactionId = transactionDAO.insert(new com.revolut.model.Transaction(null, fromUserId, toUserId, amount, null));
        try {
            transactionalDAO.transferMoney(transactionId, fromUserId, toUserId, amount);
        }catch (Exception e){
            transactionDAO.update(new Transaction(transactionId, fromUserId, toUserId, amount, TransactionStatus.Failed));
            throw e;
        }
    }

    private void checkAccount(Integer fromUserId) {
        Account user = accountDAO.findById(fromUserId);

        if(user == null){
            throw new IllegalArgumentException(USER_DOES_NOT_EXIST + fromUserId.toString());
        }
    }
}
