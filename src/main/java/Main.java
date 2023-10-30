import config.ConnectionManager;
import config.ConnectionManagerLiquibase;
import model.*;
import repozitory.*;
import service.*;
import validation.AccountValidator;
import validation.AccountValidatorImpl;
import validation.PlayerValidator;
import validation.PlayerValidatorImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static model.TransactionType.CREDIT;
import static model.TransactionType.DEBIT;


public class Main {

    public static void main(String[] args) throws SQLException {

        String CREATE_SCHEMA_WALLET = "CREATE SCHEMA IF NOT EXISTS wallet";
        String CREATE_SCHEMA_MIGRATION = "CREATE SCHEMA IF NOT EXISTS migration";
        String path = "db/db/changelog.xml";
        try (Connection connection = ConnectionManager.open();
             var database = ConnectionManagerLiquibase.getConnection(connection)) {
            Statement statement = connection.createStatement();
            statement.addBatch(CREATE_SCHEMA_WALLET);
            statement.addBatch(CREATE_SCHEMA_MIGRATION);
            statement.executeBatch();
            ConnectionManagerLiquibase.executeLiquibase(path, database);
        } catch (Exception e) {
            e.printStackTrace();
        }


        AccountRepository accountRepository = new AccountRepositoryImpl();
        AuditRepository auditRepository = new AuditRepositoryImpl();
        PlayerRepository playerRepository = new PlayerRepositoryImpl();
        TransactionRepository transactionRepository = new TransactionRepositoryImpl();

        PlayerValidator playerValidator = new PlayerValidatorImpl();
        AccountValidator accountValidator = new AccountValidatorImpl();

        AuditService auditService = new AuditServiceImpl(auditRepository);
        LoggerService loggerService = new LoggerServiceImpl();
        AuthorizationService authorizationService = new AuthorizationServiceImpl(auditService, playerValidator, loggerService, playerRepository);
        AccountService accountService = new AccountServiceImpl(loggerService, auditService, accountRepository);

        TransactionService transactionService = new TransactionServiceImpl(auditService, loggerService, accountValidator, accountService, transactionRepository);


        Scanner in = new Scanner(System.in);

        String name;
        String password;
        boolean flag = true;
        boolean isLogout = false;


        while (flag) {

            System.out.println("""
                    Выберите действие:
                    1-зарегистрироваться
                    2-авторизоваться
                    3-выход""");


            int actionNumber=in.nextInt();

            if (actionNumber == 1) {
                System.out.println("Введите логин:");
                name = in.next();
                System.out.println("Введите пароль:");
                password = in.next();
                Player player = authorizationService.playerRegister(name, password);
                accountService.getAccount(player);
            }

            if (actionNumber == 2) {
                System.out.println("Введите логин:");
                name = in.next();
                System.out.println("Введите пароль:");
                password = in.next();
                Player player = authorizationService.logIn(name, password);
                Account account = accountService.getAccount(player);
                System.out.println("Игрок авторизован" + player.getName());

                while (!isLogout) {

                    System.out.println("""
                    Выберите действие:
                    1. Пополнение счета
                    2. Снятие со счета
                    3. Текущий баланс
                    4. Показать аудит действий
                    5. Показать историю операций
                    6. Выход""");

                    actionNumber = in.nextInt();

                    if (actionNumber == 1) {
                        System.out.println("Введите сумму");
                        float amount = in.nextFloat();
                          Transaction credit = new Transaction(CREDIT, accountService.getAccount(player), amount);
                          transactionService.processTransaction(credit);
                    }
                    if (actionNumber == 2) {
                        System.out.println("Введите сумму");
                        float amount = in.nextFloat();
                         Transaction debit = new Transaction(DEBIT, accountService.getAccount(player), amount);
                         transactionService.processTransaction(debit);
                    }
                    if (actionNumber == 3) {
                        accountService.showPlayerCurrentBalance(player);
                    }
                    if (actionNumber == 4) {
                        auditService.printAuditUserHistory(player);
                    }
                    if (actionNumber == 5) {
                        transactionService.showUserTransactionHistory(account);
                    }
                    if (actionNumber == 6) {
                        authorizationService.logout(player);
                        isLogout = true;
                        System.out.println("Вы вышли из системы");
                    }
                }
            }
            if (actionNumber == 3) {
                loggerService.info(Action.COMPLETION_WORK);
                flag = false;
            }
        }
    }
}

