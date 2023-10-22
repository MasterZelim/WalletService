package service;

import model.*;
import repository.TransactionRepository;
import validation.AccountValidator;

import java.sql.SQLException;
import java.util.*;

public class TransactionService {


    private final AuditService auditService;
    private final LoggerService informationService = new LoggerService();
    private final AccountValidator playerAmountValidation = new AccountValidator();

    public TransactionService(AuditService auditService) {
        this.auditService = auditService;
    }

    public void processTransaction(Transaction transaction) throws SQLException {

        TransactionRepository.createTable();
        playerAmountValidation.amountValidation(transaction.getAmount());

        String login = transaction.getAccount().getPlayer().getLogin();

            if (TransactionRepository.retrieveBoolean(transaction)) {
                informationService.infoError(Action.ERROR);
                throw new RuntimeException("Транзакция не уникальна");
            }

            if (transaction.getTransactionType() == TransactionType.CREDIT) {
            double newBalance = transaction.getAccount().getBalance() + transaction.getAmount();
            transaction.getAccount().setBalance(newBalance);
            TransactionRepository.insertRecord(transaction);
            auditService.saveAuditUserHistory(login, Action.CREDIT);
            informationService.info(Action.CREDIT);
        }
        if (transaction.getTransactionType() == TransactionType.DEBIT) {
            double newBalance = transaction.getAccount().getBalance() - transaction.getAmount();
            if (newBalance >= 0) {
                transaction.getAccount().setBalance(newBalance);
                TransactionRepository.insertRecord(transaction);
                auditService.saveAuditUserHistory(transaction.getAccount().getPlayer().getLogin(), Action.DEBIT);
                informationService.info(Action.DEBIT);
            }
            else {
                informationService.infoError(Action.ERROR);
                throw new RuntimeException("Недостаточно баланса для игрока: " + login);
            }
        }
    }

    public void showUserOperationHistory(Account account) throws SQLException {
        List<Transaction> transaction = TransactionRepository.retrieveTransaction(account);
        if (transaction == null) {
            System.out.println("История действий для: " + account.getPlayer().getLogin() + " не найдена");
            return;
        }
        transaction.forEach(System.out::println);
    }
}
