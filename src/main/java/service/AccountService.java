package service;

import model.Account;
import model.Player;

public interface AccountService {

    Account getAccount(Player player);
    void showPlayerCurrentBalance(Player player);
    void updateBalance(Account account);
}
