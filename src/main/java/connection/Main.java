package connection;

import model.Player;
import model.Transaction;
import service.*;
import validation.PlayerValidator;

import java.sql.SQLException;
import java.util.Scanner;

import static model.TransactionType.CREDIT;
import static model.TransactionType.DEBIT;


public class Main {

    public static void main(String[] args) throws SQLException {

        AuditService auditService = new AuditService();
        TransactionService transactionService = new TransactionService(auditService);
        PlayerValidator playerValidator = new PlayerValidator();
        AuthorizationService authorizationService = new AuthorizationService(auditService, playerValidator);
        LoggerService loggerService = new LoggerService();
        AccountService accountService = new AccountService(auditService, loggerService);
        Scanner scanner = new Scanner(System.in);

        String login;
        String password;
        boolean flag = true;
        boolean isLogout = true;
        int selectionNumber;

        while (flag) {

            System.out.println("Выберите действие:");
            System.out.println("1-зарегистрироваться");
            System.out.println("2-авторизоваться");
            System.out.println("3-выход");
            selectionNumber = scanner.nextInt();
            if (selectionNumber == 1) {
                System.out.println("Введите логин:");
                login = scanner.next();
                System.out.println("Введите пароль:");
                password = scanner.next();
                Player player = authorizationService.registrationPlayer(login, password);
                accountService.getAccount(player);
            }

            if (selectionNumber == 2) {
                System.out.println("Введите логин:");
                login = scanner.next();
                System.out.println("Введите пароль:");
                password = scanner.next();
                Player player = authorizationService.logIn(login, password);
                System.out.println("Игрок авторизован");

                while (isLogout) {

                    System.out.println("Выберите действие:");
                    System.out.println("1. Пополнение счета");
                    System.out.println("2. Снятие со счета");
                    System.out.println("3. Текущий баланс");
                    System.out.println("4. Показать аудит действий");
                    System.out.println("5. Показать историю операций");
                    System.out.println("6. Выход");
                    selectionNumber = scanner.nextInt();

                    if (selectionNumber == 1) {
                        System.out.println("Введите сумму");
                        double amount = scanner.nextDouble();
                        Transaction credit = new Transaction(amount, accountService.getAccount(player), CREDIT);
                        transactionService.processTransaction(credit);
                    }
                    if (selectionNumber == 2) {
                        System.out.println("Введите сумму");
                        double amount = scanner.nextDouble();
                        Transaction debit = new Transaction(amount, accountService.getAccount(player), DEBIT);
                        transactionService.processTransaction(debit);
                    }
//                    if (selectionNumber == 3) {
//                        accountService.showPlayerCurrentBalance(player.getLogin());
//                    }
//                    if (selectionNumber == 4) {
//                        auditService.printAuditUserHistory(player.getLogin());
//                    }
//                    if (selectionNumber == 5) {
//                        transactionService.showUserOperationHistory(player.getLogin());
//                    }
                    if (selectionNumber == 6) {
                        authorizationService.logout();
                        System.out.println("Вы вышли из системы");
                        isLogout = false;
                    }
                }
            }
            if (selectionNumber == 3) {
                flag = false;
            }
        }
    }
}