package repozitory;



import model.Account;
import model.Action;
import model.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorizationRepository {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER_NAME = "postgres";
    public static final String PASSWORD = "1";


    public static void insertRecord(String login, String password) throws SQLException {

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {

            String insertDataSQL = "INSERT INTO players (login, password) VALUES (?, ?)";
            PreparedStatement insertDataStatement = connection.prepareStatement(insertDataSQL);
            insertDataStatement.setString(1, login);
            insertDataStatement.setString(2, password);
            insertDataStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static Player retrievePlayer(String login) throws SQLException {

        List<Player> playerList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {

            String retrieveDataSQL = "SELECT * FROM players";
            Statement retrieveDataStatement = connection.createStatement();
            ResultSet  resultSet = retrieveDataStatement.executeQuery(retrieveDataSQL);

            while (resultSet.next()){
                String loginPlayer = resultSet.getString("login");
                String password = resultSet.getString("password");

                Player player = new Player(loginPlayer,password);
                playerList.add(player);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Player player : playerList) {

            if (player.getLogin().equals(login)){
                return player;
            }

        }
        return null;
    }
    public static void createTable() throws SQLException {

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {

            String createTableSQL = "CREATE TABLE IF NOT EXISTS players (" +
                    "id SERIAL PRIMARY KEY," +
                    "login VARCHAR(255)," +
                    "password VARCHAR(255))";
            Statement createTableStatement = connection.createStatement();
            createTableStatement.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
