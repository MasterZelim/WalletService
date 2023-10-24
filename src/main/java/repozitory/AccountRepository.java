package repozitory;

import model.Account;
import model.Player;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "1";

    private final String SELECT_BY_player_Id_LEFT_JOIN = "SELECT account.id, account.player_id, account.balance, " +
            "player.name, player.password FROM account LEFT JOIN player ON player.id = player_id WHERE player_id = ?";

    private final String SELECT_BY_PLAYER_NAME_LEFT_JOIN = "SELECT account.id, account.player_id, account.balance, " +
            "player.name, player.password FROM account LEFT JOIN player ON player.id = player_id WHERE player.name = ?";
    private final String INSERT_ACCOUNT = "INSERT INTO account(player_id,balance) VALUES(?,?)";
    private final String UPDATE_BALANCE = "UPDATE account SET balance = ? WHERE id = ?";
    public static void insertRecord(Account account) throws SQLException {

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {

            String insertDataSQL = "INSERT INTO accounts (player_id, balance) VALUES (?, ?)";
            PreparedStatement insertDataStatement = connection.prepareStatement(insertDataSQL);
            insertDataStatement.setLong(1, account.getPlayer().getId());
            insertDataStatement.setDouble(2, account.getBalance());
            insertDataStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static Account retrieveAccount(Player player) throws SQLException {

        List<Account> accountList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {

            String retrieveDataSQL = "SELECT * FROM accounts";
            Statement retrieveDataStatement = connection.createStatement();
            ResultSet  resultSet = retrieveDataStatement.executeQuery(retrieveDataSQL);

            while (resultSet.next()){
                long id = resultSet.getInt("id");
                int player_id = resultSet.getInt("player_id");
                double balance = resultSet.getDouble("balance");

                Player player1 = AuthorizationRepository.retrievePlayer(player_id);
//                Account account = new Account(id,player1,balance);
//                accountList.add(account);
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
    public static Account retrieveAccount(long id) throws SQLException {

        List<Account> accountList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {

            String retrieveDataSQL = "SELECT * FROM accounts";
            Statement retrieveDataStatement = connection.createStatement();
            ResultSet  resultSet = retrieveDataStatement.executeQuery(retrieveDataSQL);

            while (resultSet.next()){
                long id1 = resultSet.getInt("id");
                int player_id = resultSet.getInt("player_id");
                double balance = resultSet.getDouble("balance");

                Player player1 = AuthorizationRepository.retrievePlayer(player_id);
               // Account account = new Account(id1,player1,balance);
               // accountList.add(account);
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

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {

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
