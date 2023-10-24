package model;

import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@ToString
public class Transaction {
    private Long uuid;
    private final TransactionType typeTransaction;
    private final Account account;
    private float amount;
    private final Timestamp timestamp;


    public Transaction(TransactionType typeTransaction, Account account, float amount) {
        this.typeTransaction = typeTransaction;
        this.account = account;
        this.amount = amount;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public Transaction(Long uuid, TransactionType typeTransaction, Account account, float amount, Timestamp timestamp) {
        this.uuid = uuid;
        this.typeTransaction = typeTransaction;
        this.account = account;
        this.amount = amount;
        this.timestamp = timestamp;
    }
}
