package service;

import model.*;
import repozitory.TransactionRepository;
import validation.AccountValidator;
import validation.AccountValidatorImpl;

public class TransactionServiceImpl implements TransactionService {


  private final AuditService auditService;
  private final LoggerService loggerService;
  private final AccountValidator accountValidator;
  private final AccountService accountService;
  private final TransactionRepository transactionRepository;


    public TransactionServiceImpl(AuditService auditService,
                                  LoggerService loggerService,
                                  AccountValidator accountValidator,
                                  AccountService accountService,
                                  TransactionRepository transactionRepository) {


        this.auditService = auditService;
        this.loggerService = loggerService;
        this.accountValidator = accountValidator;
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
    }


    @Override
    public void processTransaction(Transaction transaction) {
        accountValidator.amountValidator(transaction.getAmount());
        Player player = transaction.getAccount().getPlayer();
        if (transaction.getTypeTransaction()==TransactionType.CREDIT){
            float newBalance = transaction.getAccount().getBalance() + transaction.getAmount();
            transaction.getAccount().setBalance(newBalance);
            accountService.updateBalance(transaction.getAccount());
            saveUserTransaction(transaction);
            auditService.saveAuditUserHistory(new Audit(player.getId(),Action.CREDIT));
            loggerService.info(Action.CREDIT);
        }
        if (transaction.getTypeTransaction()==TransactionType.DEBIT){
            float newBalance = transaction.getAccount().getBalance() - transaction.getAmount();
            if (newBalance>=0){
                transaction.getAccount().setBalance(newBalance);
                accountService.updateBalance(transaction.getAccount());
                saveUserTransaction(transaction);
                auditService.saveAuditUserHistory(new Audit(player.getId(),Action.DEBIT));
            }

            else {
                loggerService.infoError(Action.ERROR);
                throw new RuntimeException("Not enough balance for player: " + player.getName());

            }
        }
    }

    @Override
    public void saveUserTransaction(Transaction transaction) {

        transactionRepository.save(transaction);
    }

    @Override
    public void showUserTransactionHistory(Account account) {
        var transactionHistories = transactionRepository.getByAccountIdAndPlayerId(account.getId(),account.getPlayer().getId());
        if (transactionHistories.isEmpty()){
            System.out.println("Action history for name: " + account.getPlayer().getName()+ " not found");
            return;
        }
        transactionHistories.get().forEach(System.out::println);

    }
}

