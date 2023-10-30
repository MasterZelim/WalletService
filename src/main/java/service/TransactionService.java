package service;

import model.Account;
import model.Transaction;

public interface TransactionService {

    void processTransaction(Transaction transaction);
    void saveUserTransaction(Transaction transaction);
    void showUserTransactionHistory(Account account);


}
