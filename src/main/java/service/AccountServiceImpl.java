package service;

import model.Account;
import model.Action;
import model.Audit;
import model.Player;
import repozitory.AccountRepository;
import repozitory.AccountRepositoryImpl;

import java.sql.SQLException;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {

    private final LoggerService loggerService;
    private final AuditService auditService;
    private final AccountRepository accountRepository;

    public AccountServiceImpl(LoggerService loggerService, AuditService auditService,
                              AccountRepository accountRepository) {
        this.loggerService = loggerService;
        this.auditService = auditService;
        this.accountRepository = accountRepository;
    }


    @Override
    public Account getAccount(Player player) {
        Optional<Account> account = accountRepository.getAccountById(player.getId());
        if (account.isEmpty()) {
            Account newAccount = new Account(player, (float) 0);
            accountRepository.save(newAccount);
            auditService.saveAuditUserHistory(new Audit(newAccount.getPlayer().getId(), Action.CREATE_ACCOUNT));
            loggerService.info(Action.CREATE_ACCOUNT);
            return newAccount;
        }
        return account.get();
    }

    @Override
    public void showPlayerCurrentBalance(Player player) {

        System.out.println("Текущий баланс игрока: " + accountRepository.getCurrentBalance(player.getId()));

    }

    @Override
    public void updateBalance(Account account) {

        accountRepository.saveCurrentBalance(account);

    }
}
