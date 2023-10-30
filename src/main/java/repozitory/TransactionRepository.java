package repozitory;

import model.Player;
import model.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TransactionRepository {

    boolean save(Transaction transaction);
    Optional<List<Transaction>> getByAccountIdAndPlayerId(Long accountId,Long playerId);
    Optional<Set<Long>> getTransactionUuid();
    Optional<Player> getById(Long id);

}
