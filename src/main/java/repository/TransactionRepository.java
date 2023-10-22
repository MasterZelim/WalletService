package repository;

import model.Account;
import model.Transaction;
import model.TransactionType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionRepository {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "1";

    public static void insertRecord(Transaction transaction) throws SQLException {

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {

            String insertDataSQL = "INSERT INTO transactions (uuid, amount,account_id,transactionType) VALUES (?,?,?,?)";
            PreparedStatement insertDataStatement = connection.prepareStatement(insertDataSQL);
            insertDataStatement.setString(1, String.valueOf(transaction.getUuid()));
            insertDataStatement.setDouble(2, transaction.getAmount());
            insertDataStatement.setLong(3, transaction.getAccount().getId());
            insertDataStatement.setString(4, String.valueOf(transaction.getTransactionType()));
            insertDataStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static List<Transaction> retrieveTransaction(Account account) throws SQLException {

        List<Transaction> transactionList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {

            String retrieveDataSQL = "SELECT * FROM transactions";
            Statement retrieveDataStatement = connection.createStatement();
            ResultSet  resultSet = retrieveDataStatement.executeQuery(retrieveDataSQL);

            while (resultSet.next()){
                long id = resultSet.getInt("id");
                UUID uuid = UUID.fromString(resultSet.getString("uuid"));
                double amount = resultSet.getDouble("amount");
                long account_id = resultSet.getLong("account_id");
                TransactionType transactionType = TransactionType.valueOf(resultSet.getString("transactionType"));

                Account account1 = AccountRepository.getAccountById(account_id);
                Transaction transaction = new Transaction(id,uuid,amount,account1,transactionType);

                if (transaction.getAccount().equals(account)){
                    transactionList.add(transaction);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactionList;
    }

    public static boolean retrieveBoolean(Transaction transaction) throws SQLException {

        List<Transaction> transactionList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {

            String retrieveDataSQL = "SELECT * FROM transactions";
            Statement retrieveDataStatement = connection.createStatement();
            ResultSet  resultSet = retrieveDataStatement.executeQuery(retrieveDataSQL);

            while (resultSet.next()){
                long id = resultSet.getLong("id");
                UUID uuid = UUID.fromString(resultSet.getString("uuid"));
                double amount = resultSet.getDouble("amount");
                long account_id = resultSet.getLong("account_id");
                TransactionType transactionType = TransactionType.valueOf(resultSet.getString("transactionType"));

                Account account1 = AccountRepository.getAccountById(account_id);
                Transaction transaction1 = new Transaction(id,uuid,amount,account1,transactionType);
                transactionList.add(transaction1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Transaction transaction2 : transactionList) {

            if (transaction2.getUuid().equals(transaction.getUuid())){
                return true;
            }
        }
        return false;
    }
    public static void createTable() throws SQLException {

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {

            String createTableSQL = "CREATE TABLE IF NOT EXISTS transactions (" +
                    "id SERIAL PRIMARY KEY," +
                    "uuid VARCHAR(255)," +
                    "amount NUMERIC,"+
                    "account_id INT REFERENCES accounts(id),"+
                    "transactionType VARCHAR(6))";
            Statement createTableStatement = connection.createStatement();
            createTableStatement.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
