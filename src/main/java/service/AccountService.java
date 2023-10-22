package service;

import model.Account;
import model.Action;
import model.Player;
import repository.AccountRepository;

import java.sql.SQLException;

public class AccountService {

    private final LoggerService loggerService;
    private final AuditService auditService;
    public AccountService(AuditService auditService, LoggerService loggerService) {
        this.auditService = auditService;
        this.loggerService = loggerService;
    }
    public Account getAccount(Player player) {
        Account account = null;
        try {
            AccountRepository.createTable();
            account = AccountRepository.getAccountByPlayerId(player);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (account == null) {
            account = new Account(player, 0);
            try {
                AccountRepository.insertRecord(account);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            auditService.saveAuditUserHistory(account.getPlayer().getLogin(), Action.CREATE_ACCOUNT);
            loggerService.info(Action.CREATE_ACCOUNT);
        }
        return account;
    }
//    public void showPlayerCurrentBalance(String login) {
//        System.out.println("Текущий баланс игрока: " + accounts.get(login).getBalance());
//    }
}
