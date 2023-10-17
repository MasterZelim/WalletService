package repozitory;

import model.Account;
import model.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository {

//    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
//    private static final String USER_NAME = "postgres";
//    private static final String PASSWORD = "1";
//
//
//    public static void insertRecord(String login, String password) throws SQLException {
//
//        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
//
//            String insertDataSQL = "INSERT INTO accounts (login, password) VALUES (?, ?)";
//            PreparedStatement insertDataStatement = connection.prepareStatement(insertDataSQL);
//            insertDataStatement.setString(1, login);
//            insertDataStatement.setString(2, password);
//            insertDataStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }
//    public static Account retrieveAccount(Player player) throws SQLException {
//
//        List<Account> playerList = new ArrayList<>();
//
//        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
//
//            String retrieveDataSQL = "SELECT * FROM accounts";
//            Statement retrieveDataStatement = connection.createStatement();
//            ResultSet  resultSet = retrieveDataStatement.executeQuery(retrieveDataSQL);
//
//            while (resultSet.next()){
//                Player player = resultSet.getString("login");
//                String password = resultSet.getString("password");
//
//                Player player = new Player(loginPlayer,password);
//                playerList.add(player);
//            }
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        for (Player player : playerList) {
//
//            if (player.getLogin().equals(login)){
//                return ac;
//            }
//
//        }
//        return null;
//    }
//
//
//
//    public static void createTable() throws SQLException {
//
//        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
//
//            String createTableSQL = "CREATE TABLE IF NOT EXISTS accounts (" +
//                    "id SERIAL PRIMARY KEY," +
//                    "player INT REFERENCES players(id)," +
//                    "balance NUMERIC())";
//            Statement createTableStatement = connection.createStatement();
//            createTableStatement.execute(createTableSQL);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }
}
