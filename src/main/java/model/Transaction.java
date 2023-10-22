package model;

import lombok.Data;
import java.util.UUID;

public class Transaction {
    private long id;
    private UUID uuid;
    private double amount;
    private Account account;
    private TransactionType transactionType;
    public Transaction(double amount, Account account, TransactionType transactionType) {
        this.uuid = UUID.randomUUID();
        this.amount = amount;
        this.account = account;
        this.transactionType = transactionType;
    }


    public Transaction(long id, UUID uuid, double amount, Account account, TransactionType transactionType) {
        this.id = id;
        this.uuid = uuid;
        this.amount = amount;
        this.account = account;
        this.transactionType = transactionType;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
