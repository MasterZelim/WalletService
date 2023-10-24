package model;

import java.sql.Timestamp;

public class TransactionHistory {

    private Long id;
    private Long accountId;
    private Long playerId;
    private TransactionType typeTransaction;
    private float amount;
    private Timestamp timestamp;

    public TransactionHistory(Long id, Long accountId, Long playerId, TransactionType typeTransaction, float amount, Timestamp timestamp) {
        this.id = id;
        this.accountId = accountId;
        this.playerId = playerId;
        this.typeTransaction = typeTransaction;
        this.amount = amount;
        this.timestamp = timestamp;
    }
}
