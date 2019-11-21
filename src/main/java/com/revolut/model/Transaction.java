package com.revolut.model;

import com.revolut.enums.TransactionStatus;

import java.math.BigDecimal;
import java.util.Objects;

public class Transaction {

    private Integer id;

    private int fromUser;

    private int toUser;

    private BigDecimal amount;

    private TransactionStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return fromUser == that.fromUser &&
                toUser == that.toUser &&
                id.equals(that.id) &&
                amount.equals(that.amount) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fromUser, toUser, amount, status);
    }

    public Transaction() {

    }

    public Transaction(Integer id, int fromUser, int toUser, BigDecimal amount, TransactionStatus status) {
        this.id = id;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.amount = amount;
        this.status = status;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getFromUser() {
        return fromUser;
    }

    public void setFromUser(int fromUser) {
        this.fromUser = fromUser;
    }

    public int getToUser() {
        return toUser;
    }

    public void setToUser(int toUser) {
        this.toUser = toUser;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
