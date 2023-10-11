package service;

import model.*;
import validation.AccountValidator;

import java.util.*;

public class TransactionService {

    private final Map<String, List<Transaction>> playerTransactionHistory = new HashMap<>();
    private final HashSet<UUID> transactionUuid = new HashSet<>();
    private final AuditService auditService;
    private final LoggerService informationService = new LoggerService();
    private final AccountValidator playerAmountValidation = new AccountValidator();

    public TransactionService(AuditService auditService) {
        this.auditService = auditService;
    }

    public void processTransaction(Transaction transaction) {
        playerAmountValidation.amountValidation(transaction.getAmount());

        String login = transaction.getAccount().getPlayer().getLogin();

        if (transactionUuid.contains(transaction.getUuid())) {
            informationService.infoError(Action.ERROR);
            throw new RuntimeException("Транзакция не уникальна");
        }
        if (transaction.getTransactionType() == TransactionType.CREDIT) {
            double newBalance = transaction.getAccount().getBalance() + transaction.getAmount();
            transaction.getAccount().setBalance(newBalance);

            transactionUuid.add(transaction.getUuid());
            List<Transaction> trans = playerTransactionHistory
                    .computeIfAbsent(login, k -> new ArrayList<>());
            trans.add(transaction);
            auditService.saveAuditUserHistory(login, Action.CREDIT);
            informationService.info(Action.CREDIT);
        }
        if (transaction.getTransactionType() == TransactionType.DEBIT) {
            double newBalance = transaction.getAccount().getBalance() - transaction.getAmount();
            if (newBalance >= 0) {
                transaction.getAccount().setBalance(newBalance);
                transactionUuid.add(transaction.getUuid());
                List<Transaction> trans = playerTransactionHistory
                        .computeIfAbsent(login, k -> new ArrayList<>());
                trans.add(transaction);
                auditService.saveAuditUserHistory(transaction.getAccount().getPlayer().getLogin(), Action.DEBIT);
                informationService.info(Action.DEBIT);
            }
            else {
                informationService.infoError(Action.ERROR);
                throw new RuntimeException("Недостаточно баланса для игрока: " + login);
            }
        }
    }

    public void showUserOperationHistory(String login) {
        List<Transaction> transaction = playerTransactionHistory.get(login);
        if (transaction == null) {
            System.out.println("История действий для: " + login + " не найдена");
            return;
        }
        transaction.forEach(System.out::println);
    }
}
