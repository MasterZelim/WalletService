package repozitory;

import model.Account;

import java.util.Optional;

public interface AccountRepository {

    Optional<Account> getAccountById(long playerId);
    Optional<Account> getAccountByName(String name);
    boolean save(Account account);
    float getCurrentBalance(Long id);
    boolean saveCurrentBalance(Account account);

}
