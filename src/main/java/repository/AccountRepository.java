package repository;

import connection.ConnectionDatabase;
import model.Account;
import model.Player;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository {


    public static void insertRecord(Account account) throws SQLException {


            try(Connection connection = ConnectionDatabase.connection()) {
                String insertDataSQL = "INSERT INTO accounts (player_id, balance) VALUES (?, ?)";
                PreparedStatement insertDataStatement = connection.prepareStatement(insertDataSQL);
                insertDataStatement.setLong(1, account.getPlayer().getId());
                insertDataStatement.setDouble(2, account.getBalance());
                insertDataStatement.executeUpdate();

            }catch (SQLException e){
                e.printStackTrace();
            }
    }
    public static Account getAccountByPlayerId(Player player) throws SQLException {

        List<Account> accountList = new ArrayList<>();

        try (Connection connection = ConnectionDatabase.connection()) {

            String retrieveDataSQL = "SELECT * FROM accounts";
            Statement retrieveDataStatement = connection.createStatement();
            ResultSet  resultSet = retrieveDataStatement.executeQuery(retrieveDataSQL);

            while (resultSet.next()){
                long id = resultSet.getInt("id");
                int player_id = resultSet.getInt("player_id");
                double balance = resultSet.getDouble("balance");
                Player player1 = AuthorizationRepository.retrievePlayer(player_id);
                Account account = new Account(id,player1,balance);
                accountList.add(account);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Account account : accountList) {

            if (account.getPlayer().equals(player)){
                return account;
            }
        }
        return null;
    }
    public static Account getAccountById(long id) throws SQLException {

        List<Account> accountList = new ArrayList<>();

        try (Connection connection = ConnectionDatabase.connection()) {

            String retrieveDataSQL = "SELECT * FROM accounts";
            Statement retrieveDataStatement = connection.createStatement();
            ResultSet  resultSet = retrieveDataStatement.executeQuery(retrieveDataSQL);

            while (resultSet.next()){
                long id1 = resultSet.getInt("id");
                int player_id = resultSet.getInt("player_id");
                double balance = resultSet.getDouble("balance");

                Player player1 = AuthorizationRepository.retrievePlayer(player_id);
                Account account = new Account(id1,player1,balance);
                accountList.add(account);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Account account : accountList) {

            if (account.getId()==id){
                return account;
            }
        }
        return null;
    }
    public static void createTable() throws SQLException {

        try (Connection connection = ConnectionDatabase.connection()) {

            String createTableSQL = "CREATE TABLE IF NOT EXISTS accounts (" +
                    "id SERIAL PRIMARY KEY," +
                    "player_id INT REFERENCES players(id)," +
                    "balance NUMERIC)";
            Statement createTableStatement = connection.createStatement();
            createTableStatement.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
