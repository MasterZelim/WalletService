package service;

import model.Account;
import model.Action;
import model.Player;
import repozitory.AccountRepository;

import java.util.HashMap;
import java.util.Map;
public class AccountService {

//    private final LoggerService loggerService;
//    private final AuditService auditService;
//    public AccountService(AuditService auditService, LoggerService loggerService) {
//        this.auditService = auditService;
//        this.loggerService = loggerService;
//    }
//    public Account getAccount(Player player) {
//        Account account = AccountRepository.retrieveAccount(player);
//        if (account == null) {
//            account = new Account(player, 0);
//            AccountRepository.insertRecord(account);
//            auditService.saveAuditUserHistory(account.getPlayer().getLogin(), Action.CREATE_ACCOUNT);
//            loggerService.info(Action.CREATE_ACCOUNT);
//        }
//        return account;
//    }
//    public void showPlayerCurrentBalance(String login) {
//        System.out.println("Текущий баланс игрока: " + accounts.get(login).getBalance());
//    }
}
