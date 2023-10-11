package model;

import lombok.Data;
import java.util.UUID;
@Data
public class Transaction {
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
}
